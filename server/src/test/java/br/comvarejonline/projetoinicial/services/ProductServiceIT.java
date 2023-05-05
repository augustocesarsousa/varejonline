package br.comvarejonline.projetoinicial.services;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.comvarejonline.projetoinicial.dtos.ProductCreateDTO;
import br.comvarejonline.projetoinicial.dtos.ProductDTO;
import br.comvarejonline.projetoinicial.dtos.ProductUpdateDTO;
import br.comvarejonline.projetoinicial.services.exceptions.ResourceNotFoundException;
import br.comvarejonline.projetoinicial.tests.Factory;

@SpringBootTest
@Transactional
public class ProductServiceIT {

    @Autowired
    private ProductService productService;

    private ProductCreateDTO productCreateDTO;
    private ProductUpdateDTO productUpdateDTO;
    private long existingId;
    private long noExistingId;

    @BeforeEach
    void setUp() throws Exception {
        productCreateDTO = Factory.createProductCreateDtoTest();
        productUpdateDTO = Factory.createProductUpdateDtoTest();
        existingId = 1L;
        noExistingId = 9999L;
    }

    @Test
    public void createShouldReturnFuncionarioCreatDto() {
        ProductCreateDTO result = productService.create(productCreateDTO);

        Assertions.assertNotNull(result);
    }

    @Test
    public void findByIdShouldReturnProductDtoWhenIdExists() {
        ProductDTO result = productService.findById(existingId);

        Assertions.assertNotNull(result);
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.findById(noExistingId);
        });
    }

    @Test
    public void findByFilterPagedShouldReturnPageWhenPage0Size10() {
        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<ProductDTO> result = productService.findByFilterPaged(null, null, null, pageRequest);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertFalse(result.getContent().isEmpty());
        Assertions.assertEquals(0, result.getNumber());
        Assertions.assertEquals(10, result.getSize());
    }

    @Test
    public void findByFilterPagedShouldReturnEmptyPageWhenPageDoesNotExists() {
        PageRequest pageRequest = PageRequest.of(100, 10);

        Page<ProductDTO> result = productService.findByFilterPaged(null, null, null, pageRequest);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void updateShouldReturnProductDtoWhenIdExists() {
        ProductUpdateDTO result = productService.update(existingId, productUpdateDTO);

        Assertions.assertNotNull(result);
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.update(noExistingId, productUpdateDTO);
        });
    }

}