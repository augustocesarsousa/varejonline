package br.comvarejonline.projetoinicial.dtos;

import br.comvarejonline.projetoinicial.entities.Product;
import br.comvarejonline.projetoinicial.services.validation.ProductCreateValid;

/*
 * DTO para criação da entidade Produto adicioando a validator customizada
 */

// Anotação da constraint validator customizada
@ProductCreateValid
public class ProductCreateDTO extends ProductDTO {

    private static final long serialVersionUID = 1L;

    private Long userId;

    public ProductCreateDTO() {
        super();
    }

    public ProductCreateDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.hexCode = product.getHexCode();
        this.minQuantity = product.getMinQuantity();
        this.balance = product.getBalance();
        this.currentBalance = product.getCurrentBalance();
        this.createdAt = product.getCreatedAt();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
