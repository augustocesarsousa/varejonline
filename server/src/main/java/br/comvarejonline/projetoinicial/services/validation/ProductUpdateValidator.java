package br.comvarejonline.projetoinicial.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.servlet.HandlerMapping;

import br.comvarejonline.projetoinicial.controllers.exceptions.FieldMessage;
import br.comvarejonline.projetoinicial.dtos.ProductUpdateDTO;
import br.comvarejonline.projetoinicial.entities.Product;
import br.comvarejonline.projetoinicial.repositories.ProductRepository;

public class ProductUpdateValidator implements ConstraintValidator<ProductUpdateValid, ProductUpdateDTO> {

    private ProductRepository productRepository;

    private HttpServletRequest request;

    public ProductUpdateValidator(ProductRepository productRepository, HttpServletRequest request) {
        this.productRepository = productRepository;
        this.request = request;
    }

    @Override
    public void initialize(ProductUpdateValid ann) {
    }

    @Override
    public boolean isValid(ProductUpdateDTO productDTO, ConstraintValidatorContext context) {

        @SuppressWarnings("unchecked")
        var uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        long productId = Long.parseLong(uriVars.get("id"));

        List<FieldMessage> list = new ArrayList<>();

        Product product = productRepository.findByHexCode(productDTO.getHexCode());

        if (product != null && productId != product.getId()) {
            list.add(new FieldMessage("hexCode",
                    "Código de barras já cadastrado para o produto id = " + product.getId() + "!"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
