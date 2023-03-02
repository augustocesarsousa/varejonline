package br.comvarejonline.projetoinicial.dtos;

import java.io.Serializable;
import java.time.Instant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import br.comvarejonline.projetoinicial.entities.Product;

/*
 * DTO da entidade Produto adicioando as costraints validations
 */
public class ProductDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Long id;

    // Valida que o nome é obrigatório
    @NotBlank(message = "Campo obrigatório!")
    protected String name;

    // Regex que valida a estrutura do código de barras
    @Pattern(regexp = "[0-9]{13}", message = "O código de barras precisa ter 13 dígitos numéricos!")
    // Valida que o código de barras é obrigatório
    @NotBlank(message = "Campo obrigatório")
    protected String hexCode;

    // Valida valida que a quantidade mínima é positiva
    @PositiveOrZero(message = "Digite um valor positivo!")
    protected Integer minQuantity;

    // Valida valida que o saldo é positivo
    @PositiveOrZero(message = "Digite um valor positivo!")
    protected Integer balance;

    protected Integer currentBalance;

    protected Instant createdAt;

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, String hexCode,
            Integer minQuantity, Integer balance, Instant createdAt, Integer currentBalance) {
        this.id = id;
        this.name = name;
        this.hexCode = hexCode;
        this.minQuantity = minQuantity;
        this.balance = balance;
        this.createdAt = createdAt;
        this.currentBalance = currentBalance;
    }

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.hexCode = product.getHexCode();
        this.minQuantity = product.getMinQuantity();
        this.balance = product.getBalance();
        this.createdAt = product.getCreatedAt();
        this.currentBalance = product.getCurrentBalance();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHexCode() {
        return hexCode;
    }

    public void setHexCode(String hexCode) {
        this.hexCode = hexCode;
    }

    public Integer getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(Integer minQuantity) {
        this.minQuantity = minQuantity;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Integer currentBalance) {
        this.currentBalance = currentBalance;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
