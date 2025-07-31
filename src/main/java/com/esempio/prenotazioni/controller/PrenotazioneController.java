package com.esempio.prenotazioni.controller;

import com.esempio.prenotazioni.dto.PrenotazioneClienteDTO;
import com.esempio.prenotazioni.service.PrenotazioneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prenotazioni")
@CrossOrigin(origins = "*")
public class PrenotazioneController {

    private final PrenotazioneService service;

    public PrenotazioneController(PrenotazioneService service) {
        this.service = service;
    }

    @GetMapping
    public List<PrenotazioneClienteDTO> getAll() {
        return service.getAllPrenotazioni();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrenotazioneClienteDTO> getById(@PathVariable Long id) {
        return service.getPrenotazioneById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PrenotazioneClienteDTO> create(@RequestBody PrenotazioneClienteDTO dto) {
        PrenotazioneClienteDTO created = service.createOrUpdatePrenotazione(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrenotazioneClienteDTO> update(@PathVariable Long id,
            @RequestBody PrenotazioneClienteDTO dto) {
        dto.setPrenotazioneId(id);
        PrenotazioneClienteDTO updated = service.createOrUpdatePrenotazione(dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deletePrenotazione(id);
        return ResponseEntity.noContent().build();
    }
}