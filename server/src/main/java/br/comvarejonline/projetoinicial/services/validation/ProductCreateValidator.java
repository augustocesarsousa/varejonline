package br.comvarejonline.projetoinicial.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.comvarejonline.projetoinicial.controllers.exceptions.FieldMessage;
import br.comvarejonline.projetoinicial.dtos.ProductCreateDTO;
import br.comvarejonline.projetoinicial.entities.Product;
import br.comvarejonline.projetoinicial.repositories.ProductRepository;

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

        List<FieldMessage> list = new ArrayList<>();

        Product product = productRepository.findByHexCode(productDTO.getHexCode());

        if (product != null) {
            list.add(new FieldMessage("hexCode",
                    "Código de barras já cadastrado para o produto id = " + product.getId() + "!"));
        }

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
