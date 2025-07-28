package com.esempio.prenotazioni.controller;

import com.esempio.prenotazioni.dto.UtenteDTO;
import com.esempio.prenotazioni.service.UtenteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utenti")
@CrossOrigin(origins = "*")
public class UtenteController {

    private final UtenteService utenteService;

    public UtenteController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @GetMapping
    public List<UtenteDTO> getAll() {
        return utenteService.getAllUtenti();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UtenteDTO> getById(@PathVariable Long id) {
        return utenteService.getUtenteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UtenteDTO> create(@RequestBody UtenteDTO dto) {
        UtenteDTO created = utenteService.createOrUpdateUtente(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UtenteDTO> update(@PathVariable Long id, @RequestBody UtenteDTO dto) {
        dto.setUtenteId(id); // Assicura che l'ID dal path sia usato per l'aggiornamento
        UtenteDTO updated = utenteService.createOrUpdateUtente(dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        utenteService.deleteUtente(id);
        return ResponseEntity.noContent().build();
    }
}