package br.comvarejonline.projetoinicial.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.comvarejonline.projetoinicial.entities.Product;

/**
 * ProductRepository
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByHexCode(String hexCode);
}