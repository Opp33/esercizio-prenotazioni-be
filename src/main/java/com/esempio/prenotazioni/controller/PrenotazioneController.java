package com.esempio.prenotazioni.controller;

import com.esempio.prenotazioni.dto.PrenotazioneUtenteDTO;
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
    public List<PrenotazioneUtenteDTO> getAll() {
        return service.getAllPrenotazioni();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrenotazioneUtenteDTO> getById(@PathVariable Long id) {
        return service.getPrenotazioneById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PrenotazioneUtenteDTO> create(@RequestBody PrenotazioneUtenteDTO dto) {
        PrenotazioneUtenteDTO created = service.createOrUpdatePrenotazione(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrenotazioneUtenteDTO> update(@PathVariable Long id,
                                                        @RequestBody PrenotazioneUtenteDTO dto) {
        dto.setPrenotazioneId(id);
        PrenotazioneUtenteDTO updated = service.createOrUpdatePrenotazione(dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deletePrenotazione(id);
        return ResponseEntity.noContent().build();
    }
}