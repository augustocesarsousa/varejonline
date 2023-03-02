package br.comvarejonline.projetoinicial.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.comvarejonline.projetoinicial.entities.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * ProductRepository
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByHexCode(String hexCode);

    //void updateCurrentBalanceById(Long id, Integer currentBalance);

    @Modifying
    @Query("UPDATE Product p SET p.currentBalance = :currentBalance WHERE p.id = :id")
    void updateCurrentBalanceById(@Param("id") Long id, @Param("currentBalance") Integer current_balance);
}