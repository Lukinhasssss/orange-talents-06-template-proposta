package com.lukinhasssss.proposta.integrations;

import com.lukinhasssss.proposta.dto.request.PropostaRequest;
import com.lukinhasssss.proposta.dto.request.SolicitacaoRequest;
import com.lukinhasssss.proposta.dto.response.SolicitacaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(url = "${solicitacao.url}", name = "solicitacaoIntegracao")
public interface SolicitacaoIntegration {

    @PostMapping
    SolicitacaoResponse novaSolicitacao(@RequestBody @Valid SolicitacaoRequest request);

}