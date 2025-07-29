package com.esempio.prenotazioni.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.esempio.prenotazioni.model.Cliente;
import com.esempio.prenotazioni.dto.ClienteDTO;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	Optional<Cliente> findByEmail(String email);

    @Query("SELECT new com.esempio.prenotazioni.dto.ClienteDTO(c.id, c.nome, c.cognome, c.email, c.telefono) FROM Cliente c")
    List<ClienteDTO> findAllClientiDTO();

    @Query("SELECT new com.esempio.prenotazioni.dto.ClienteDTO(c.id, c.nome, c.cognome, c.email, c.telefono) FROM Cliente c WHERE c.id = :id")
    Optional<ClienteDTO> findClienteDTOById(Long id);
}