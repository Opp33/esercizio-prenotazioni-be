package com.esempio.prenotazioni.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PrenotazioneClienteDTO {
    private Long prenotazioneId;
    private LocalDate giorno;
    private LocalTime ora;
    private String note;
    private ClienteDTO cliente;

    public PrenotazioneClienteDTO(Long prenotazioneId, LocalDate giorno, LocalTime ora, String note, ClienteDTO cliente) {
        this.prenotazioneId = prenotazioneId;
        this.giorno = giorno;
        this.ora = ora;
        this.note = note;
        this.cliente = cliente;
    }
}