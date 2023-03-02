package br.comvarejonline.projetoinicial.utils;

import br.comvarejonline.projetoinicial.dtos.MovementDTO;
import br.comvarejonline.projetoinicial.dtos.ProductDTO;
import br.comvarejonline.projetoinicial.dtos.RoleDTO;
import br.comvarejonline.projetoinicial.dtos.TypeMovementDTO;
import br.comvarejonline.projetoinicial.dtos.UserDTO;
import br.comvarejonline.projetoinicial.entities.Movement;
import br.comvarejonline.projetoinicial.entities.Product;
import br.comvarejonline.projetoinicial.entities.Role;
import br.comvarejonline.projetoinicial.entities.TypeMovement;
import br.comvarejonline.projetoinicial.entities.User;

public abstract class CopyDtoToEntity {

    public static void copyMovementDtoToMovement(MovementDTO movementDTO, Movement movement) {
        Product product = new Product();
        copyProductDtoToProduct(movementDTO.getProduct(), product);

        TypeMovement typeMovement = new TypeMovement();
        copyTypeMovementDtoToTypeMovement(movementDTO.getTypeMovement(), typeMovement);

        User user = new User();
        copyUserDtoToUser(movementDTO.getUser(), user);

        movement.setId(movementDTO.getId());
        movement.setProduct(product);
        movement.setTypeMovement(typeMovement);
        movement.setUser(user);
        movement.setDate(movementDTO.getDate());
        movement.setDocument(movementDTO.getDocument());
        movement.setReason(movementDTO.getReason());
        movement.setQuantity(movementDTO.getQuantity());

    }

    public static void copyProductDtoToProduct(ProductDTO productDTO, Product product) {
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setHexCode(productDTO.getHexCode());
        product.setMinQuantity(productDTO.getMinQuantity());
        product.setBalance(productDTO.getBalance());
        product.setCreatedAt(productDTO.getCreatedAt());
    }

    public static void copyTypeMovementDtoToTypeMovement(TypeMovementDTO typeMovementDTO, TypeMovement typeMovement) {
        Role role = new Role();
        copyRoleDtoToRole(typeMovementDTO.getRole(), role);

        typeMovement.setId(typeMovementDTO.getId());
        typeMovement.setDescription(typeMovementDTO.getDescription());
        typeMovement.setRole(role);
        typeMovement.setType(typeMovementDTO.getType());
    }

    public static void copyUserDtoToUser(UserDTO userDTO, User user) {
        Role role = new Role();
        copyRoleDtoToRole(userDTO.getRole(), role);

        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setRole(role);
    }

    public static void copyRoleDtoToRole(RoleDTO roleDTO, Role role) {
        role.setId(roleDTO.getId());
        role.setAuthority(roleDTO.getAuthority());
    }
}
