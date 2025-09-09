package com.br.mindeasy.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.mindeasy.dto.request.PacienteRequestDTO;
import com.br.mindeasy.dto.response.PacienteResponseDTO;
import com.br.mindeasy.model.Paciente;
import com.br.mindeasy.service.PacienteService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
    private final PacienteService service;

    public PacienteController(PacienteService service) {
        this.service = service;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<PacienteResponseDTO> cadastrar(@RequestBody @Valid PacienteRequestDTO requestDTO) {
        PacienteResponseDTO responseDTO = service.cadastrarPaciente(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid PacienteRequestDTO requestDTO) {
        PacienteResponseDTO updated = service.atualizarPaciente(id, requestDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.removerPaciente(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> buscarPorId(@PathVariable Long id) {
        Paciente paciente = service.buscarPorId(id);
        PacienteResponseDTO responseDTO = toResponseDTO(paciente);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/pacientes")
    public ResponseEntity<List<PacienteResponseDTO>> listarTodos() {
        List<PacienteResponseDTO> pacientes = service.listarPacientes();
        return ResponseEntity.ok(pacientes);
    }

    private PacienteResponseDTO toResponseDTO(Paciente p) {
        return new PacienteResponseDTO(
                p.getId(),
                p.getNome(),
                p.getEmail(),
                p.getSexo(),
                p.getDataNascimento());
    }

}
