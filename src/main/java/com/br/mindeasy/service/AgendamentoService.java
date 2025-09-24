package com.br.mindeasy.service;

import com.br.mindeasy.dto.request.AgendamentoRequestDTO;
import com.br.mindeasy.dto.response.AgendamentoResponseDTO;
import com.br.mindeasy.enums.StatusAgendamento;
import com.br.mindeasy.exceptions.BusinessException;
import com.br.mindeasy.model.Agendamento;
import com.br.mindeasy.model.Paciente;
import com.br.mindeasy.model.Terapeuta;
import com.br.mindeasy.repository.AgendamentoRepository;
import com.br.mindeasy.repository.PacienteRepository;
import com.br.mindeasy.repository.TerapeutaRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    // Mapeamento manual
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
        Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
            .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado"));

        Terapeuta terapeuta = terapeutaRepository.findById(dto.getTerapeutaId())
            .orElseThrow(() -> new EntityNotFoundException("Terapeuta não encontrado"));

        boolean conflito = agendamentoRepository.existsByTerapeutaIdAndDataAndHoraInicio(
            terapeuta.getId(), dto.getData(), dto.getHoraInicio()
        );

        if (conflito) {
            throw new BusinessException("Horário já está ocupado para esse terapeuta");
        }

        long agendamentosNoDia = agendamentoRepository.countByPacienteIdAndData(
            paciente.getId(), dto.getData()
        );

        if (agendamentosNoDia >= 2) {
            throw new BusinessException("Paciente já possui 2 agendamentos nesse dia");
        }

        Agendamento agendamento = new Agendamento();
        agendamento.setPaciente(paciente);
        agendamento.setTerapeuta(terapeuta);
        agendamento.setData(dto.getData());
        agendamento.setHoraInicio(dto.getHoraInicio());
        agendamento.setStatus(StatusAgendamento.AGENDADO);

        agendamentoRepository.save(agendamento);

        return toResponseDTO(agendamento, paciente, terapeuta);
    }

    public AgendamentoResponseDTO buscarPorId(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado"));

        return toResponseDTO(agendamento, agendamento.getPaciente(), agendamento.getTerapeuta());
    }

    public List<AgendamentoResponseDTO> listarTodos() {
        return agendamentoRepository.findAll().stream()
            .map(a -> toResponseDTO(a, a.getPaciente(), a.getTerapeuta()))
            .collect(Collectors.toList());
    }

    public List<AgendamentoResponseDTO> listarPorTerapeuta(Long idTerapeuta) {
        return agendamentoRepository.findByTerapeutaId(idTerapeuta).stream()
            .map(a -> toResponseDTO(a, a.getPaciente(), a.getTerapeuta()))
            .collect(Collectors.toList());
    }

    public AgendamentoResponseDTO atualizar(Long id, AgendamentoRequestDTO dto) {
        Agendamento agendamento = agendamentoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado"));

        Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
            .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado"));

        Terapeuta terapeuta = terapeutaRepository.findById(dto.getTerapeutaId())
            .orElseThrow(() -> new EntityNotFoundException("Terapeuta não encontrado"));

        agendamento.setPaciente(paciente);
        agendamento.setTerapeuta(terapeuta);
        agendamento.setData(dto.getData());
        agendamento.setHoraInicio(dto.getHoraInicio());

        agendamentoRepository.save(agendamento);

        return toResponseDTO(agendamento, paciente, terapeuta);
    }

    public AgendamentoResponseDTO atualizarParcial(Long id, AgendamentoRequestDTO dto) {
        Agendamento agendamento = agendamentoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado"));

        if (dto.getData() != null) agendamento.setData(dto.getData());
        if (dto.getHoraInicio() != null) agendamento.setHoraInicio(dto.getHoraInicio());
        if (dto.getPacienteId() != null) {
            Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado"));
            agendamento.setPaciente(paciente);
        }
        if (dto.getTerapeutaId() != null) {
            Terapeuta terapeuta = terapeutaRepository.findById(dto.getTerapeutaId())
                .orElseThrow(() -> new EntityNotFoundException("Terapeuta não encontrado"));
            agendamento.setTerapeuta(terapeuta);
        }

        agendamentoRepository.save(agendamento);

        return toResponseDTO(agendamento, agendamento.getPaciente(), agendamento.getTerapeuta());
    }

    public void deletar(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado"));

        agendamentoRepository.delete(agendamento);
    }
}