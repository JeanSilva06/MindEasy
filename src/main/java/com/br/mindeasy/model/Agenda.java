package com.br.mindeasy.model;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import com.br.mindeasy.enums.DiaAtendimento;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "agendas")
public class Agenda {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @Column(nullable = false)
    private Terapeuta terapeuta;

    @Column(nullable = false)
    private LocalTime horaEntrada;

    @Column(nullable = false)
    private LocalTime horaSaida;

    @Column(nullable = false)
    private Duration duracaoConsulta;

    @OneToMany(mappedBy = "agenda", cascade = CascadeType.ALL)
    @Column(nullable = false)
    private List<DiaAtendimento> dias;

    //Contrutor padrão
    public Agenda() {}

    //Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Terapeuta getTerapeuta() { return terapeuta; }
    public void setTerapeuta(Terapeuta terapeuta) { this.terapeuta = terapeuta; }

    public LocalTime getHoraEntrada() { return horaEntrada; }
    public void setHoraEntrada(LocalTime horaEntrada) { this.horaEntrada = horaEntrada; }

    public LocalTime getHoraSaida() { return horaSaida; }
    public void setHoraSaida(LocalTime horaSaida) { this.horaSaida = horaSaida; }

    public Duration getDuracaoConsulta() { return duracaoConsulta; }
    public void setDuracaoConsulta(Duration duracaoConsulta) { this.duracaoConsulta = duracaoConsulta; }

    public List<DiaAtendimento> getDias() { return dias; }
    public void setDias(List<DiaAtendimento> dias) { this.dias = dias; }

}
