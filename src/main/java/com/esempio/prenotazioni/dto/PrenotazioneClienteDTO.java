package com.esempio.prenotazioni.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PrenotazioneClienteDTO {
    private Long prenotazioneId;
    private LocalDate giorno;
    private LocalTime ora;
    private String note;
    private ClienteDTO cliente;
}