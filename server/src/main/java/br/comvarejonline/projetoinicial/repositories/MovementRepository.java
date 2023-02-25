package br.comvarejonline.projetoinicial.repositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.comvarejonline.projetoinicial.entities.Movement;

public interface MovementRepository extends JpaRepository<Movement, Long> {

    List<Movement> findByProductId(Long id);

    List<Movement> findByTypeMovementId(Long id);

    List<Movement> findByDateBetween(Instant startDate, Instant endDate);
}
