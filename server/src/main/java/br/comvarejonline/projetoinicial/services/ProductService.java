package br.comvarejonline.projetoinicial.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.comvarejonline.projetoinicial.dtos.MovementCreateDTO;
import br.comvarejonline.projetoinicial.dtos.ProductCreateDTO;
import br.comvarejonline.projetoinicial.dtos.ProductDTO;
import br.comvarejonline.projetoinicial.dtos.ProductUpdateDTO;
import br.comvarejonline.projetoinicial.dtos.TypeMovementDTO;
import br.comvarejonline.projetoinicial.dtos.UserDTO;
import br.comvarejonline.projetoinicial.entities.Movement;
import br.comvarejonline.projetoinicial.entities.Product;
import br.comvarejonline.projetoinicial.entities.TypeMovement;
import br.comvarejonline.projetoinicial.entities.User;
import br.comvarejonline.projetoinicial.repositories.ProductRepository;
import br.comvarejonline.projetoinicial.services.exceptions.ResourceNotFoundException;
import br.comvarejonline.projetoinicial.utils.CopyDtoToEntity;

/*
 * Serviço da entidade Produto
 */
@Service
public class ProductService {

    private ProductRepository productRepository;
    private MovementService movementService;
    private TypeMovementService typeMovementService;
    private UserService userService;

    public ProductService(ProductRepository productRepository, MovementService movementService,
            TypeMovementService typeMovementService, UserService userService) {
        this.productRepository = productRepository;
        this.movementService = movementService;
        this.typeMovementService = typeMovementService;
        this.userService = userService;
    }

    // Consulta todos os produtos
    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {
        List<Product> productList = productRepository.findAll();
        return productList.stream().map(product -> new ProductDTO(product)).collect(Collectors.toList());
    }

    // Consulta um produto por id
    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        Product product = productOptional.orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado!"));
        return new ProductDTO(product);
    }

    // Cria um produto
    @Transactional
    public ProductCreateDTO create(ProductCreateDTO productDTO) {
        Product product = new Product();

        // Copia o DTO do produto para a entidade
        CopyDtoToEntity.copyProductDtoToProduct(productDTO, product);

        // Adiciona a data de criação do produto subtraindo três horas do padrão UTC
        product.setCreatedAt(Instant.now().minusMillis(10800000));

        // Cria o produto
        product = productRepository.save(product);

        // Se o saldo do produto é maior que 0, chama o método para criar um movimento
        // de SALDO_INICIAL
        if (product.getBalance() > 0) {
            createInitialBalanceMovement(product, productDTO.getUserId());
        }

        // Retorna o produto criado
        return new ProductCreateDTO(product);
    }

    // Atualiza um produto por id
    @Transactional
    public ProductUpdateDTO update(Long id, ProductUpdateDTO productDTO) {
        try {
            // Consulta se o produto existe
            Product product = productRepository.getById(id);
            product.setName(productDTO.getName());
            product.setHexCode(productDTO.getHexCode());
            product = productRepository.save(product);
            return new ProductUpdateDTO(product);

            // Caso não exista lança um excesão
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Produto não encontrado = " + id);
        }
    }

    // Método que cria uma movimentação de SALDO_INICAL
    private void createInitialBalanceMovement(Product product, Long userId) {

        // Consulta o usuário por id
        UserDTO userDTO = userService.findById(userId);
        User user = new User();

        // Copia o DTO do produto para a entidade
        CopyDtoToEntity.copyUserDtoToUser(userDTO, user);

        // Consulta o tipo de movimentação SALDO_INICIAL
        TypeMovementDTO typeMovementDTO = typeMovementService.findById(1L);

        TypeMovement typeMovement = new TypeMovement();

        // Copia o DTO do tipo de movimento para a entidade
        CopyDtoToEntity.copyTypeMovementDtoToTypeMovement(typeMovementDTO, typeMovement);

        // Integer quantity = product.getBalance();
        // product.setBalance(0);

        // Cria um objeto movimento e adiciona os valores
        Movement movement = new Movement();
        movement.setProduct(product);
        movement.setTypeMovement(typeMovement);
        movement.setUser(user);
        movement.setDate(Instant.now());
        movement.setReason("Movimentação de cadastro");
        movement.setDocument(999L);
        movement.setQuantity(product.getBalance());
        movement.setCurrentBalance(product.getBalance());

        MovementCreateDTO movementDTO = new MovementCreateDTO(movement);
        movementService.create(movementDTO);

    }
}
