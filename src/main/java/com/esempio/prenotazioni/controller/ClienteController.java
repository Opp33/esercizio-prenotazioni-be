package com.esempio.prenotazioni.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.esempio.prenotazioni.dto.ClienteDTO;
import com.esempio.prenotazioni.service.ClienteService;

import java.util.List;

@RestController
@RequestMapping("/api/clienti")
@CrossOrigin(origins = "*")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping
    public List<ClienteDTO> getAll() {
        return service.getAllClienti();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> getById(@PathVariable Long id) {
        return service.getClienteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> create(@RequestBody ClienteDTO dto) {
        ClienteDTO created = service.createOrUpdateCliente(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> update(@PathVariable Long id, @RequestBody ClienteDTO dto) {
        dto.setClienteId(id);
        ClienteDTO updated = service.createOrUpdateCliente(dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/autocomplete")
    public ResponseEntity<List<ClienteDTO>> autocomplete(
            @RequestParam String term,
            @RequestParam String field) {
        if (!List.of("nome", "cognome", "email", "telefono").contains(field)) {
            return ResponseEntity.badRequest().build();
        }

        List<ClienteDTO> risultati = service.autocompleteClienti(term, field);
        return ResponseEntity.ok(risultati);
    }

}