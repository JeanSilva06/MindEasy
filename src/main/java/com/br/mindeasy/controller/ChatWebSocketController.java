package com.br.mindeasy.controller;

import com.br.mindeasy.dto.request.MensagemRequestDTO;
import com.br.mindeasy.dto.response.MensagemResponseDTO;
import com.br.mindeasy.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class ChatWebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatService chatService;

    @MessageMapping("/chat.enviar")
    public void processarMensagem(@Payload MensagemRequestDTO mensagemDTO, Principal principal) {
        Long remetenteId = Long.parseLong(principal.getName());
        
        MensagemResponseDTO mensagemSalva = chatService.salvarMensagem(mensagemDTO, remetenteId);

        messagingTemplate.convertAndSendToUser(
            String.valueOf(mensagemSalva.getDestinatarioId()),
            "/topic/mensagens",
            mensagemSalva
        );
    }
}