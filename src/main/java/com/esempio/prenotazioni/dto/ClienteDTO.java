package com.esempio.prenotazioni.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ClienteDTO {
    private Long clienteId;
    private String nome;
    private String cognome;
    private String email;
    private String telefono;       
}