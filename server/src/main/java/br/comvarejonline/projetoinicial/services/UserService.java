package br.comvarejonline.projetoinicial.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.comvarejonline.projetoinicial.dtos.UserDTO;
import br.comvarejonline.projetoinicial.entities.User;
import br.comvarejonline.projetoinicial.repositories.UserRepository;
import br.comvarejonline.projetoinicial.services.exceptions.ResourceNotFoundException;

/*
 * Serviço da entidade Usuário implementando a interface UserDetailsService exigida pelo Spring Security
 */
@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Consulta usuário por id
    @Transactional
    public UserDTO findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        User user = userOptional.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
        return new UserDTO(user);
    }

    // Sobrescreve o método do UserDetailsService para consultar usuário por email
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado!");
        }
        return user;
    }

}
