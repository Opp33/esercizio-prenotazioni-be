package com.esempio.prenotazioni.service;

import com.esempio.prenotazioni.dto.PrenotazioneClienteDTO;
import com.esempio.prenotazioni.model.Prenotazione;
import com.esempio.prenotazioni.model.Cliente;
import com.esempio.prenotazioni.repository.PrenotazioneRepository;
import com.esempio.prenotazioni.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PrenotazioneService {

    private final PrenotazioneRepository prenotazioneRepo;
    private final ClienteRepository clienteRepo;

    public PrenotazioneService(PrenotazioneRepository prenotazioneRepo, ClienteRepository clienteRepo) {
        this.prenotazioneRepo = prenotazioneRepo;
        this.clienteRepo = clienteRepo;
    }

    public List<PrenotazioneClienteDTO> getAllPrenotazioni() {
        return prenotazioneRepo.findAllWithCliente();
    }

    public Optional<PrenotazioneClienteDTO> getPrenotazioneById(Long id) {
        return prenotazioneRepo.findById(id).map(p -> {
            Cliente u = p.getCliente();
            return new PrenotazioneClienteDTO(
                p.getId(), p.getGiorno(), p.getOra(), p.getNote(),
                u.getId(), u.getNome(), u.getCognome(), u.getEmail(), u.getTelefono()
            );
        });
    }

    @Transactional
    public PrenotazioneClienteDTO createOrUpdatePrenotazione(PrenotazioneClienteDTO dto) {
        Cliente cliente;

        // Cerca cliente esistente per email
        Optional<Cliente> clienteOpt = clienteRepo.findByEmail(dto.getEmail());

        if (clienteOpt.isPresent()) {
            cliente = clienteOpt.get();
            // Se vuoi aggiornare i dati cliente (opzionale)
            cliente.setNome(dto.getNome());
            cliente.setCognome(dto.getCognome());
            cliente.setTelefono(dto.getTelefono());
        } else {
            // Nuovo cliente
            cliente = new Cliente();
            cliente.setNome(dto.getNome());
            cliente.setCognome(dto.getCognome());
            cliente.setEmail(dto.getEmail());
            cliente.setTelefono(dto.getTelefono());
        }

        cliente = clienteRepo.save(cliente); // fa update o insert a seconda dei casi

        Prenotazione prenotazione;

        if (dto.getPrenotazioneId() != null) {
            prenotazione = prenotazioneRepo.findById(dto.getPrenotazioneId())
                    .orElseThrow(() -> new RuntimeException("Prenotazione non trovata"));
        } else {
            prenotazione = new Prenotazione();
        }

        prenotazione.setCliente(cliente);
        prenotazione.setGiorno(dto.getGiorno());
        prenotazione.setOra(dto.getOra());
        prenotazione.setNote(dto.getNote());

        prenotazione = prenotazioneRepo.save(prenotazione);

        return new PrenotazioneClienteDTO(
                prenotazione.getId(), prenotazione.getGiorno(), prenotazione.getOra(), prenotazione.getNote(),
                cliente.getId(), cliente.getNome(), cliente.getCognome(), cliente.getEmail(), cliente.getTelefono()
        );
    }


    public void deletePrenotazione(Long id) {
        prenotazioneRepo.deleteById(id);
    }
}
