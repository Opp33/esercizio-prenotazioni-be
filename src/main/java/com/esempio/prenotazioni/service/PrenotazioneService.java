package com.esempio.prenotazioni.service;

import com.esempio.prenotazioni.dto.ClienteDTO;
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
            Cliente c = p.getCliente();
            ClienteDTO clienteDTO = new ClienteDTO(c.getId(), c.getNome(), c.getCognome(), c.getEmail(), c.getTelefono());

            return new PrenotazioneClienteDTO(
                p.getId(), p.getGiorno(), p.getOra(), p.getNote(), clienteDTO
            );
        });
    }

    @Transactional
    public PrenotazioneClienteDTO createOrUpdatePrenotazione(PrenotazioneClienteDTO dto) {
        ClienteDTO cdto = dto.getCliente();
        Cliente cliente;

        Optional<Cliente> clienteOpt = clienteRepo.findByTelefono(cdto.getTelefono());

        if (clienteOpt.isPresent()) {
            cliente = clienteOpt.get();

        } else {
            cliente = new Cliente();
            cliente.setNome(cdto.getNome());
            cliente.setCognome(cdto.getCognome());
            cliente.setEmail(cdto.getEmail());
            cliente.setTelefono(cdto.getTelefono());
        }

        cliente = clienteRepo.save(cliente);

        Prenotazione prenotazione = (dto.getPrenotazioneId() != null)
            ? prenotazioneRepo.findById(dto.getPrenotazioneId())
                  .orElseThrow(() -> new RuntimeException("Prenotazione non trovata"))
            : new Prenotazione();

        prenotazione.setCliente(cliente);
        prenotazione.setGiorno(dto.getGiorno());
        prenotazione.setOra(dto.getOra());
        prenotazione.setNote(dto.getNote());

        prenotazione = prenotazioneRepo.save(prenotazione);

        ClienteDTO nuovoClienteDTO = new ClienteDTO(cliente.getId(), cliente.getNome(), cliente.getCognome(), cliente.getEmail(), cliente.getTelefono());
        return new PrenotazioneClienteDTO(
            prenotazione.getId(), prenotazione.getGiorno(), prenotazione.getOra(), prenotazione.getNote(), nuovoClienteDTO
        );
    }



    public void deletePrenotazione(Long id) {
        prenotazioneRepo.deleteById(id);
    }
}
