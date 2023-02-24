package br.comvarejonline.projetoinicial.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.comvarejonline.projetoinicial.dtos.ProductCreateDTO;
import br.comvarejonline.projetoinicial.dtos.ProductDTO;
import br.comvarejonline.projetoinicial.dtos.ProductUpdateDTO;
import br.comvarejonline.projetoinicial.entities.Product;
import br.comvarejonline.projetoinicial.repositories.ProductRepository;
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
    public ProductCreateDTO create(ProductCreateDTO productDTO) {
        Product product = new Product();
        copyDtoToEntity(product, productDTO);
        product = productRepository.save(product);
        return new ProductCreateDTO(product);
    }

    @Transactional
    public ProductUpdateDTO update(Long id, ProductUpdateDTO productDTO) {
        try {
            Product product = productRepository.getById(id);
            product.setName(productDTO.getName());
            product.setHexCode(productDTO.getHexCode());
            product = productRepository.save(product);
            return new ProductUpdateDTO(product);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Produto não encontrado = " + id);
        }
    }

    public void copyDtoToEntity(Product product, ProductDTO productDTO) {
        product.setName(productDTO.getName());
        product.setHexCode(productDTO.getHexCode());
        product.setMinQuantity(productDTO.getMinQuantity());
        product.setBalance(productDTO.getBalance());
    }
}
