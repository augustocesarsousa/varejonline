package br.comvarejonline.projetoinicial.tests;

import br.comvarejonline.projetoinicial.entities.Product;

import java.time.Instant;

public class Factory {

    public static Product createProductTest(){
        return new Product(
                1l,
                "Product test",
                "9999999999999",
                1,
                1,
                Instant.now()
        );
    }
}
