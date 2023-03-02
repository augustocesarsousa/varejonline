package br.comvarejonline.projetoinicial.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.comvarejonline.projetoinicial.controllers.exceptions.FieldMessage;
import br.comvarejonline.projetoinicial.dtos.MovementCreateDTO;
import br.comvarejonline.projetoinicial.entities.Movement;
import br.comvarejonline.projetoinicial.entities.Product;
import br.comvarejonline.projetoinicial.repositories.MovementRepository;
import br.comvarejonline.projetoinicial.repositories.ProductRepository;

public class MovementCreateValidator implements ConstraintValidator<MovementCreateValid, MovementCreateDTO> {

    private MovementRepository movementRepository;
    private ProductRepository productRepository;

    public MovementCreateValidator(MovementRepository movementRepository, ProductRepository productRepository) {
        this.movementRepository = movementRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void initialize(MovementCreateValid ann) {
    }

    @Override
    public boolean isValid(MovementCreateDTO movementDTO, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        if (movementDTO.getUser().getRole().getId() < movementDTO.getTypeMovement().getRole().getId()) {
            list.add(new FieldMessage("id",
                    "Operação não realizada, usuário não possui autorização para executar essa movimentação!"));
        }

        if (movementDTO.getTypeMovement().getId() == 1) {
            List<Movement> movementList = movementRepository.findByProductId(movementDTO.getProduct().getId());
            if (!movementList.isEmpty()) {
                list.add(new FieldMessage("typeMovement", "Operação não realizada, produto já possui movimentação!"));
            }
        }

        if (movementDTO.getTypeMovement().getId() == 4 || movementDTO.getTypeMovement().getId() == 5) {
            List<Movement> movementList = movementRepository.findByProductId(movementDTO.getProduct().getId());
            if (movementList.isEmpty()) {
                list.add(new FieldMessage("typeMovement", "Operação não realizada, produto não possui movimentação!"));
            }
        }

        if (movementDTO.getTypeMovement().getType() == 'S'
                && movementDTO.getQuantity() > movementDTO.getProduct().getCurrentBalance()) {
            list.add(new FieldMessage("quantity", "Operação não realizada, produto não possui saldo suficiente!"));
        }

        Optional<Product> productOptional = productRepository.findById(movementDTO.getProduct().getId());
        Product product = productOptional.get();

        if (movementDTO.getDate().compareTo(product.getCreatedAt()) < 0) {
            list.add(new FieldMessage("date",
                    "Operação não realizada, produto não pode ter movimentação anterior a sua criação!"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
