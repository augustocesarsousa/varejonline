package br.comvarejonline.projetoinicial.repositories;

import br.comvarejonline.projetoinicial.entities.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {


    private final ProductRepository productRepository;

    @Autowired
    public ProductRepositoryTests(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Test
    public void findByIdShouldReturnProductWhenIdExists(){

        long productId = 1L;

        Optional<Product> result = productRepository.findById(productId);

        Assertions.assertTrue(result.isPresent());

    }

}
