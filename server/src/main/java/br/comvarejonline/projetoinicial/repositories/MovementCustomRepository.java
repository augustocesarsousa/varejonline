package br.comvarejonline.projetoinicial.repositories;

import java.time.Instant;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import br.comvarejonline.projetoinicial.entities.Movement;

/*
 * Repositório customizado da entidade Movimento
 */

@Repository
public class MovementCustomRepository {

//    private static Logger logger = LoggerFactory.getLogger(MovementCustomRepository.class);

    private final EntityManager entityManager;

    public MovementCustomRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Page<Movement> findByFilter(Long productId, Instant startDate, Instant endDate, Long typeMovementId,
            Pageable pageable) {
        Sort sort = pageable.getSort();

        String orderBy = sort.stream()
                .map(order -> order.getProperty() + " " + order.getDirection().name())
                .reduce((s1, s2) -> s1 + ", " + s2)
                .orElse("");

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT m FROM Movement m WHERE 1=1 ");

        if (productId != null) {
            sql.append("AND m.product.id = :productId ");
        }

        if (startDate != null && endDate != null) {
            sql.append("AND m.date BETWEEN :startDate and :endDate ");
        } else if (startDate != null) {
            sql.append("AND m.date >= :startDate ");
        } else if (endDate != null) {
            sql.append("AND m.date <= :endDate ");
        }

        if (typeMovementId != null) {
            sql.append("AND m.typeMovement.id = :typeMovementId ");
        }

        if (!"".equals(orderBy)) {
            sql.append("ORDER BY f." + orderBy);
        }

//        logger.warn("QUERY: " + sql.toString());
        TypedQuery<Movement> typedQuery = entityManager.createQuery(sql.toString(), Movement.class);

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

        // logger.warn("TYPED QUERY: " + typedQuery.toString());
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());
        List<Movement> movements = typedQuery.getResultList();
        long total = totalMovements();
        return new PageImpl<>(movements, pageable, total);
    }

    private long totalMovements() {
        String sql = "SELECT COUNT(m) FROM Movement m";

        var query = entityManager.createQuery(sql, Long.class);

        return (Long) query.getSingleResult();
    }
}
