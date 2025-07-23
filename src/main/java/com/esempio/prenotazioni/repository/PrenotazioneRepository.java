package com.esempio.prenotazioni.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esempio.prenotazioni.model.Prenotazione;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
	
	List<Prenotazione> findAllByOrderByGiornoAscOraAsc();
	List<Prenotazione> findAllByOrderByNomeAsc();

}
