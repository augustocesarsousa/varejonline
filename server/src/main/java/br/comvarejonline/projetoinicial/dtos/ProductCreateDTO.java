package br.comvarejonline.projetoinicial.dtos;

import br.comvarejonline.projetoinicial.entities.Product;
import br.comvarejonline.projetoinicial.services.validation.ProductCreateValid;

@ProductCreateValid
public class ProductCreateDTO extends ProductDTO {

    private static final long serialVersionUID = 1L;

    public ProductCreateDTO() {
        super();
    }

    public ProductCreateDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.hexCode = product.getHexCode();
        this.minQuantity = product.getMinQuantity();
        this.balance = product.getBalance();
    }

}