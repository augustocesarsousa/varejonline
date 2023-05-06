package br.comvarejonline.projetoinicial.controllers;

import br.comvarejonline.projetoinicial.dtos.ProductCreateDTO;
import br.comvarejonline.projetoinicial.dtos.ProductUpdateDTO;
import br.comvarejonline.projetoinicial.tests.Factory;
import br.comvarejonline.projetoinicial.tests.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenUtil tokenUtil;

    private Long existingId;
    private Long noExistingId;
    private ProductCreateDTO productCreateDTO;
    private ProductUpdateDTO productUpdateDTO;
    private String username;
    private String password;
    private String accessToken;

    @BeforeEach
    void setUp() throws Exception {
        productCreateDTO = Factory.createProductCreateDtoTest();
        productUpdateDTO = Factory.createProductUpdateDtoTest();
        existingId = 1L;
        noExistingId = 9999L;
        username = "gerente@email.com";
        password = "123456";
        accessToken = tokenUtil.obtainAccessToken(mockMvc, username, password);
    }

    @Test
    public void createShouldReturnCreated() throws Exception {
        productCreateDTO.setMinQuantity(0);
        productCreateDTO.setUserId(1L);
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
    public void createShouldReturnUnprocessableEntityWhenBalanceIsLowerThanMinQuantity() throws Exception {
        productCreateDTO.setBalance(0);
        productCreateDTO.setUserId(1L);
        String jsonBody = objectMapper.writeValueAsString((productCreateDTO));

        mockMvc.perform(post("/product")
                        .header("Authorization", "Bearer " + accessToken)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors[0].message").value("Saldo inicial não pode ser menor que a quantidade mínima!"));
    }

    @Test
    public void findByFilterPagedShouldReturnSortedPageWhenSortByName() throws Exception{
        mockMvc.perform(get("/product/filter?page=0&size=10&sort=name,asc")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content[0].name").value("Fogão 5B Supreme Glass 570 Dako"))
                .andExpect(jsonPath("$.content[1].name").value("Refrigerador 384L RT38K5530S8/AZ Samsung"));
    }

    @Test
    public void findByIdShouldReturnProductWhenIdExists() throws Exception{
        mockMvc.perform(get("/product/{id}", existingId)
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {
        mockMvc.perform(get("/product/{id}", noExistingId)
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateShouldReturnProductUpdateDtoWhenIdExists() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(productUpdateDTO);

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
        String jsonBody = objectMapper.writeValueAsString(productUpdateDTO);

        mockMvc.perform(put("/product/{id}", noExistingId)
                        .header("Authorization", "Bearer " + accessToken)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
