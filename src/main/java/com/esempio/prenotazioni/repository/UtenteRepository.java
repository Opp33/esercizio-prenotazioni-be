package com.esempio.prenotazioni.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esempio.prenotazioni.model.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
	
	Optional<Utente> findByEmail(String email);
}