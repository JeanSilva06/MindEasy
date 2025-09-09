package com.br.mindeasy.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.mindeasy.dto.request.TerapeutaRequestDTO;
import com.br.mindeasy.dto.response.TerapeutaResponseDTO;
import com.br.mindeasy.model.Terapeuta;
import com.br.mindeasy.service.TerapeutaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/terapeuta")
public class TerapeutaController {
    private final TerapeutaService service;

    public TerapeutaController(TerapeutaService service) {
        this.service = service;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<TerapeutaResponseDTO> cadastrar(@RequestBody @Valid TerapeutaRequestDTO requestDTO) {
        TerapeutaResponseDTO responseDTO = service.cadastrarTerapeuta(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.removerTerapeuta(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TerapeutaResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid TerapeutaRequestDTO dto) {
        TerapeutaResponseDTO terapeutaAtualizado = service.atualizarTerapeuta(id, dto);
        return ResponseEntity.ok(terapeutaAtualizado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TerapeutaResponseDTO> buscarPorId(@PathVariable Long id) {
        Terapeuta terapeuta = service.buscarPorId(id);
        TerapeutaResponseDTO responseDTO = toResponseDTO(terapeuta);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/terapeutas")
    public ResponseEntity<List<TerapeutaResponseDTO>> listarTodos() {
        List<TerapeutaResponseDTO> terapeutas = service.listarTerapeutas();
        return ResponseEntity.ok(terapeutas);
    }

    private TerapeutaResponseDTO toResponseDTO(Terapeuta terapeuta) {
        return new TerapeutaResponseDTO(
                terapeuta.getId(),
                terapeuta.getNome(),
                terapeuta.getEmail(),
                terapeuta.getTelefone(),
                terapeuta.getSexo(),
                terapeuta.getCrm(),
                terapeuta.getEspecialidade());
    }
}
