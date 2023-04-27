package br.comvarejonline.projetoinicial.services;

import br.comvarejonline.projetoinicial.dtos.ProductCreateDTO;
import br.comvarejonline.projetoinicial.dtos.ProductDTO;
import br.comvarejonline.projetoinicial.dtos.ProductUpdateDTO;
import br.comvarejonline.projetoinicial.entities.Product;
import br.comvarejonline.projetoinicial.repositories.*;
import br.comvarejonline.projetoinicial.services.exceptions.ResourceNotFoundException;
import br.comvarejonline.projetoinicial.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductCustomRepository productCustomRepository;

    private Product product;
    private ProductDTO productDTO;
    private ProductCreateDTO productCreateDTO;
    private ProductUpdateDTO productUpdateDTO;
    private long existingId;
    private long noExistingId;
    private Pageable pageable;
    private PageImpl<Product> page;

    @BeforeEach
    void setUp() throws Exception {
        product = Factory.createProductTest();
        productDTO = Factory.createProductDtoTest();
        productCreateDTO = Factory.createProductCreateDtoTest();
        productUpdateDTO = Factory.createProductUpdateDtoTest();
        existingId = 1L;
        noExistingId = 9999L;
        pageable = PageRequest.of(0, 10);
        page = new PageImpl<>(List.of(product));

        when(productRepository.save(any())).thenReturn(product);
        when(productRepository.findById(existingId)).thenReturn(Optional.of(product));
        when(productCustomRepository.findByFilter(null, null, null, pageable)).thenReturn(page);
        doNothing().when(productRepository).deleteById(existingId);
        doThrow(EmptyResultDataAccessException.class).when(productRepository).deleteById(noExistingId);
    }

    @Test
    public void createShouldReturnProductDtoWhenIdIsNullAndBalanceIsZero() {
        productCreateDTO.setId(null);
        productCreateDTO.setBalance(0);

        ProductDTO result = productService.create(productCreateDTO);

        Assertions.assertNotNull(result.getId());
    }

    @Test
    public void findByIdShouldReturnProductDtoWhenIdExists() {
        ProductDTO result = productService.findById(existingId);

        Assertions.assertNotNull(result);
        verify(productRepository, times(1)).findById(existingId);
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.findById(noExistingId);
        });
        verify(productRepository, times(1)).findById(noExistingId);
    }

    @Test
    public void findByFilterPagedShouldReturnPage() {
        Page<ProductDTO> result = productService.findByFilterPaged(null, null, null, pageable);

        Assertions.assertNotNull(result);
    }

    @Test
    public void updateShouldReturnProductUpdateDtoWhenIdExists() {
        ProductUpdateDTO result = productService.update(existingId, productUpdateDTO);

        Assertions.assertNotNull(result);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.update(noExistingId, productUpdateDTO);
        });
    }
}
