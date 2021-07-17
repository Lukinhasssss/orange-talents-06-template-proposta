package com.lukinhasssss.proposta.controllers;

import com.lukinhasssss.proposta.config.validation.StandardErrorMessage;
import com.lukinhasssss.proposta.dto.request.PropostaRequest;
import com.lukinhasssss.proposta.dto.request.SolicitacaoRequest;
import com.lukinhasssss.proposta.dto.response.SolicitacaoResponse;
import com.lukinhasssss.proposta.entities.Proposta;
import com.lukinhasssss.proposta.entities.enums.StatusProposta;
import com.lukinhasssss.proposta.integrations.SolicitacaoIntegration;
import com.lukinhasssss.proposta.repositories.PropostaRepository;
import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<?> novaProposta(@RequestBody @Valid PropostaRequest request,) {
        Proposta proposta = request.converterParaEntidade();
        propostaRepository.save(proposta);

        try {
            SolicitacaoRequest solicitacaoRequest = new SolicitacaoRequest(proposta);
            SolicitacaoResponse response = solicitacaoIntegration.novaSolicitacao(solicitacaoRequest);
            assert response != null;
            proposta.setStatus(StatusProposta.convert(response.getResultadoSolicitacao()));
            propostaRepository.save(proposta);
        }
        catch (FeignException e) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(proposta.getId()).toUri();
            return ResponseEntity.created(uri).build();
        }

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}
