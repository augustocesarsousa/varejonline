package br.comvarejonline.projetoinicial.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.comvarejonline.projetoinicial.entities.TypeMovement;

import java.util.List;

public interface TypeMovementRepository extends JpaRepository<TypeMovement, Long> {

    List<TypeMovement> findByRoleAuthority(String roleAuthority);
}
