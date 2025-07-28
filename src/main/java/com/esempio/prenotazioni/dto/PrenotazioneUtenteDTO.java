package com.esempio.prenotazioni.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrenotazioneUtenteDTO {
    private Long prenotazioneId;
    private LocalDate giorno;
    private LocalTime ora;
    private String note;

    private Long utenteId;
    private String nome;
    private String cognome;
    private String email;
    private String telefono;

    public PrenotazioneUtenteDTO(Long prenotazioneId, LocalDate giorno, LocalTime ora, String note,
                                 Long utenteId, String nome, String cognome, String email, String telefono) {
        this.prenotazioneId = prenotazioneId;
        this.giorno = giorno;
        this.ora = ora;
        this.note = note;
        this.utenteId = utenteId;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.telefono = telefono;
    }
}
