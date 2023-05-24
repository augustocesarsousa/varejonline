package br.comvarejonline.projetoinicial.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.comvarejonline.projetoinicial.dtos.ProductCreateDTO;
import br.comvarejonline.projetoinicial.dtos.ProductDTO;
import br.comvarejonline.projetoinicial.dtos.ProductUpdateDTO;
import br.comvarejonline.projetoinicial.services.ProductService;
import br.comvarejonline.projetoinicial.services.exceptions.ResourceNotFoundException;
import br.comvarejonline.projetoinicial.tests.Factory;
import br.comvarejonline.projetoinicial.tests.TokenUtil;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTests {

    @MockBean
    private ProductService productService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TokenUtil tokenUtil;

    private ProductDTO productDTO;
    private ProductCreateDTO productCreateDTO;
    private ProductUpdateDTO productUpdateDTO;
    private PageImpl<ProductDTO> page;
    private long existingId;
    private long noExistingId;
    private String username;
    private String password;
    private String accessToken;

    @BeforeEach
    void setUp() throws Exception {
        productDTO = Factory.createProductDtoTest();
        productCreateDTO = Factory.createProductCreateDtoTest();
        productUpdateDTO = Factory.createProductUpdateDtoTest();
        page = new PageImpl<>(List.of(productDTO));
        existingId = 1L;
        noExistingId = 9999L;
        username = "gerente@email.com";
        password = "123456";
        accessToken = tokenUtil.obtainAccessToken(mockMvc, username, password);

        when(productService.findByFilterPaged(any(), any(), any(), any())).thenReturn(page);
        when(productService.findById(existingId)).thenReturn(productDTO);
        when(productService.findById(noExistingId)).thenThrow(ResourceNotFoundException.class);
        when(productService.create(any())).thenReturn(productCreateDTO);
        when(productService.update(eq(existingId), any())).thenReturn(productUpdateDTO);
        when(productService.update(eq(noExistingId), any())).thenThrow(ResourceNotFoundException.class);
    }

    @Test
    public void findByFilterPagedShouldReturnPage() throws Exception {
        mockMvc.perform(get("/product/filter")
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.pageable").exists());
    }

    @Test
    public void findByIdShouldReturnProductWhenIdExists() throws Exception {
        mockMvc.perform(get("/product/{id}", existingId)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void createShouldReturnCreated() throws Exception {
        productCreateDTO.setBalance(1);
        String jsonBody = objectMapper.writeValueAsString(productCreateDTO);

        mockMvc.perform(post("/product")
                .header("Authorization", "Bearer " + accessToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void createShouldReturnUnprocessableEntityWhenNomeIsInvalid() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(productCreateDTO);

        mockMvc.perform(post("/product")
                .header("Authorization", "Bearer " + accessToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors[0].message")
                        .value("Saldo inicial não pode ser menor que a quantidade mínima!"));
    }

    @Test
    public void updateShouldReturnProductDtoWhenIdExists() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(productCreateDTO);

        mockMvc.perform(put("/product/{id}", existingId)
                .header("Authorization", "Bearer " + accessToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void updateShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(productCreateDTO);

        mockMvc.perform(put("/product/{id}", noExistingId)
                .header("Authorization", "Bearer " + accessToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
