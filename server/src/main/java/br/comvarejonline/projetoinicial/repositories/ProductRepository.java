package br.comvarejonline.projetoinicial.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.comvarejonline.projetoinicial.entities.Product;

/*
 * Repositório da entidade Produto
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Consulta produto por código de barras
    Product findByHexCode(String hexCode);

    // Atualiza o balanço atual do produto pelo id do produto
    @Modifying
    @Query("UPDATE Product p SET p.currentBalance = :currentBalance WHERE p.id = :id")
    void updateCurrentBalanceById(@Param("id") Long id, @Param("currentBalance") Integer current_balance);
}