package br.comvarejonline.projetoinicial.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import br.comvarejonline.projetoinicial.dtos.MovementDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.comvarejonline.projetoinicial.dtos.ProductCreateDTO;
import br.comvarejonline.projetoinicial.dtos.ProductDTO;
import br.comvarejonline.projetoinicial.dtos.ProductUpdateDTO;
import br.comvarejonline.projetoinicial.services.ProductService;

/*
 * Controller da entidade Produto
 */
@RestController
// Definindo endpoint para /product
@RequestMapping(value = "/product")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Endpoind que retorna todos os produtos
    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll() {
        List<ProductDTO> productList = productService.findAll();
        return ResponseEntity.ok().body(productList);
    }

    @GetMapping(value = "/filter")
    public ResponseEntity<Page<ProductDTO>> findByFilterPaged(
            @RequestParam(value = "productId", required = false) String productId,
            @RequestParam(value = "productName", required = false) String productName,
            @RequestParam(value = "productHexCode", required = false) String productHexCode,
            @PageableDefault Pageable pageable) {
//        logger.warn("PAGEABLE: " + pageable.toString());
        Page<ProductDTO> productDTO = productService.findByFilterPaged(productId, productName, productHexCode,
                pageable);

        return ResponseEntity.ok().body(productDTO);
    }

    // Endpoind que retorna um produto por id
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        ProductDTO product = productService.findById(id);
        return ResponseEntity.ok().body(product);
    }

    // Endpoind para criar um produto
    @PostMapping
    public ResponseEntity<ProductCreateDTO> create(@Valid @RequestBody ProductCreateDTO productDTO) {
        productDTO = productService.create(productDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(productDTO.getId())
                .toUri();
        return ResponseEntity.created(uri).body(productDTO);
    }

    // Endpoind para atualiza um produto
    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductUpdateDTO> update(@PathVariable Long id,
            @Valid @RequestBody ProductUpdateDTO productDTO) {
        productDTO = productService.update(id, productDTO);
        return ResponseEntity.ok().body(productDTO);
    }
}