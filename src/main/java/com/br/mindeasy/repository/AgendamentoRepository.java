package com.br.mindeasy.repository;

import com.br.mindeasy.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    @Query("""
        SELECT a.avaliacaoNota, COUNT(a.avaliacaoNota)
        FROM Agendamento a
        WHERE a.terapeuta.id = :terapeutaId AND a.avaliacaoNota IS NOT NULL
        GROUP BY a.avaliacaoNota
    """)
    List<Object[]> countAvaliacoesByNota(@Param("terapeutaId") Long terapeutaId);

}