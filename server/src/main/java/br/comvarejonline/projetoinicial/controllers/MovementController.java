package br.comvarejonline.projetoinicial.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.comvarejonline.projetoinicial.dtos.MovementCreateDTO;
import br.comvarejonline.projetoinicial.dtos.MovementDTO;
import br.comvarejonline.projetoinicial.services.MovementService;

@RestController
@RequestMapping(value = "/movement")
public class MovementController {

    private MovementService movementService;

    public MovementController(MovementService movementService) {
        this.movementService = movementService;
    }

    @GetMapping
    public ResponseEntity<List<MovementDTO>> findAll() {
        List<MovementDTO> movementList = movementService.findAll();
        return ResponseEntity.ok().body(movementList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MovementDTO> findByMovementId(@PathVariable Long id) {
        MovementDTO movement = movementService.findByMovementId(id);
        return ResponseEntity.ok().body(movement);
    }

    @GetMapping(value = "/product/{id}")
    public ResponseEntity<List<MovementDTO>> findByProductId(@PathVariable Long id) {
        List<MovementDTO> movementList = movementService.findByProductId(id);
        return ResponseEntity.ok().body(movementList);
    }

    @GetMapping(value = "/type_movement/{id}")
    public ResponseEntity<List<MovementDTO>> findByTypeMovementId(@PathVariable Long id) {
        List<MovementDTO> movementList = movementService.findByTypeMovementId(id);
        return ResponseEntity.ok().body(movementList);
    }

    @PostMapping
    public ResponseEntity<MovementCreateDTO> create(@Valid @RequestBody MovementCreateDTO movementCreateDTO) {
        movementCreateDTO = movementService.create(movementCreateDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(movementCreateDTO.getId())
                .toUri();
        return ResponseEntity.created(uri).body(movementCreateDTO);
    }
}
