package com.esempio.prenotazioni.service;

import com.esempio.prenotazioni.dto.UtenteDTO;
import com.esempio.prenotazioni.model.Utente;
import com.esempio.prenotazioni.repository.UtenteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UtenteService {

    private final UtenteRepository utenteRepo;

    public UtenteService(UtenteRepository utenteRepo) {
        this.utenteRepo = utenteRepo;
    }

    public List<UtenteDTO> getAllUtenti() {
        return utenteRepo.findAllUtentiDTO();
    }

    public Optional<UtenteDTO> getUtenteById(Long id) {
        return utenteRepo.findUtenteDTOById(id);
    }

    @Transactional
    public UtenteDTO createOrUpdateUtente(UtenteDTO dto) {
        Utente utente;
        if (dto.getUtenteId() != null) {
            utente = utenteRepo.findById(dto.getUtenteId())
                    .orElseThrow(() -> new RuntimeException("Utente non trovato con ID: " + dto.getUtenteId()));
        } else {
            utente = new Utente();
        }

        utente.setNome(dto.getNome());
        utente.setCognome(dto.getCognome());
        utente.setEmail(dto.getEmail());
        utente.setTelefono(dto.getTelefono());

        Utente savedUtente = utenteRepo.save(utente);
        return new UtenteDTO(savedUtente.getId(), savedUtente.getNome(), savedUtente.getCognome(), savedUtente.getEmail(), savedUtente.getTelefono());
    }

    @Transactional
    public void deleteUtente(Long id) {
        utenteRepo.deleteById(id);
    }
}