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

@Service
public class TypeMovementService {

    private TypeMovementRepository typeMovementRepository;

    public TypeMovementService(TypeMovementRepository typeMovementRepository) {
        this.typeMovementRepository = typeMovementRepository;
    }

    @Transactional(readOnly = true)
    public TypeMovementDTO findById(Long id) {
        Optional<TypeMovement> typeMovementOptional = typeMovementRepository.findById(id);
        TypeMovement typeMovement = typeMovementOptional
                .orElseThrow(() -> new ResourceNotFoundException("Tipo não encontrado!"));
        return new TypeMovementDTO(typeMovement);
    }

    @Transactional(readOnly = true)
    public List<TypeMovementDTO> findAll() {
        List<TypeMovement> listTypeMovement = typeMovementRepository.findAll();
        return listTypeMovement.stream().map(typeMomente -> new TypeMovementDTO(typeMomente))
                .collect(Collectors.toList());
    }
}
