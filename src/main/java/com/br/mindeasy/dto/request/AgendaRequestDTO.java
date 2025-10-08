package com.br.mindeasy.dto.request;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import com.br.mindeasy.enums.DiaAtendimento;
import com.br.mindeasy.model.Terapeuta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class AgendaRequestDTO {
    @NotNull
    private Terapeuta terapeuta;

    @NotBlank
    private LocalTime horaEntrada;

    @NotBlank
    private LocalTime horaSaida;

    @NotBlank
    private Duration duracaoConsulta;

    @NotEmpty
    private List<DiaAtendimento> dias;

    //Getters e Setters
    public Terapeuta getTerapeuta() {
        return terapeuta;
    }

    public void setTerapeuta(Terapeuta terapeuta) {
        this.terapeuta = terapeuta;
    }

    public LocalTime getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(LocalTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public LocalTime getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida(LocalTime horaSaida) {
        this.horaSaida = horaSaida;
    }

    public Duration getDuracaoConsulta() {
        return duracaoConsulta;
    }

    public void setDuracaoConsulta(Duration duracaoConsulta) {
        this.duracaoConsulta = duracaoConsulta;
    }

    public List<DiaAtendimento> getDias() {
        return dias;
    }

    public void setDias(List<DiaAtendimento> dias) {
        this.dias = dias;
    }
    
}
