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

/*
 * Classe que implementa o contraint validator customizado de alteração da entidade Produto
 */
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

        // Pegando o id que vem na URL
        @SuppressWarnings("unchecked")
        Map<String, String> uriVars = (Map<String, String>) request
                .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        long productId = Long.parseLong(uriVars.get("id"));

        // Criando a lista onde serão adicionados os erros
        List<FieldMessage> list = new ArrayList<>();

        // Valida se o código de barras já existe em outro produto
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

        // Retorna se a lista possui erros
        return list.isEmpty();
    }
}
