package com.esempio.prenotazioni.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrenotazioneClienteDTO {
    private Long prenotazioneId;
    private LocalDate giorno;
    private LocalTime ora;
    private String note;

    private Long clienteId;
    private String nome;
    private String cognome;
    private String email;
    private String telefono;

    public PrenotazioneClienteDTO(Long prenotazioneId, LocalDate giorno, LocalTime ora, String note,
                                 Long clienteId, String nome, String cognome, String email, String telefono) {
        this.prenotazioneId = prenotazioneId;
        this.giorno = giorno;
        this.ora = ora;
        this.note = note;
        this.clienteId = clienteId;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.telefono = telefono;
    }
}
