package com.lukinhasssss.proposta.controllers;

import com.lukinhasssss.proposta.dto.request.PropostaRequest;
import com.lukinhasssss.proposta.dto.request.SolicitacaoRequest;
import com.lukinhasssss.proposta.dto.response.PropostaResponse;
import com.lukinhasssss.proposta.dto.response.SolicitacaoResponse;
import com.lukinhasssss.proposta.entities.Proposta;
import com.lukinhasssss.proposta.entities.enums.StatusProposta;
import com.lukinhasssss.proposta.integrations.SolicitacaoIntegration;
import com.lukinhasssss.proposta.repositories.PropostaRepository;
import feign.FeignException;
import io.opentracing.Tracer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    private PropostaRepository propostaRepository;
    private SolicitacaoIntegration solicitacaoIntegration;
    private Tracer tracer;

    public PropostaController(PropostaRepository propostaRepository, SolicitacaoIntegration solicitacaoIntegration, Tracer tracer) {
        this.propostaRepository = propostaRepository;
        this.solicitacaoIntegration = solicitacaoIntegration;
        this.tracer = tracer;
    }

    @PostMapping
    public ResponseEntity<?> novaProposta(@RequestBody @Valid PropostaRequest request) {
        tracer.activeSpan();
        String salt = KeyGenerators.string().generateKey();
        TextEncryptor encriptor = Encryptors.text("password", salt);

        Proposta proposta = request.converterParaEntidade(encriptor);
        propostaRepository.save(proposta);

        try {
            SolicitacaoRequest solicitacaoRequest = new SolicitacaoRequest(proposta);
            SolicitacaoResponse response = solicitacaoIntegration.novaSolicitacao(solicitacaoRequest);
            proposta.setStatus(StatusProposta.convert(response.getResultadoSolicitacao()));
            propostaRepository.save(proposta);
        }
        catch (FeignException e) {
            if (e.status() == 422)
                return ResponseEntity.unprocessableEntity().body(Map.of("documento", "Não foi possível criar uma proposta para este CPF/CNPJ!"));

            return ResponseEntity.badRequest().build();
        }

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{idProposta}")
    public ResponseEntity<?> acompanharProposta(@PathVariable String idProposta) {
        tracer.activeSpan();

        Optional<Proposta> proposta = propostaRepository.findById(idProposta);

        if (proposta.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensagem", "Não foi possível encontrar a proposta de id: " + idProposta));

        return ResponseEntity.ok().body(new PropostaResponse(proposta.get()));

    }

}
