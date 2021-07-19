package com.lukinhasssss.proposta.controllers;

import com.lukinhasssss.proposta.dto.request.PropostaRequest;
import com.lukinhasssss.proposta.dto.request.SolicitacaoRequest;
import com.lukinhasssss.proposta.dto.response.PropostaResponse;
import com.lukinhasssss.proposta.dto.response.SolicitacaoResponse;
import com.lukinhasssss.proposta.entities.Proposta;
import com.lukinhasssss.proposta.entities.enums.StatusProposta;
import com.lukinhasssss.proposta.integrations.SolicitacaoIntegration;
import com.lukinhasssss.proposta.repositories.PropostaRepository;
import com.lukinhasssss.proposta.utils.MensagemDeErroNotFound;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

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
    public ResponseEntity<?> novaProposta(@RequestBody @Valid PropostaRequest request) {
        Proposta proposta = request.converterParaEntidade();
        propostaRepository.save(proposta);

        try {
            SolicitacaoRequest solicitacaoRequest = new SolicitacaoRequest(proposta);
            SolicitacaoResponse response = solicitacaoIntegration.novaSolicitacao(solicitacaoRequest);
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

    @GetMapping("/{idProposta}")
    public ResponseEntity<?> acompanharProposta(@PathVariable String idProposta) {
        Optional<Proposta> proposta = propostaRepository.findById(idProposta);

        if (proposta.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensagemDeErroNotFound("Não foi possível encontrar a proposta de id: " + idProposta));

        return ResponseEntity.ok().body(new PropostaResponse(proposta.get()));

    }

}
