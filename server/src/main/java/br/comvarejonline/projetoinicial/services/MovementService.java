package br.comvarejonline.projetoinicial.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.comvarejonline.projetoinicial.dtos.MovementCreateDTO;
import br.comvarejonline.projetoinicial.dtos.MovementDTO;
import br.comvarejonline.projetoinicial.entities.Movement;
import br.comvarejonline.projetoinicial.entities.Product;
// import br.comvarejonline.projetoinicial.repositories.MovementCustomRepository;
import br.comvarejonline.projetoinicial.repositories.MovementRepository;
import br.comvarejonline.projetoinicial.repositories.ProductRepository;
import br.comvarejonline.projetoinicial.services.exceptions.ResourceNotFoundException;
import br.comvarejonline.projetoinicial.utils.CopyDtoToEntity;

/*
 * Serviço da entidade Movimento
 */
@Service
public class MovementService {

    // private static Logger logger =
    // LoggerFactory.getLogger(MovementService.class);
    private MovementRepository movementRepository;
    // private MovementCustomRepository movementCustomRepository;
    private ProductRepository productRepository;

    public MovementService(MovementRepository movementRepository, ProductRepository productRepository) {
        this.movementRepository = movementRepository;
        this.productRepository = productRepository;
    }

    // Consulta todos os movimentos
    @Transactional(readOnly = true)
    public List<MovementDTO> findAll() {
        List<Movement> movementList = movementRepository.findAll();
        return movementList.stream().map(movement -> new MovementDTO(movement)).collect(Collectors.toList());
    }

    // Consulta um movimento por id
    @Transactional(readOnly = true)
    public MovementDTO findByMovementId(Long id) {
        Optional<Movement> movementOptional = movementRepository.findById(id);
        Movement movement = movementOptional
                .orElseThrow(() -> new ResourceNotFoundException("Movimento não encontrado!"));
        return new MovementDTO(movement);
    }

    // Consulta movimentos por id do produto
    @Transactional(readOnly = true)
    public List<MovementDTO> findByProductId(Long id) {
        List<Movement> movementList = movementRepository.findByProductId(id);
        return movementList.stream().map(movement -> new MovementDTO(movement)).collect(Collectors.toList());
    }

    // Consulta movimentos por id do tipo de movimentação
    @Transactional(readOnly = true)
    public List<MovementDTO> findByTypeMovementId(Long id) {
        List<Movement> movementList = movementRepository.findByTypeMovementId(id);
        return movementList.stream().map(movement -> new MovementDTO(movement)).collect(Collectors.toList());
    }

    // Consulta movimentos entre um intervalo de datas
    @Transactional(readOnly = true)
    public List<MovementDTO> findByDateBetween(Instant startDate, Instant endDate) {
        List<Movement> movementList = movementRepository.findByDateBetween(startDate, endDate);
        return movementList.stream().map(movement -> new MovementDTO(movement)).collect(Collectors.toList());
    }

    // Consulta movimentos por id do tipo de movimento e id do produto
    @Transactional(readOnly = true)
    public List<MovementDTO> findByTypeMovementIdAndProductId(Long typeMovementId, Long productId) {
        List<Movement> movementList = movementRepository.findByTypeMovementIdAndProductId(typeMovementId, productId);
        return movementList.stream().map(movement -> new MovementDTO(movement)).collect(Collectors.toList());
    }

    // TODO pesquisar como fazer query dinâmica

    // @Transactional(readOnly = true)
    // public List<MovementDTO> findByFilter(Long productId,Instant
    // startDate,Instant endDate,Long typeMovementId) {
    // logger.warn("FILTRO: productId: " + productId + " startDate: " + startDate +
    // " endDate: " + endDate
    // + " typeMovementId " + typeMovementId);
    // List<Movement> movementList = movementCustomRepository.findByFilter(
    // productId,
    // startDate,
    // endDate,
    // typeMovementId);
    // return movementList.stream().map(movement -> new
    // MovementDTO(movement)).collect(Collectors.toList());
    // }

    // Cria um moviemnto
    @Transactional
    public MovementCreateDTO create(MovementCreateDTO movementCreateDTO) {
        Movement movement = new Movement();
        Product product = new Product();

        // Copia o DTO do movimento para a entidade
        CopyDtoToEntity.copyMovementDtoToMovement(movementCreateDTO, movement);
        // Copia o DTO do produto para a entidade
        CopyDtoToEntity.copyProductDtoToProduct(movementCreateDTO.getProduct(), product);
        // Ajusta o balanço atual do produto
        adjustProductCurrentBalance(movementCreateDTO.getTypeMovement().getType(), movementCreateDTO.getQuantity(),
                product);
        // Ajusta a situação do movimento
        adjustMovementSituation(movement, product);

        // Atualiza o saldo atual do produto
        productRepository.updateCurrentBalanceById(product.getId(), product.getCurrentBalance());
        // Cria o movimento
        movement = movementRepository.save(movement);

        // Retorna o movimento criado
        return new MovementCreateDTO(movement);
    }

    // Método que ajusta o saldo atual do produto
    private void adjustProductCurrentBalance(Character type, Integer quantity, Product product) {

        // Se o tipo da movimentação for entrada, soma o saldo atual com a quantidade do
        // movimento, senão diminui
        if (type == 'E') {
            product.setCurrentBalance(product.getCurrentBalance() + quantity);
        } else {
            product.setCurrentBalance(product.getCurrentBalance() - quantity);
        }
    }

    // Método que ajusta a situação do movimento
    private void adjustMovementSituation(Movement movement, Product product) {

        // Se a quantidade mínima for maior que o saldo atual a situação é inferior ao
        // mínimo, senão é ok
        if (movement.getProduct().getMinQuantity() > product.getCurrentBalance()) {
            movement.setSituation("Inferior ao Mínimo");
        } else {
            movement.setSituation("Ok");
        }
    }

}
