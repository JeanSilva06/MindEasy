package com.br.mindeasy.service;

import org.springframework.stereotype.Service;

import com.br.mindeasy.dto.request.AgendamentoRequestDTO;
import com.br.mindeasy.dto.response.AgendamentoResponseDTO;
import com.br.mindeasy.model.Agendamento;
import com.br.mindeasy.model.Paciente;
import com.br.mindeasy.model.Terapeuta;
import com.br.mindeasy.repository.AgendamentoRepository;
import com.br.mindeasy.repository.PacienteRepository;
import com.br.mindeasy.repository.TerapeutaRepository;

import jakarta.persistence.EntityNotFoundException;

import com.br.mindeasy.enums.StatusAgendamento;
import com.br.mindeasy.exceptions.BusinessException;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final PacienteRepository pacienteRepository;
    private final TerapeutaRepository terapeutaRepository;

    public AgendamentoService(
        AgendamentoRepository agendamentoRepository,
        PacienteRepository pacienteRepository,
        TerapeutaRepository terapeutaRepository
    ) {
        this.agendamentoRepository = agendamentoRepository;
        this.pacienteRepository = pacienteRepository;
        this.terapeutaRepository = terapeutaRepository;
    }

    private AgendamentoResponseDTO toResponseDTO(Agendamento agendamento, Paciente paciente, Terapeuta terapeuta) {
        return new AgendamentoResponseDTO(
            agendamento.getId(),
            paciente.getNome(),
            terapeuta.getNome(),
            agendamento.getData(),
            agendamento.getHoraInicio(),
            agendamento.getStatus(),
            agendamento.getAvaliacaoNota(),
            agendamento.getAvaliacaoComentario()
        );
    }
    public AgendamentoResponseDTO agendar(AgendamentoRequestDTO dto) {
        // Validar paciente
        Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
            .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado"));

        // Validar terapeuta
        Terapeuta terapeuta = terapeutaRepository.findById(dto.getTerapeutaId())
            .orElseThrow(() -> new EntityNotFoundException("Terapeuta não encontrado"));

        // Verificar conflitos de horário
        boolean conflito = agendamentoRepository.existsByTerapeutaIdAndDataAndHoraInicio(
            terapeuta.getId(), dto.getData(), dto.getHoraInicio()
        );

        if (conflito) {
            throw new BusinessException("Horário já está ocupado para esse terapeuta");
        }

        // Verificar limite de agendamentos do paciente
        long agendamentosNoDia = agendamentoRepository.countByPacienteIdAndData(
            paciente.getId(), dto.getData()
        );

        if (agendamentosNoDia >= 2) {
            throw new BusinessException("Paciente já possui 2 agendamentos nesse dia");
        }

        // Criar agendamento
        Agendamento agendamento = new Agendamento();
        agendamento.setPaciente(paciente);
        agendamento.setTerapeuta(terapeuta);
        agendamento.setData(dto.getData());
        agendamento.setHoraInicio(dto.getHoraInicio());
        agendamento.setStatus(StatusAgendamento.AGENDADO);

        agendamentoRepository.save(agendamento);

        return toResponseDTO(agendamento, paciente, terapeuta);
    }
}