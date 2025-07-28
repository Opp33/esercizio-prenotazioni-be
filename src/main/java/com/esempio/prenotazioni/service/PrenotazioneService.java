package com.esempio.prenotazioni.service;

import com.esempio.prenotazioni.dto.PrenotazioneUtenteDTO;
import com.esempio.prenotazioni.model.Prenotazione;
import com.esempio.prenotazioni.model.Utente;
import com.esempio.prenotazioni.repository.PrenotazioneRepository;
import com.esempio.prenotazioni.repository.UtenteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PrenotazioneService {

    private final PrenotazioneRepository prenotazioneRepo;
    private final UtenteRepository utenteRepo;

    public PrenotazioneService(PrenotazioneRepository prenotazioneRepo, UtenteRepository utenteRepo) {
        this.prenotazioneRepo = prenotazioneRepo;
        this.utenteRepo = utenteRepo;
    }

    public List<PrenotazioneUtenteDTO> getAllPrenotazioni() {
        return prenotazioneRepo.findAllWithUtente();
    }

    public Optional<PrenotazioneUtenteDTO> getPrenotazioneById(Long id) {
        return prenotazioneRepo.findById(id).map(p -> {
            Utente u = p.getUtente();
            return new PrenotazioneUtenteDTO(
                p.getId(), p.getGiorno(), p.getOra(), p.getNote(),
                u.getId(), u.getNome(), u.getCognome(), u.getEmail(), u.getTelefono()
            );
        });
    }

    @Transactional
    public PrenotazioneUtenteDTO createOrUpdatePrenotazione(PrenotazioneUtenteDTO dto) {
        Utente utente;

        // Cerca utente esistente per email
        Optional<Utente> utenteOpt = utenteRepo.findByEmail(dto.getEmail());

        if (utenteOpt.isPresent()) {
            utente = utenteOpt.get();
            // Se vuoi aggiornare i dati utente (opzionale)
            utente.setNome(dto.getNome());
            utente.setCognome(dto.getCognome());
            utente.setTelefono(dto.getTelefono());
        } else {
            // Nuovo utente
            utente = new Utente();
            utente.setNome(dto.getNome());
            utente.setCognome(dto.getCognome());
            utente.setEmail(dto.getEmail());
            utente.setTelefono(dto.getTelefono());
        }

        utente = utenteRepo.save(utente); // fa update o insert a seconda dei casi

        Prenotazione prenotazione;

        if (dto.getPrenotazioneId() != null) {
            prenotazione = prenotazioneRepo.findById(dto.getPrenotazioneId())
                    .orElseThrow(() -> new RuntimeException("Prenotazione non trovata"));
        } else {
            prenotazione = new Prenotazione();
        }

        prenotazione.setUtente(utente);
        prenotazione.setGiorno(dto.getGiorno());
        prenotazione.setOra(dto.getOra());
        prenotazione.setNote(dto.getNote());

        prenotazione = prenotazioneRepo.save(prenotazione);

        return new PrenotazioneUtenteDTO(
                prenotazione.getId(), prenotazione.getGiorno(), prenotazione.getOra(), prenotazione.getNote(),
                utente.getId(), utente.getNome(), utente.getCognome(), utente.getEmail(), utente.getTelefono()
        );
    }


    public void deletePrenotazione(Long id) {
        prenotazioneRepo.deleteById(id);
    }
}
