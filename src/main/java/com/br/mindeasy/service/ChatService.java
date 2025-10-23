package com.br.mindeasy.service;

import com.br.mindeasy.dto.request.MensagemRequestDTO;
import com.br.mindeasy.dto.response.MensagemResponseDTO;
import com.br.mindeasy.exceptions.BusinessException; // Verifique o caminho da sua exceção
import com.br.mindeasy.model.Mensagem;
import com.br.mindeasy.model.Paciente;
import com.br.mindeasy.model.Terapeuta;
import com.br.mindeasy.model.Usuario;
import com.br.mindeasy.repository.MensagemRepository;
import com.br.mindeasy.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    private MensagemRepository mensagemRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public MensagemResponseDTO salvarMensagem(MensagemRequestDTO dto, Long remetenteId) {
        Usuario remetente = usuarioRepository.findById(remetenteId)
                .orElseThrow(() -> new BusinessException("Remetente não encontrado"));

        Usuario destinatario = usuarioRepository.findById(dto.getDestinatarioId())
                .orElseThrow(() -> new BusinessException("Destinatário não encontrado"));

        if (remetente instanceof Paciente && !(destinatario instanceof Terapeuta)) {
            throw new BusinessException("Paciente só pode enviar mensagens para Terapeutas.");
        }

        if (remetente instanceof Terapeuta && !(destinatario instanceof Paciente)) {
            throw new BusinessException("Terapeuta só pode enviar mensagens para Pacientes.");
        }

        Mensagem novaMensagem = new Mensagem();
        novaMensagem.setRemetente(remetente);
        novaMensagem.setDestinatario(destinatario);
        novaMensagem.setConteudo(dto.getConteudo());
        novaMensagem.setDataEnvio(LocalDateTime.now());

        Mensagem mensagemSalva = mensagemRepository.save(novaMensagem);

        return converterParaResponseDTO(mensagemSalva);
    }

    public List<MensagemResponseDTO> buscarHistorico(Long usuarioLogadoId, Long outroUsuarioId) {
        List<Mensagem> historico = mensagemRepository.findHistoricoConversa(usuarioLogadoId, outroUsuarioId);
        
        List<MensagemResponseDTO> historicoDto = new ArrayList<>();
        for (Mensagem mensagem : historico) {
            historicoDto.add(converterParaResponseDTO(mensagem));
        }

        return historicoDto;
    }

    private MensagemResponseDTO converterParaResponseDTO(Mensagem mensagem) {
        MensagemResponseDTO dto = new MensagemResponseDTO();
        dto.setId(mensagem.getId());
        dto.setConteudo(mensagem.getConteudo());
        dto.setDataEnvio(mensagem.getDataEnvio());
        dto.setRemetenteId(mensagem.getRemetente().getId());
        dto.setRemetenteNome(mensagem.getRemetente().getNome());
        dto.setDestinatarioId(mensagem.getDestinatario().getId());
        return dto;
    }
}