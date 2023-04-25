package br.comvarejonline.projetoinicial.repositories;

import java.util.Optional;

import br.comvarejonline.projetoinicial.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.comvarejonline.projetoinicial.entities.Product;
import org.springframework.dao.EmptyResultDataAccessException;

@DataJpaTest
public class ProductRepositoryTests {

    private final ProductRepository productRepository;
    private long totalProducts;
    private long existingId;
    private long noExistingId;

    // TODO pesquisar porque o Autowired é exigido
    @Autowired
    public ProductRepositoryTests(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @BeforeEach
    void setUp() throws Exception {
        totalProducts = 6L;
        existingId = 1L;
        noExistingId = 9999L;
    }

    @Test
    public void findByIdShouldReturnProductWhenIdExists() {
        Optional<Product> result = productRepository.findById(existingId);

        Assertions.assertTrue(result.isPresent());
    }

    @Test
    public void findByIdShouldNotReturnProductWhenIdNonExists() {
        Optional<Product> result = productRepository.findById(noExistingId);

        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void saveShouldPersistsWithAutoincrementWhenIdIsNull() {
        Product product = Factory.createProductTest();
        product.setId(null);

        product = productRepository.save(product);

        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(totalProducts + 1, product.getId());
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {
        productRepository.deleteById(existingId);

        Optional<Product> product = productRepository.findById(existingId);

        Assertions.assertFalse(product.isPresent());
    }

    @Test
    public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExists() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            productRepository.deleteById(noExistingId);
        });
    }

}
