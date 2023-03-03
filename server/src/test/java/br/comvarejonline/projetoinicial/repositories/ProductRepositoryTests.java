package br.comvarejonline.projetoinicial.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.comvarejonline.projetoinicial.entities.Product;

@DataJpaTest
public class ProductRepositoryTests {

    private final ProductRepository productRepository;

    // TODO pesquisar porque o Autowired é exigido
    @Autowired
    public ProductRepositoryTests(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Test
    public void findByIdShouldReturnProductWhenIdExists() {

        long productId = 1L;

        Optional<Product> result = productRepository.findById(productId);

        Assertions.assertTrue(result.isPresent());

    }

}
