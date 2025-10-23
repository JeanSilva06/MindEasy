package com.br.mindeasy.controller;

import com.br.mindeasy.dto.response.MensagemResponseDTO;
import com.br.mindeasy.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatRestController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/historico/{outroUsuarioId}")
    public ResponseEntity<List<MensagemResponseDTO>> getHistoricoConversa(
            @PathVariable Long outroUsuarioId, Principal principal) {
        
        Long usuarioLogadoId = Long.parseLong(principal.getName());
        
        List<MensagemResponseDTO> historico = chatService.buscarHistorico(usuarioLogadoId, outroUsuarioId);
        return ResponseEntity.ok(historico);
    }
}