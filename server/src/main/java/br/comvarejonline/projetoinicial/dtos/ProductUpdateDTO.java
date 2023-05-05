package br.comvarejonline.projetoinicial.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import br.comvarejonline.projetoinicial.entities.Product;
import br.comvarejonline.projetoinicial.services.validation.ProductUpdateValid;

/*
 * DTO para alteração da entidade Produto adicioando as constraints validations
 */

// Anotação da constraint validator customizada
@ProductUpdateValid
public class ProductUpdateDTO extends ProductDTO {

    private static final long serialVersionUID = 1L;

//    protected Long id;
//
//    // Valida que o nome é obrigatório
//    @NotBlank(message = "Campo obrigatório!")
//    protected String name;
//
//    // Regex que valida a estrutura do código de barras
//    @Pattern(regexp = "[0-9]{13}", message = "O código de barras precisa ter 13 dígitos numéricos!")
//    // Valida que o código de barras é obrigatório
//    @NotBlank(message = "Campo obrigatório")
//    protected String hexCode;

    public ProductUpdateDTO() {super();}

    public ProductUpdateDTO(Product product) {
        super(product);
    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getHexCode() {
//        return hexCode;
//    }
//
//    public void setHexCode(String hexCode) {
//        this.hexCode = hexCode;
//    }

}
