package com.esempio.prenotazioni.service;

import com.esempio.prenotazioni.dto.ClienteDTO;
import com.esempio.prenotazioni.model.Cliente;
import com.esempio.prenotazioni.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository repo;

    public ClienteService(ClienteRepository repo) {
        this.repo = repo;
    }

    public List<ClienteDTO> getAllClienti() {
        return repo.findAllClientiDTO();
    }

    public Optional<ClienteDTO> getClienteById(Long id) {
        return repo.findClienteDTOById(id);
    }

    @Transactional
    public ClienteDTO createOrUpdateCliente(ClienteDTO dto) {
        Cliente c = (dto.getClienteId() != null)
                ? repo.findById(dto.getClienteId())
                      .orElseThrow(() -> new RuntimeException("Cliente non trovato con ID: " + dto.getClienteId()))
                : new Cliente();

        c.setNome(dto.getNome());
        c.setCognome(dto.getCognome());
        c.setEmail(dto.getEmail());
        c.setTelefono(dto.getTelefono());

        Cliente saved = repo.save(c);
        return new ClienteDTO(saved.getId(), saved.getNome(), saved.getCognome(),
                              saved.getEmail(), saved.getTelefono());
    }

    @Transactional
    public void deleteCliente(Long id) {
        repo.deleteById(id);
    }

    public List<ClienteDTO> autocompleteClienti(String term, String field) {
        return repo.autocompleteClienti(term, field);
    }
}
