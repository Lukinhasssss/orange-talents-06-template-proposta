package com.lukinhasssss.proposta.integrations;

import com.lukinhasssss.proposta.dto.request.PropostaRequest;
import com.lukinhasssss.proposta.dto.response.SolicitacaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "http://localhost:9999/api/solicitacao", name = "solicitacaoIntegracao")
public interface SolicitacaoIntegration {

    @PostMapping
    ResponseEntity<SolicitacaoResponse> novaSolicitacao(@RequestBody PropostaRequest request);

}
