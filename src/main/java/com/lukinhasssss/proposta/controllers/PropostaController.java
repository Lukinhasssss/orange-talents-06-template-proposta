package com.lukinhasssss.proposta.controllers;

import com.lukinhasssss.proposta.dto.request.PropostaRequest;
import com.lukinhasssss.proposta.dto.response.SolicitacaoResponse;
import com.lukinhasssss.proposta.entities.Proposta;
import com.lukinhasssss.proposta.entities.enums.StatusProposta;
import com.lukinhasssss.proposta.integrations.SolicitacaoIntegration;
import com.lukinhasssss.proposta.repositories.PropostaRepository;
import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    private PropostaRepository propostaRepository;
    private SolicitacaoIntegration solicitacaoIntegration;

    public PropostaController(PropostaRepository propostaRepository, SolicitacaoIntegration solicitacaoIntegration) {
        this.propostaRepository = propostaRepository;
        this.solicitacaoIntegration = solicitacaoIntegration;
    }

    @PostMapping
    public ResponseEntity<Void> novaProposta(@RequestBody @Valid PropostaRequest request) {
        Proposta proposta = request.converterParaEntidade();
        propostaRepository.save(proposta);
        proposta.setStatus(StatusProposta.NAO_ELEGIVEL);


        try {
            SolicitacaoResponse response = solicitacaoIntegration.novaSolicitacao(request).getBody();
            proposta.setStatus(StatusProposta.convert(response.getResultadoSolicitacao()));
            propostaRepository.save(proposta);
        }
        catch (FeignException e) {
            System.out.println(e);
        }

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}
