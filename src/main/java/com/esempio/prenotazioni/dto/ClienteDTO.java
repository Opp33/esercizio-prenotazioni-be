package com.esempio.prenotazioni.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class ClienteDTO {
    private Long clienteId;
    private String nome;
    private String cognome;
    private String email;
    private String telefono;

    public ClienteDTO(Long id, String nome, String cognome, String email, String telefono) {
        this.clienteId = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.telefono = telefono;
    }
}