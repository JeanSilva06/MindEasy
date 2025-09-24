package com.br.mindeasy.controller;

import com.br.mindeasy.dto.request.AgendamentoRequestDTO;
import com.br.mindeasy.dto.response.AgendamentoResponseDTO;
import com.br.mindeasy.service.AgendamentoService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    // GET: agendamento específico
    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDTO> buscarPorId(@PathVariable Long id) {
        AgendamentoResponseDTO dto = agendamentoService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    // GET: todos os agendamentos
    @GetMapping
    public ResponseEntity<List<AgendamentoResponseDTO>> listarTodos() {
        List<AgendamentoResponseDTO> lista = agendamentoService.listarTodos();
        return ResponseEntity.ok(lista);
    }

    // GET: agendamentos de um terapeuta específico
    @GetMapping("/terapeuta/{idTerapeuta}")
    public ResponseEntity<List<AgendamentoResponseDTO>> listarPorTerapeuta(@PathVariable Long idTerapeuta) {
        List<AgendamentoResponseDTO> lista = agendamentoService.listarPorTerapeuta(idTerapeuta);
        return ResponseEntity.ok(lista);
    }

    // POST: criação de agendamento
    @PostMapping
    public ResponseEntity<AgendamentoResponseDTO> criar(@Valid @RequestBody AgendamentoRequestDTO dto) {
        AgendamentoResponseDTO criado = agendamentoService.agendar(dto);
        return ResponseEntity.status(201).body(criado);
    }

    // PUT: edição completa
    @PutMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody AgendamentoRequestDTO dto) {
        AgendamentoResponseDTO atualizado = agendamentoService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    // PATCH: atualização parcial (ex: status ou avaliação)
    @PatchMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDTO> atualizarParcial(@PathVariable Long id, @RequestBody AgendamentoRequestDTO dto) {
        AgendamentoResponseDTO atualizado = agendamentoService.atualizarParcial(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    // DELETE: deletar agendamento
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        agendamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}