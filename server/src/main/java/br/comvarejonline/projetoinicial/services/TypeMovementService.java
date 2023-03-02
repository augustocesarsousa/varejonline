package br.comvarejonline.projetoinicial.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.comvarejonline.projetoinicial.dtos.TypeMovementDTO;
import br.comvarejonline.projetoinicial.entities.TypeMovement;
import br.comvarejonline.projetoinicial.repositories.TypeMovementRepository;
import br.comvarejonline.projetoinicial.services.exceptions.ResourceNotFoundException;

/*
 * Serviço da entidade Tipo de Movimento
 */
@Service
public class TypeMovementService {

    private TypeMovementRepository typeMovementRepository;

    public TypeMovementService(TypeMovementRepository typeMovementRepository) {
        this.typeMovementRepository = typeMovementRepository;
    }

    // Consulta tipo de movimento por id
    @Transactional(readOnly = true)
    public TypeMovementDTO findById(Long id) {
        Optional<TypeMovement> typeMovementOptional = typeMovementRepository.findById(id);
        TypeMovement typeMovement = typeMovementOptional
                .orElseThrow(() -> new ResourceNotFoundException("Tipo não encontrado!"));
        return new TypeMovementDTO(typeMovement);
    }

    // Consulta todos os tipos de movimentos
    @Transactional(readOnly = true)
    public List<TypeMovementDTO> findAll() {
        List<TypeMovement> listTypeMovement = typeMovementRepository.findAll();
        return listTypeMovement.stream().map(typeMomente -> new TypeMovementDTO(typeMomente))
                .collect(Collectors.toList());
    }

    // Consulta tipos de movimento pela descrição
    @Transactional(readOnly = true)
    public List<TypeMovementDTO> findByRoleAuthority(String roleAuthority) {
        List<TypeMovement> listTypeMovement = typeMovementRepository.findByRoleAuthority(roleAuthority);
        return listTypeMovement.stream().map(typeMomente -> new TypeMovementDTO(typeMomente))
                .collect(Collectors.toList());
    }
}
