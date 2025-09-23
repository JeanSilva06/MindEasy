package com.br.mindeasy.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

public class AgendamentoRequestDTO {

    @NotNull
    private Long pacienteId;

    @NotNull
    private Long terapeutaId;

    @NotNull
    @FutureOrPresent
    private LocalDate data;

    @NotNull
    private LocalTime horaInicio;

    // Getters e Setters
    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public Long getTerapeutaId() {
        return terapeutaId;
    }

    public void setTerapeutaId(Long terapeutaId) {
        this.terapeutaId = terapeutaId;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }
    
}
