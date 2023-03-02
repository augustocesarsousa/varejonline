package br.comvarejonline.projetoinicial.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.comvarejonline.projetoinicial.controllers.exceptions.FieldMessage;
import br.comvarejonline.projetoinicial.dtos.ProductCreateDTO;
import br.comvarejonline.projetoinicial.entities.Product;
import br.comvarejonline.projetoinicial.repositories.ProductRepository;

/*
 * Classe que implementa o contraint validator customizado de alteração da entidade Produto
 */
public class ProductCreateValidator implements ConstraintValidator<ProductCreateValid, ProductCreateDTO> {

    private ProductRepository productRepository;

    public ProductCreateValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void initialize(ProductCreateValid ann) {
    }

    @Override
    public boolean isValid(ProductCreateDTO productDTO, ConstraintValidatorContext context) {

        // Criando a lista onde serão adicionados os erros
        List<FieldMessage> list = new ArrayList<>();

        Product product = productRepository.findByHexCode(productDTO.getHexCode());

        // Valida se o código de barras já
        if (product != null) {
            list.add(new FieldMessage("hexCode",
                    "Código de barras já cadastrado para o produto id = " + product.getId() + "!"));
        }

        // Valida se o saldo inicial é menor que a quantidade mínima
        if (productDTO.getBalance() < productDTO.getMinQuantity()) {
            list.add(new FieldMessage("balance",
                    "Saldo inicial não pode ser menor que a quantidade mínima!"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
