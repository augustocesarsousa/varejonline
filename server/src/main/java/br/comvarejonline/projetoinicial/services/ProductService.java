package br.comvarejonline.projetoinicial.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.comvarejonline.projetoinicial.dtos.ProductDTO;
import br.comvarejonline.projetoinicial.entities.Product;
import br.comvarejonline.projetoinicial.repositories.ProductRepository;
import br.comvarejonline.projetoinicial.services.exceptions.HexCodeViolationException;
import br.comvarejonline.projetoinicial.services.exceptions.InitialBalanceViolationException;
import br.comvarejonline.projetoinicial.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {
        List<Product> productList = productRepository.findAll();
        return productList.stream().map(product -> new ProductDTO(product)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        Product product = productOptional.orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado!"));
        return new ProductDTO(product);
    }

    @Transactional
    public ProductDTO create(ProductDTO productDTO) {
        Product product = productRepository.findByHexCode(productDTO.getHexCode());
        if (product != null) {
            throw new HexCodeViolationException(
                    "Código de barras já cadastrado para o produto id = " + product.getId() + "!");
        }
        if (productDTO.getBalance() < productDTO.getMinQuantity()) {
            throw new InitialBalanceViolationException(
                    "Saldo inicial não pode ser menor que a quantidade mínima!");
        }
        product = new Product();
        copyDtoToEntity(product, productDTO);
        product = productRepository.save(product);
        return new ProductDTO(product);
    }

    public void copyDtoToEntity(Product product, ProductDTO productDTO) {
        product.setName(productDTO.getName());
        product.setHexCode(productDTO.getHexCode());
        product.setMinQuantity(productDTO.getMinQuantity());
        product.setBalance(productDTO.getBalance());
    }
}
