package br.comvarejonline.projetoinicial.controllers;

import java.net.URI;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

import javax.validation.Valid;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
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
import br.comvarejonline.projetoinicial.repositories.MovementCustomRepository;
import br.comvarejonline.projetoinicial.services.MovementService;

/*
 * Controller da entidade Movimento
 */
@RestController
// Definindo endpoint para /movement
@RequestMapping(value = "/movement")
public class MovementController {

    // private static Logger logger =
    // LoggerFactory.getLogger(MovementController.class);

    private MovementService movementService;
    // private MovementCustomRepository movementCustomRepository;

    // Definindo a zoda da data para America/Sao_Paulo
    private LocalDateTime now = LocalDateTime.now();
    private ZoneId zone = ZoneId.of("America/Sao_Paulo");
    private ZoneOffset zoneOffSet = zone.getRules().getOffset(now);

    public MovementController(MovementService movementService, MovementCustomRepository movementCustomRepository) {
        this.movementService = movementService;
        // this.movementCustomRepository = movementCustomRepository;
    }

    // Endpoind que retorna todos os movimentos
    @GetMapping
    public ResponseEntity<List<MovementDTO>> findAll() {
        List<MovementDTO> movementList = movementService.findAll();
        return ResponseEntity.ok().body(movementList);
    }

    // Endpoind que retorna um movimento pelo id
    @GetMapping(value = "/{id}")
    public ResponseEntity<MovementDTO> findByMovementId(@PathVariable Long id) {
        MovementDTO movement = movementService.findByMovementId(id);
        return ResponseEntity.ok().body(movement);
    }

    // Endpoind que retorna movimentos por id do produto
    @GetMapping(value = "/product/{id}")
    public ResponseEntity<List<MovementDTO>> findByProductId(@PathVariable Long id) {
        List<MovementDTO> movementList = movementService.findByProductId(id);
        return ResponseEntity.ok().body(movementList);
    }

    // Endpoind que retorna movimentos por id do tipo de movimentação
    @GetMapping(value = "/type_movement/{id}")
    public ResponseEntity<List<MovementDTO>> findByTypeMovementId(@PathVariable Long id) {
        // logger.warn("id: " + id);
        List<MovementDTO> movementList = movementService.findByTypeMovementId(id);
        return ResponseEntity.ok().body(movementList);
    }

    // Endpoind que retorna movimentos entre um período
    @GetMapping(value = "/date/{startDate}/{endDate}")
    public ResponseEntity<List<MovementDTO>> findByDateBetween(
            @PathVariable(value = "startDate") String startDate,
            @PathVariable(value = "endDate") String endDate) {

        // Pegando as datas em texto na URL e convertendo para Instant
        // Ajustar melhor forma
        LocalDate localDate = LocalDate.parse(startDate);
        LocalDateTime localDateTime = localDate.atStartOfDay();
        Instant startDateInstant = localDateTime.toInstant(zoneOffSet);
        localDate = LocalDate.parse(endDate);
        localDateTime = localDate.atStartOfDay();
        Instant endDateInstant = localDateTime.toInstant(zoneOffSet);

        List<MovementDTO> movementList = movementService.findByDateBetween(
                startDateInstant,
                endDateInstant);
        return ResponseEntity.ok().body(movementList);
    }

    // Endpoind que retorna movimentos por id do tipo de movimento e id do produto
    @GetMapping(value = "/{typeMovementId}/{productId}")
    public ResponseEntity<List<MovementDTO>> findByTypeMovementIdAndProductId(
            @PathVariable(value = "typeMovementId") Long typeMovementId,
            @PathVariable(value = "productId") Long productId) {
        List<MovementDTO> movementList = movementService.findByTypeMovementIdAndProductId(typeMovementId, productId);
        return ResponseEntity.ok().body(movementList);
    }

    // Endpoind que retorna movimentos por filtro
    // Erro na query dinâmica
    // TODO pesquisar solução
    // @GetMapping(value = "/filter")
    // public ResponseEntity<List<MovementDTO>> findByFilter(
    // @RequestParam(value = "productId", required = false) Long productId,
    // @RequestParam(value = "startDate", required = false) String startDate,
    // @RequestParam(value = "endDate", required = false) String endDate,
    // @RequestParam(value = "typeMovementId", required = false) Long
    // typeMovementId) {

    // LocalDate localDate = LocalDate.parse(startDate);
    // LocalDateTime localDateTime = localDate.atStartOfDay();
    // Instant startDateInstant = localDateTime.toInstant(zoneOffSet);

    // localDate = LocalDate.parse(endDate);
    // localDateTime = localDate.atStartOfDay();
    // Instant endDateInstant = localDateTime.toInstant(zoneOffSet);

    // movementCustomRepository.findByFilter(
    // productId,
    // startDateInstant,
    // endDateInstant,
    // typeMovementId);
    // return ResponseEntity.ok().body(null);
    // }

    // Endpoind para criar uma movimentação
    @PostMapping
    public ResponseEntity<MovementCreateDTO> create(@Valid @RequestBody MovementCreateDTO movementCreateDTO) {
        movementCreateDTO = movementService.create(movementCreateDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(movementCreateDTO.getId())
                .toUri();
        return ResponseEntity.created(uri).body(movementCreateDTO);
    }
}
