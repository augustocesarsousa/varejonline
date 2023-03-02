package br.comvarejonline.projetoinicial.dtos;

import java.io.Serializable;
import java.time.Instant;

import br.comvarejonline.projetoinicial.entities.Movement;

/*
 * DTO da entidade Movimento
 */
public class MovementDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Long id;
    protected ProductDTO product;
    protected TypeMovementDTO typeMovement;
    protected UserDTO user;
    protected Instant date;
    protected String reason;
    protected Long document;
    protected Integer quantity;
    protected Integer currentBalance;
    protected String situation;

    public MovementDTO() {
    }

    public MovementDTO(Long id, ProductDTO product, TypeMovementDTO type, UserDTO user, Instant date, String reason,
            Long document, Integer quantity, Integer currentBalance, String situation) {
        this.id = id;
        this.product = product;
        this.typeMovement = type;
        this.user = user;
        this.date = date;
        this.reason = reason;
        this.document = document;
        this.quantity = quantity;
        this.currentBalance = currentBalance;
        this.situation = situation;
    }

    public MovementDTO(Movement movement) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public TypeMovementDTO getTypeMovement() {
        return typeMovement;
    }

    public void setTypeMovement(TypeMovementDTO type) {
        this.typeMovement = type;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getDocument() {
        return document;
    }

    public void setDocument(Long document) {
        this.document = document;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Integer currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

}
