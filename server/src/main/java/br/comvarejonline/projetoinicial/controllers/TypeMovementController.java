package br.comvarejonline.projetoinicial.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.comvarejonline.projetoinicial.dtos.TypeMovementDTO;
import br.comvarejonline.projetoinicial.services.TypeMovementService;

@RestController
@RequestMapping(value = "/type_movement")
public class TypeMovementController {

    private TypeMovementService typeMovementService;

    public TypeMovementController(TypeMovementService typeMovementService) {
        this.typeMovementService = typeMovementService;
    }

    @GetMapping
    public ResponseEntity<List<TypeMovementDTO>> findAll() {
        List<TypeMovementDTO> listTypeMovement = typeMovementService.findAll();
        return ResponseEntity.ok().body(listTypeMovement);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TypeMovementDTO> findById(@PathVariable Long id){
        TypeMovementDTO typeMovementDTO = typeMovementService.findById(id);
        return ResponseEntity.ok().body(typeMovementDTO);
    }

    @GetMapping(value = "/authority/{roleAuthority}")
    public ResponseEntity<List<TypeMovementDTO>> findByRoleAuthority(@PathVariable String roleAuthority) {
        List<TypeMovementDTO> listTypeMovement = typeMovementService.findByRoleAuthority(roleAuthority);
        return ResponseEntity.ok().body(listTypeMovement);
    }

}
