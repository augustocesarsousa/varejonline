package br.comvarejonline.projetoinicial.repositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.comvarejonline.projetoinicial.entities.Movement;

/*
 * Repositório da entidade Movimento
 */

public interface MovementRepository extends JpaRepository<Movement, Long> {

    // Consulta movimentos por id do produto
    List<Movement> findByProductId(Long id);

    // Consulta movimentos por id do tipo de movimento
    List<Movement> findByTypeMovementId(Long id);

    // Consulta movimentos entre um intervalo de datas
    List<Movement> findByDateBetween(Instant startDate, Instant endDate);

    // Consulta movimentos por id do tipo de movimento e id do produto
    List<Movement> findByTypeMovementIdAndProductId(Long typeMovementId, Long productId);

}
