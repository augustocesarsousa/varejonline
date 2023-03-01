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

    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {
        List<Product> productList = productRepository.findAll();
        return productList.stream().map(product -> new ProductDTO(product)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        Product product = productOptional.orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado!"));
        return new ProductDTO(product);
    }

    @Transactional
    public ProductCreateDTO create(ProductCreateDTO productDTO) {
        Product product = new Product();
        CopyDtoToEntity.copyProductDtoToProduct(productDTO, product);
        product.setCreatedAt(Instant.now());
        product = productRepository.save(product);
        if (product.getBalance() > 0) {
            createInitialBalanceMovement(product, productDTO.getUserId());
        }
        return new ProductCreateDTO(product);
    }

    @Transactional
    public ProductUpdateDTO update(Long id, ProductUpdateDTO productDTO) {
        try {
            Product product = productRepository.getById(id);
            product.setName(productDTO.getName());
            product.setHexCode(productDTO.getHexCode());
            product = productRepository.save(product);
            return new ProductUpdateDTO(product);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Produto não encontrado = " + id);
        }
    }

    private void createInitialBalanceMovement(Product product, Long userId) {
        UserDTO userDTO = userService.findById(userId);
        User user = new User();
        CopyDtoToEntity.copyUserDtoToUser(userDTO, user);

        TypeMovementDTO typeMovementDTO = typeMovementService.findById(1L);
        TypeMovement typeMovement = new TypeMovement();
        CopyDtoToEntity.copyTypeMovementDtoToTypeMovement(typeMovementDTO, typeMovement);

        Integer quantity = product.getBalance();
        product.setBalance(0);

        Movement movement = new Movement();
        movement.setProduct(product);
        movement.setTypeMovement(typeMovement);
        movement.setUser(user);
        movement.setDate(Instant.now());
        movement.setReason("Movimentação de cadastro");
        movement.setDocument(0L);
        movement.setQuantity(quantity);

        MovementCreateDTO movementDTO = new MovementCreateDTO(movement);
        movementService.create(movementDTO);

    }
}
