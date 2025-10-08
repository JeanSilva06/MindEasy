package com.br.mindeasy.repository;

import java.time.Duration;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.br.mindeasy.enums.DiaAtendimento;
import com.br.mindeasy.model.Agenda;
import com.br.mindeasy.model.Terapeuta;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    Agenda findAgendaById(Long idAgenda);

    Agenda findAgendaByTerapeutaId(Long idTerapeuta);

    @Query("""
                    SELECT DISTINCT a.terapeuta
                    FROM Agenda a
                    JOIN a.dias d
                    WHERE d.dia
                    IN :dias
            """)
    List<Terapeuta> findTerapeutasByDias(@Param("dias") List<DiaAtendimento> dias);

    @Query("""
                SELECT a.terapeuta
                FROM Agenda a
                WHERE a.duracaoConsulta = :duracao
        """)
    List<Terapeuta> findTerapeutasByDuracaoConsulta(@Param("duracao") Duration duracao);
}
