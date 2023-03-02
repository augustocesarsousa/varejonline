package br.comvarejonline.projetoinicial.dtos;

import java.io.Serializable;

import br.comvarejonline.projetoinicial.entities.User;

/*
 * DTO da entidade Usuário
 */
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String email;
    private RoleDTO role;

    public UserDTO() {
    }

    public UserDTO(Long id, String name, String email, RoleDTO role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = new RoleDTO(user.getRole());
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

}
