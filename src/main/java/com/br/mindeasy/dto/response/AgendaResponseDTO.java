package com.br.mindeasy.dto.response;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import com.br.mindeasy.enums.DiaAtendimento;
import com.br.mindeasy.model.Terapeuta;

public class AgendaResponseDTO {

    private Terapeuta terapeuta;
    private LocalTime horaEntrada;
    private LocalTime horaSaida;
    private Duration duracaoConsulta;
    private List<DiaAtendimento> dias;

    //Construtor com parâmetros
    public AgendaResponseDTO(Terapeuta terapeuta, LocalTime horaEntrada, LocalTime horaSaida, Duration duracaoConsulta,
            List<DiaAtendimento> dias) {
        this.terapeuta = terapeuta;
        this.horaEntrada = horaEntrada;
        this.horaSaida = horaSaida;
        this.duracaoConsulta = duracaoConsulta;
        this.dias = dias;
    }

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
