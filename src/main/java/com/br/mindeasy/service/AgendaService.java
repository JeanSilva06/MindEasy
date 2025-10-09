package com.br.mindeasy.service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.mindeasy.dto.request.AgendaRequestDTO;
import com.br.mindeasy.dto.response.AgendaResponseDTO;
import com.br.mindeasy.enums.DiaAtendimento;
import com.br.mindeasy.model.Agenda;
import com.br.mindeasy.model.Terapeuta;
import com.br.mindeasy.repository.AgendaRepository;
import com.br.mindeasy.repository.TerapeutaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AgendaService {
    private final AgendaRepository agendaRepository;
    private final TerapeutaRepository terapeutaRepository;

    public AgendaService(
            AgendaRepository agendaRepository,
            TerapeutaRepository terapeutaRepository) {
        this.agendaRepository = agendaRepository;
        this.terapeutaRepository = terapeutaRepository;
    }

    public AgendaResponseDTO toResponseDTO(Agenda agenda) {
        return new AgendaResponseDTO(
                agenda.getId(),
                agenda.getTerapeuta().getId(),
                agenda.getTerapeuta().getNome(),
                agenda.getHoraEntrada(),
                agenda.getHoraSaida(),
                agenda.getDuracaoConsulta(),
                agenda.getDias());
    }

    private Agenda fromRequestDTO(AgendaRequestDTO dto, Agenda agendaExistente) {
        Terapeuta terapeuta = terapeutaRepository
                .findById(dto.getIdTerapeuta())
                .orElseThrow(() -> new EntityNotFoundException("Terapeuta não encontrado"));

        agendaExistente.setTerapeuta(terapeuta);
        agendaExistente.setHoraEntrada(dto.getHoraEntrada());
        agendaExistente.setHoraSaida(dto.getHoraSaida());
        agendaExistente.setDuracaoConsulta(dto.getDuracaoConsulta());
        agendaExistente.setDias(dto.getDias());
        return agendaExistente;
    }

    @Transactional
    public AgendaResponseDTO criarAgenda(AgendaRequestDTO dto) {
        Agenda nova = new Agenda();
        fromRequestDTO(dto, nova);
        Agenda salvo = agendaRepository.save(nova);
        return toResponseDTO(salvo);
    }

    @Transactional(readOnly = true)
    public AgendaResponseDTO buscarPorId(Long id) {
        Agenda agenda = agendaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agenda não encontrada"));
        return toResponseDTO(agenda);
    }

    @Transactional(readOnly = true)
    public List<AgendaResponseDTO> listarTodas() {
        return agendaRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AgendaResponseDTO> listarPorTerapeuta(Long terapeutaId) {
        Agenda agenda = agendaRepository.findAgendaByTerapeutaId(terapeutaId);
        if (agenda == null) {
            return Collections.emptyList();
        }
        return List.of(toResponseDTO(agenda));
    }

    @Transactional
    public AgendaResponseDTO atualizarAgenda(Long id, AgendaRequestDTO dto) {
        Agenda existente = agendaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agenda não encontrada"));
        fromRequestDTO(dto, existente);
        Agenda atualizado = agendaRepository.save(existente);
        return toResponseDTO(atualizado);
    }

    @Transactional
    public void deletarAgenda(Long id) {
        Agenda existente = agendaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agenda não encontrada"));
        agendaRepository.delete(existente);
    }

    @Transactional
    public AgendaResponseDTO atualizarParcial(Long id, Map<String, Object> updates) {
        Agenda agenda = agendaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agenda não encontrada"));

        if (updates.containsKey("horaEntrada")) {
            String hora = updates.get("horaEntrada").toString();
            agenda.setHoraEntrada(LocalTime.parse(hora));
        }

        if (updates.containsKey("horaSaida")) {
            String hora = updates.get("horaSaida").toString();
            agenda.setHoraSaida(LocalTime.parse(hora));
        }

        if (updates.containsKey("duracaoConsulta")) {
            Integer minutos = (Integer) updates.get("duracaoConsulta");
            agenda.setDuracaoConsulta(Duration.ofMinutes(minutos));
        }

        if (updates.containsKey("dias")) {
            @SuppressWarnings("unchecked")
            List<String> diasStr = (List<String>) updates.get("dias");
            List<DiaAtendimento> dias = diasStr.stream()
                    .map(DiaAtendimento::valueOf)
                    .collect(Collectors.toList());
            agenda.setDias(dias);
        }

        Agenda salvo = agendaRepository.save(agenda);
        return toResponseDTO(salvo);
    }

    @Transactional(readOnly = true)
    public List<AgendaResponseDTO> buscarPorDiasOuDuracao(
            List<DiaAtendimento> dias,
            Duration duracao
    ) {
        if (dias != null && !dias.isEmpty()) {
            return agendaRepository.findDistinctByDiasIn(dias)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
        }

        if (duracao != null) {
            return agendaRepository.findByDuracaoConsulta(duracao)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
        }

        // Se nenhum filtro for enviado, retorna todos
        return agendaRepository.findAll()
            .stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }

}
