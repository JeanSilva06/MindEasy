package com.br.mindeasy.dto.request;

public class MensagemRequestDTO {

    private Long destinatarioId;
    private String conteudo;

    public MensagemRequestDTO() {
    }

    public Long getDestinatarioId() {
        return destinatarioId;
    }

    public void setDestinatarioId(Long destinatarioId) {
        this.destinatarioId = destinatarioId;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
}