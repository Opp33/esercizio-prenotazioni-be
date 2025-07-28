package com.esempio.prenotazioni.repository;

import com.esempio.prenotazioni.dto.PrenotazioneUtenteDTO;
import com.esempio.prenotazioni.model.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {

    @Query("SELECT new com.esempio.prenotazioni.dto.PrenotazioneUtenteDTO(" +
           "p.id, p.giorno, p.ora, p.note, " +
           "u.id, u.nome, u.cognome, u.email, u.telefono) " +
           "FROM Prenotazione p JOIN p.utente u")
    List<PrenotazioneUtenteDTO> findAllWithUtente();
}
