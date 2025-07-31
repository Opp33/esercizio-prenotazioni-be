package com.esempio.prenotazioni.repository;

import com.esempio.prenotazioni.dto.PrenotazioneClienteDTO;
import com.esempio.prenotazioni.model.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {

	@Query("""
			    SELECT new com.esempio.prenotazioni.dto.PrenotazioneClienteDTO(
			        p.id, p.giorno, p.ora, p.note,
			        new com.esempio.prenotazioni.dto.ClienteDTO(
			            c.id, c.nome, c.cognome, c.email, c.telefono
			        )
			    )
			    FROM Prenotazione p JOIN p.cliente c ORDER BY p.giorno ASC, p.ora ASC
			""")
	List<PrenotazioneClienteDTO> findAllWithCliente();
}
