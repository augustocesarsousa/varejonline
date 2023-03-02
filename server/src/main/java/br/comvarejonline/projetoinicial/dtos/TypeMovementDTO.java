package br.comvarejonline.projetoinicial.dtos;

import java.io.Serializable;

import br.comvarejonline.projetoinicial.entities.TypeMovement;

/*
 * DTO da entidade Tipo de Movimento
 */
public class TypeMovementDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String description;
    private Character type;
    private RoleDTO role;

    public TypeMovementDTO() {
    }

    public TypeMovementDTO(Long id, String description, Character type, RoleDTO role) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.role = role;
    }

    public TypeMovementDTO(TypeMovement typeMovement) {
        this.id = typeMovement.getId();
        this.description = typeMovement.getDescription();
        this.type = typeMovement.getType();
        this.role = new RoleDTO(typeMovement.getRole());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Character getType() {
        return type;
    }

    public void setType(Character type) {
        this.type = type;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

}
