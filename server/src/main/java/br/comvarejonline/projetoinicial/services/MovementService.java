package br.comvarejonline.projetoinicial.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.comvarejonline.projetoinicial.dtos.MovementCreateDTO;
import br.comvarejonline.projetoinicial.dtos.MovementDTO;
import br.comvarejonline.projetoinicial.entities.Movement;
import br.comvarejonline.projetoinicial.entities.Product;
import br.comvarejonline.projetoinicial.repositories.MovementRepository;
import br.comvarejonline.projetoinicial.repositories.ProductRepository;
import br.comvarejonline.projetoinicial.services.exceptions.ResourceNotFoundException;
import br.comvarejonline.projetoinicial.utils.CopyDtoToEntity;

@Service
public class MovementService {

    private static Logger logger = LoggerFactory.getLogger(MovementService.class);

    private MovementRepository movementRepository;

    private ProductRepository productRepository;

    public MovementService(MovementRepository movementRepository, ProductRepository productRepository) {
        this.movementRepository = movementRepository;
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<MovementDTO> findAll() {
        List<Movement> movementList = movementRepository.findAll();
        return movementList.stream().map(movement -> new MovementDTO(movement)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MovementDTO findByMovementId(Long id) {
        Optional<Movement> movementOptional = movementRepository.findById(id);
        Movement movement = movementOptional
                .orElseThrow(() -> new ResourceNotFoundException("Movimento não encontrado!"));
        return new MovementDTO(movement);
    }

    @Transactional(readOnly = true)
    public List<MovementDTO> findByProductId(Long id) {
        List<Movement> movementList = movementRepository.findByProductId(id);
        return movementList.stream().map(movement -> new MovementDTO(movement)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MovementDTO> findByTypeMovementId(Long id) {
        List<Movement> movementList = movementRepository.findByTypeMovementId(id);
        return movementList.stream().map(movement -> new MovementDTO(movement)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MovementDTO> findByDateBetween(Instant startDate, Instant endDate) {
        logger.warn("startDate: " + startDate);
        logger.warn("endDate: " + endDate);
        List<Movement> movementList = movementRepository.findByDateBetween(startDate, endDate);
        return movementList.stream().map(movement -> new MovementDTO(movement)).collect(Collectors.toList());
    }

    @Transactional
    public MovementCreateDTO create(MovementCreateDTO movementCreateDTO) {
        Movement movement = new Movement();
        Product product = new Product();

        CopyDtoToEntity.copyMovementDtoToMovement(movementCreateDTO, movement);
        CopyDtoToEntity.copyProductDtoToProduct(movementCreateDTO.getProduct(), product);
        adjustProductBalance(movementCreateDTO.getTypeMovement().getType(), movementCreateDTO.getQuantity(), product);
        adjustMovementSituation(movement, product);

        productRepository.save(product);
        movement = movementRepository.save(movement);

        return new MovementCreateDTO(movement);
    }

    private void adjustProductBalance(Character type, Integer quantity, Product product) {
        if (type == 'E') {
            product.setBalance(product.getBalance() + quantity);
        } else {
            product.setBalance(product.getBalance() - quantity);
        }
    }

    private void adjustMovementSituation(Movement movement, Product product) {
        if (movement.getProduct().getMinQuantity() > 0
                && movement.getProduct().getMinQuantity() > product.getBalance()) {
            movement.setSituation("Inferior ao Mínimo");
        } else {
            movement.setSituation("Ok");
        }
    }

}
