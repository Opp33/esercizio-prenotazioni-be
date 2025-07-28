package com.esempio.prenotazioni.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.esempio.prenotazioni.model.Utente;
import com.esempio.prenotazioni.dto.UtenteDTO;

public interface UtenteRepository extends JpaRepository<Utente, Long> {

	Optional<Utente> findByEmail(String email);

    @Query("SELECT new com.esempio.prenotazioni.dto.UtenteDTO(u.id, u.nome, u.cognome, u.email, u.telefono) FROM Utente u")
    List<UtenteDTO> findAllUtentiDTO();

    @Query("SELECT new com.esempio.prenotazioni.dto.UtenteDTO(u.id, u.nome, u.cognome, u.email, u.telefono) FROM Utente u WHERE u.id = :id")
    Optional<UtenteDTO> findUtenteDTOById(Long id);
}