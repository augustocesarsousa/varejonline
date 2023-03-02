package br.comvarejonline.projetoinicial.dtos;

import br.comvarejonline.projetoinicial.entities.Movement;
import br.comvarejonline.projetoinicial.services.validation.MovementCreateValid;

@MovementCreateValid
public class MovementCreateDTO extends MovementDTO {

    private static final long serialVersionUID = 1L;

    public MovementCreateDTO() {
        super();
    }

    public MovementCreateDTO(Movement movement) {
        this.id = movement.getId();
        this.product = new ProductDTO(movement.getProduct());
        this.typeMovement = new TypeMovementDTO(movement.getTypeMovement());
        this.user = new UserDTO(movement.getUser());
        this.date = movement.getDate();
        this.reason = movement.getReason();
        this.document = movement.getDocument();
        this.quantity = movement.getQuantity();
        this.currentBalance = movement.getCurrentBalance();
        this.situation = movement.getSituation();
    }
}
