package br.comvarejonline.projetoinicial.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import br.comvarejonline.projetoinicial.entities.Product;

public class ProductDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Long id;

    @NotBlank(message = "Campo obrigatório!")
    protected String name;

    @Pattern(regexp = "[0-9]{13}", message = "O código de barras precisa ter 13 dígitos numéricos!")
    @NotBlank(message = "Campo obrigatório")
    protected String hexCode;

    @PositiveOrZero(message = "Digite um valor positivo!")
    protected Integer minQuantity;

    @PositiveOrZero(message = "Digite um valor positivo!")
    protected Integer balance;

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, String hexCode,
            Integer minQuantity, Integer balance) {
        this.id = id;
        this.name = name;
        this.hexCode = hexCode;
        this.minQuantity = minQuantity;
        this.balance = balance;
    }

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.hexCode = product.getHexCode();
        this.minQuantity = product.getMinQuantity();
        this.balance = product.getBalance();
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

}
