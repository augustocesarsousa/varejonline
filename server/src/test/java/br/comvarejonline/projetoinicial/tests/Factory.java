package br.comvarejonline.projetoinicial.tests;

import br.comvarejonline.projetoinicial.dtos.ProductCreateDTO;
import br.comvarejonline.projetoinicial.dtos.ProductDTO;
import br.comvarejonline.projetoinicial.dtos.ProductUpdateDTO;
import br.comvarejonline.projetoinicial.dtos.UserDTO;
import br.comvarejonline.projetoinicial.entities.*;

import java.time.Instant;

public class Factory {

    public static Product createProductTest(){
        return new Product(
                1l,
                "Product test",
                "9999999999999",
                1,
                0,
                Instant.now()
        );
    }

    public static ProductDTO createProductDtoTest() {
        return new ProductDTO(createProductTest());
    }

    public static ProductCreateDTO createProductCreateDtoTest() {
        return new ProductCreateDTO(createProductTest());
    }

    public static ProductUpdateDTO createProductUpdateDtoTest() {
        return new ProductUpdateDTO(createProductTest());
    }

    public static Role createRoleTest() {
        return new Role(1L, "ROLE_MANAGER");
    }

    public static User createUserTest() {
        return new User(
                1L,
                "User Test",
                "test@email.com",
                "123456",
                createRoleTest()
        );
    }

    public static UserDTO createuserDtoTest() {
        return new UserDTO(createUserTest());
    }

    public static TypeMovement createTypeMovementTest() {
        return new TypeMovement(
                1L,
                "TypeMovement Test",
                "Test",
                'E',
                createRoleTest()
        );
    }

    public static Movement createMovementTest() {
        return new Movement(
                1L,
                createProductTest(),
                createTypeMovementTest(),
                createUserTest(),
                Instant.now(),
                "Test",
                1L,
                1,
                "Ok"
        );
    }
}
