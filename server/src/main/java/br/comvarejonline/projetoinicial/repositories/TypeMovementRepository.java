package br.comvarejonline.projetoinicial.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.comvarejonline.projetoinicial.entities.TypeMovement;

/*
 * Repositório da entidade Tipo de Movimento
 */
public interface TypeMovementRepository extends JpaRepository<TypeMovement, Long> {

    // Consulta tipos de movimento pela descrição
    List<TypeMovement> findByRoleAuthority(String roleAuthority);
}
