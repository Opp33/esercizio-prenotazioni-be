package com.esempio.prenotazioni.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.esempio.prenotazioni.model.Cliente;
import com.esempio.prenotazioni.dto.ClienteDTO;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	Optional<Cliente> findByTelefono(String telefono);

    @Query("SELECT new com.esempio.prenotazioni.dto.ClienteDTO(c.id, c.nome, c.cognome, c.email, c.telefono) FROM Cliente c ORDER BY c.cognome ASC")
    List<ClienteDTO> findAllClientiDTO();

    @Query("SELECT new com.esempio.prenotazioni.dto.ClienteDTO(c.id, c.nome, c.cognome, c.email, c.telefono) FROM Cliente c WHERE c.id = :id")
    Optional<ClienteDTO> findClienteDTOById(Long id);
    
    @Query("""
    	    SELECT new com.esempio.prenotazioni.dto.ClienteDTO(c.id, c.nome, c.cognome, c.email, c.telefono)
    	    FROM Cliente c
    	    WHERE 
    	      (:field = 'nome' AND LOWER(c.nome) LIKE LOWER(CONCAT(:term, '%'))) OR
    	      (:field = 'cognome' AND LOWER(c.cognome) LIKE LOWER(CONCAT(:term, '%'))) OR
    	      (:field = 'email' AND LOWER(c.email) LIKE LOWER(CONCAT(:term, '%'))) OR
    	      (:field = 'telefono' AND LOWER(c.telefono) LIKE LOWER(CONCAT(:term, '%')))
    	""")
    	List<ClienteDTO> autocompleteClienti(String term, String field);
}