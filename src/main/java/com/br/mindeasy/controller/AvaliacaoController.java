package com.br.mindeasy.controller;

import com.br.mindeasy.dto.request.AvaliacaoRequestDTO;
import com.br.mindeasy.dto.response.AvaliacaoResponseDTO;
import com.br.mindeasy.service.AvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/avaliacoes")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @GetMapping("/resumo/terapeuta/{idTerapeuta}")
    public ResponseEntity<AvaliacaoResponseDTO> getResumo(@PathVariable Long idTerapeuta) {
        AvaliacaoResponseDTO resumo = avaliacaoService.getResumoAvaliacoes(idTerapeuta);
        return ResponseEntity.ok(resumo);
    }

    @PostMapping("/agendamentos/{id}/avaliacao")
    public ResponseEntity<AvaliacaoResponseDTO> criarAvaliacao(@PathVariable Long id, @RequestBody AvaliacaoRequestDTO dto) {
        AvaliacaoResponseDTO resumoAtualizado = avaliacaoService.criarAvaliacao(id, dto);
        return ResponseEntity.status(201).body(resumoAtualizado);
    }

    @PutMapping("/agendamentos/{id}/avaliacao")
    public ResponseEntity<AvaliacaoResponseDTO> atualizarAvaliacao(@PathVariable Long id, @RequestBody AvaliacaoRequestDTO dto) {
        AvaliacaoResponseDTO resumoAtualizado = avaliacaoService.atualizarAvaliacao(id, dto);
        return ResponseEntity.ok(resumoAtualizado);
    }

    @DeleteMapping("/agendamentos/{id}/avaliacao")
public ResponseEntity<Void> removerAvaliacao(@PathVariable Long id) {
    avaliacaoService.removerAvaliacao(id);
    return ResponseEntity.noContent().build();
}

}