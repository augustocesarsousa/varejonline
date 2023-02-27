package br.comvarejonline.projetoinicial.repositories;

import java.time.Instant;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import br.comvarejonline.projetoinicial.entities.Movement;

@Repository
public class MovementCustomRepository {

    private static Logger logger = LoggerFactory.getLogger(MovementCustomRepository.class);

    private final EntityManager entityManager;

    public MovementCustomRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Movement> findByFilter(Long productId, Instant startDate, Instant endDate,
            Long typeMovementId) {

        String query = "from tb_movement M where 1=1 ";

        if (productId != null) {
            query += "and m.product_id = :productId ";
        }

        if (startDate != null && endDate != null) {
            query += "and m.data between :startDate and :endDate ";
        } else if (startDate != null) {
            query += "and m.data >= :startDate ";
        } else if (endDate != null) {
            query += "and m.data <= :endDate ";
        }

        if (typeMovementId != null) {
            query += "and m.type_movement_id = :typeMovementId ";
        }

        logger.warn("QUERY: " + query.toString());
        TypedQuery<Movement> typedQuery = entityManager.createQuery(query, Movement.class);

        if (productId != null) {
            typedQuery.setParameter("productId", productId);
        }

        if (startDate != null && endDate != null) {
            typedQuery.setParameter("startDate", startDate);
            typedQuery.setParameter("endDate", endDate);
        } else if (startDate != null) {
            typedQuery.setParameter("startDate", startDate);
        } else if (endDate != null) {
            typedQuery.setParameter("endDate", endDate);
        }

        if (typeMovementId != null) {
            typedQuery.setParameter("typeMovementId", typeMovementId);
        }

        return typedQuery.getResultList();
    }
}
