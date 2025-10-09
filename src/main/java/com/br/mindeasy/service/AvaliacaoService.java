package com.br.mindeasy.service;

import com.br.mindeasy.dto.request.AvaliacaoRequestDTO;
import com.br.mindeasy.dto.response.AvaliacaoResponseDTO;
import com.br.mindeasy.model.Agendamento;
import com.br.mindeasy.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvaliacaoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    public AvaliacaoResponseDTO getResumoAvaliacoes(Long terapeutaId) {
        List<Object[]> resultadosDoBanco = agendamentoRepository.countAvaliacoesByNota(terapeutaId);

        long contagem1Estrela = 0;
        long contagem2Estrelas = 0;
        long contagem3Estrelas = 0;
        long contagem4Estrelas = 0;
        long contagem5Estrelas = 0;

        for (Object[] resultado : resultadosDoBanco) {
            Integer nota = (Integer) resultado[0];
            Long contagem = (Long) resultado[1];

            if (nota != null) {
                switch (nota) {
                    case 1:
                        contagem1Estrela = contagem;
                        break;
                    case 2:
                        contagem2Estrelas = contagem;
                        break;
                    case 3:
                        contagem3Estrelas = contagem;
                        break;
                    case 4:
                        contagem4Estrelas = contagem;
                        break;
                    case 5:
                        contagem5Estrelas = contagem;
                        break;
                }
            }
        }

        long totalDeAvaliacoes = contagem1Estrela + contagem2Estrelas + contagem3Estrelas + contagem4Estrelas + contagem5Estrelas;

        AvaliacaoResponseDTO resumoDto = new AvaliacaoResponseDTO();
        resumoDto.setTotalAvaliacoes(totalDeAvaliacoes);
        resumoDto.setAvaliacoesDe1Estrela(contagem1Estrela);
        resumoDto.setAvaliacoesDe2Estrelas(contagem2Estrelas);
        resumoDto.setAvaliacoesDe3Estrelas(contagem3Estrelas);
        resumoDto.setAvaliacoesDe4Estrelas(contagem4Estrelas);
        resumoDto.setAvaliacoesDe5Estrelas(contagem5Estrelas);

        return resumoDto;
    }

    public AvaliacaoResponseDTO criarAvaliacao(Long agendamentoId, AvaliacaoRequestDTO dto) {
        Agendamento agendamento = agendamentoRepository.findById(agendamentoId)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        if (agendamento.getAvaliacaoNota() != null) {
            throw new RuntimeException("Este agendamento já foi avaliado.");
        }

        agendamento.setAvaliacaoNota(dto.getNota());
        agendamento.setAvaliacaoComentario(dto.getComentario());

        agendamentoRepository.save(agendamento);

        return getResumoAvaliacoes(agendamento.getTerapeuta().getId());
    }

    public AvaliacaoResponseDTO atualizarAvaliacao(Long agendamentoId, AvaliacaoRequestDTO dto) {
        Agendamento agendamento = agendamentoRepository.findById(agendamentoId)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        if (agendamento.getAvaliacaoNota() == null) {
            throw new RuntimeException("Este agendamento ainda não foi avaliado. Crie uma avaliação antes de atualizar.");
        }

        agendamento.setAvaliacaoNota(dto.getNota());
        agendamento.setAvaliacaoComentario(dto.getComentario());

        agendamentoRepository.save(agendamento);

        return getResumoAvaliacoes(agendamento.getTerapeuta().getId());
    }

    public void removerAvaliacao(Long agendamentoId) {
    Agendamento agendamento = agendamentoRepository.findById(agendamentoId)
            .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

    agendamento.setAvaliacaoNota(null);
    agendamento.setAvaliacaoComentario(null);

    agendamentoRepository.save(agendamento);
}

}