package com.br.mindeasy.controller;

import com.br.mindeasy.dto.response.AvaliacaoResponseDTO;
import com.br.mindeasy.service.AvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}