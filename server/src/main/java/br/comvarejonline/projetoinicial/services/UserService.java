package br.comvarejonline.projetoinicial.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.comvarejonline.projetoinicial.dtos.UserDTO;
import br.comvarejonline.projetoinicial.entities.User;
import br.comvarejonline.projetoinicial.repositories.UserRepository;
import br.comvarejonline.projetoinicial.services.exceptions.ResourceNotFoundException;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDTO findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        User user = userOptional.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
        return new UserDTO(user);
    }

}
