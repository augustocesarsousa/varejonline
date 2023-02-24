package br.comvarejonline.projetoinicial.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import br.comvarejonline.projetoinicial.entities.Product;
import br.comvarejonline.projetoinicial.services.validation.ProductUpdateValid;

@ProductUpdateValid
public class ProductUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Long id;

    @NotBlank(message = "Campo obrigatório!")
    protected String name;

    @Pattern(regexp = "[0-9]{13}", message = "O código de barras precisa ter 13 dígitos numéricos!")
    @NotBlank(message = "Campo obrigatório")
    protected String hexCode;

    public ProductUpdateDTO() {
    }

    public ProductUpdateDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.hexCode = product.getHexCode();
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

}
