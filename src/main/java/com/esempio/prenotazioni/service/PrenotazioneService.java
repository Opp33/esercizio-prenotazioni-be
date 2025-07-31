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

    private final PrenotazioneRepository prenRepo;
    private final ClienteRepository cliRepo;

    public PrenotazioneService(PrenotazioneRepository prenRepo, ClienteRepository cliRepo) {
        this.prenRepo = prenRepo;
        this.cliRepo = cliRepo;
    }

    public List<PrenotazioneClienteDTO> getAllPrenotazioni() {
        return prenRepo.findAllWithCliente();
    }

    public Optional<PrenotazioneClienteDTO> getPrenotazioneById(Long id) {
        return prenRepo.findById(id).map(p -> {
            Cliente c = p.getCliente();
            ClienteDTO cDto = new ClienteDTO(c.getId(), c.getNome(), c.getCognome(), c.getEmail(),
                    c.getTelefono());

            return new PrenotazioneClienteDTO(
                    p.getId(), p.getGiorno(), p.getOra(), p.getNote(), cDto);
        });
    }

    @Transactional
    public PrenotazioneClienteDTO createOrUpdatePrenotazione(PrenotazioneClienteDTO dto) {
        ClienteDTO cDto = dto.getCliente();

        Cliente cliente = cliRepo.findByTelefono(cDto.getTelefono()).orElseGet(() -> {
            Cliente nuovo = new Cliente();
            nuovo.setNome(cDto.getNome());
            nuovo.setCognome(cDto.getCognome());
            nuovo.setEmail(cDto.getEmail());
            nuovo.setTelefono(cDto.getTelefono());
            return cliRepo.save(nuovo);
        });

        Prenotazione pren = (dto.getPrenotazioneId() != null)
                ? prenRepo.findById(dto.getPrenotazioneId())
                        .orElseThrow(() -> new RuntimeException(
                                "Prenotazione non trovata con ID: " + dto.getPrenotazioneId()))
                : new Prenotazione();

        pren.setCliente(cliente);
        pren.setGiorno(dto.getGiorno());
        pren.setOra(dto.getOra());
        pren.setNote(dto.getNote());

        Prenotazione saved = prenRepo.save(pren);

        ClienteDTO clienteDTO = new ClienteDTO(cliente.getId(), cliente.getNome(),
                cliente.getCognome(), cliente.getEmail(),
                cliente.getTelefono());
        return new PrenotazioneClienteDTO(saved.getId(), saved.getGiorno(),
                saved.getOra(), saved.getNote(), clienteDTO);
    }

    @Transactional
    public void deletePrenotazione(Long id) {
        prenRepo.deleteById(id);
    }
}