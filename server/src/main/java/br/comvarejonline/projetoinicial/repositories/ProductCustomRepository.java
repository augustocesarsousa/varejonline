package br.comvarejonline.projetoinicial.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import br.comvarejonline.projetoinicial.entities.Product;

/*
 * Repositório customizado da entidade Movimento
 */

@Repository
public class ProductCustomRepository {

    // private static Logger logger =
    // LoggerFactory.getLogger(MovementCustomRepository.class);

    private final EntityManager entityManager;

    public ProductCustomRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Page<Product> findByFilter(Long productId, String productName, String productHexCode,
            Pageable pageable) {
        Sort sort = pageable.getSort();

        String orderBy = sort.stream()
                .map(order -> order.getProperty() + " " + order.getDirection().name())
                .reduce((s1, s2) -> s1 + ", " + s2)
                .orElse("");

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT p FROM Product p WHERE 1=1 ");

        if (productId != null) {
            sql.append("AND p.id = :productId ");
        }

        if (productName != null) {
            sql.append("AND LOWER(p.name) LIKE :productName ");
        }

        if (productHexCode != null) {
            sql.append("AND p.hexCode = :productHexCode ");
        }

        if (!"".equals(orderBy)) {
            sql.append("ORDER BY p." + orderBy);
        }

        // logger.warn("QUERY: " + sql.toString());
        TypedQuery<Product> typedQuery = entityManager.createQuery(sql.toString(), Product.class);

        if (productId != null) {
            typedQuery.setParameter("productId", productId);
        }

        if (productName != null) {
            typedQuery.setParameter("productName", "%" + productName + "%");
        }

        if (productHexCode != null) {
            typedQuery.setParameter("productHexCode", productHexCode);
        }

        // logger.warn("TYPED QUERY: " + typedQuery.toString());
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());
        List<Product> products = typedQuery.getResultList();
        long total = totalMovements();
        return new PageImpl<>(products, pageable, total);
    }

    private long totalMovements() {
        String sql = "SELECT COUNT(m) FROM Movement m";

        TypedQuery<Long> query = entityManager.createQuery(sql, Long.class);

        return query.getSingleResult();
    }
}
