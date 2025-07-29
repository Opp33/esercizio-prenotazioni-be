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

    private final ClienteRepository clienteRepo;

    public ClienteService(ClienteRepository clienteRepo) {
        this.clienteRepo = clienteRepo;
    }

    public List<ClienteDTO> getAllClienti() {
        return clienteRepo.findAllClientiDTO();
    }

    public Optional<ClienteDTO> getClienteById(Long id) {
        return clienteRepo.findClienteDTOById(id);
    }

    @Transactional
    public ClienteDTO createOrUpdateCliente(ClienteDTO dto) {
        Cliente cliente;
        if (dto.getClienteId() != null) {
            cliente = clienteRepo.findById(dto.getClienteId())
                    .orElseThrow(() -> new RuntimeException("cliente non trovato con ID: " + dto.getClienteId()));
        } else {
            cliente = new Cliente();
        }

        cliente.setNome(dto.getNome());
        cliente.setCognome(dto.getCognome());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());

        Cliente savedcliente = clienteRepo.save(cliente);
        return new ClienteDTO(savedcliente.getId(), savedcliente.getNome(), savedcliente.getCognome(), savedcliente.getEmail(), savedcliente.getTelefono());
    }

    @Transactional
    public void deleteCliente(Long id) {
        clienteRepo.deleteById(id);
    }
}