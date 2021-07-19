package com.lukinhasssss.proposta.utils;

import com.lukinhasssss.proposta.dto.response.CartaoResponse;
import com.lukinhasssss.proposta.entities.Proposta;
import com.lukinhasssss.proposta.entities.enums.StatusProposta;
import com.lukinhasssss.proposta.integrations.ContasIntegration;
import com.lukinhasssss.proposta.repositories.PropostaRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Optional;

@Configuration
@EnableAsync
@EnableScheduling
public class PropostaCartaoSchedule {

    private PropostaRepository propostaRepository;
    private ContasIntegration contasIntegration;

    public PropostaCartaoSchedule(PropostaRepository propostaRepository, ContasIntegration contasIntegration) {
        this.propostaRepository = propostaRepository;
        this.contasIntegration = contasIntegration;
    }

    @Scheduled(fixedDelay = 60000)
    public void atribuirCartao() {

        List<CartaoResponse> cartoes = contasIntegration.getCartoes().getBody();

        List<Proposta> propostas = propostaRepository.findByStatusAndNumeroCartaoIsNull(StatusProposta.ELEGIVEL);

        if (!propostas.isEmpty()) {
            propostas.forEach(proposta -> {
                Optional<CartaoResponse> response = cartoes.stream().filter(cartao -> proposta.getId().equals(cartao.getIdProposta())).findAny();
                response.ifPresent(cartaoResponse -> proposta.setNumeroCartao(cartaoResponse.getId()));
//                if (response.isPresent()) {
//                    proposta.setNumeroCartao(response.get().getId());
//                }
            });
            propostaRepository.saveAll(propostas);
        }
    }

}
