package br.comvarejonline.projetoinicial.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.comvarejonline.projetoinicial.entities.User;

/*
 * Repositório da entidade Usuário
 */
public interface UserRepository extends JpaRepository<User, Long> {

    // Consulta usuário por email
    User findByEmail(String email);
}
