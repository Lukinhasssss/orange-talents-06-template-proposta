package com.lukinhasssss.proposta.controllers;

import com.lukinhasssss.proposta.dto.request.AvisoViagemRequest;
import com.lukinhasssss.proposta.dto.request.BiometriaRequest;
import com.lukinhasssss.proposta.dto.request.CarteiraRequest;
import com.lukinhasssss.proposta.dto.response.CartaoResponse;
import com.lukinhasssss.proposta.entities.AvisoViagem;
import com.lukinhasssss.proposta.entities.Biometria;
import com.lukinhasssss.proposta.entities.BloqueioCartao;
import com.lukinhasssss.proposta.entities.Carteira;
import com.lukinhasssss.proposta.integrations.CartaoIntegration;
import com.lukinhasssss.proposta.repositories.AvisoViagemRepository;
import com.lukinhasssss.proposta.repositories.BiometriaRepository;
import com.lukinhasssss.proposta.repositories.BloqueioCartaoRepository;
import com.lukinhasssss.proposta.repositories.CarteiraRepository;
import com.lukinhasssss.proposta.utils.MensagemDeErroNotFound;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    private BiometriaRepository biometriaRepository;
    private CartaoIntegration cartaoIntegration;
    private BloqueioCartaoRepository bloqueioCartaoRepository;
    private AvisoViagemRepository avisoViagemRepository;
    private CarteiraRepository carteiraRepository;

    public CartaoController(BiometriaRepository biometriaRepository, CartaoIntegration cartaoIntegration, BloqueioCartaoRepository bloqueioCartaoRepository, AvisoViagemRepository avisoViagemRepository, CarteiraRepository carteiraRepository) {
        this.biometriaRepository = biometriaRepository;
        this.cartaoIntegration = cartaoIntegration;
        this.bloqueioCartaoRepository = bloqueioCartaoRepository;
        this.avisoViagemRepository = avisoViagemRepository;
        this.carteiraRepository = carteiraRepository;
    }

    @Value("${sistema.nome}")
    private String sistemaResponsavel;

    @PostMapping("/{id}/biometrias")
    public ResponseEntity<?> associarBiometria(@PathVariable String id, @RequestBody @Valid BiometriaRequest request) {
        try {
            CartaoResponse cartao = cartaoIntegration.getCartao(id); // Somente valida se o cartão existe
        } catch (FeignException e) {
            if (e.status() == 404)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensagem", "Não foi possível encontrar um cartão com id:" + id));

            return ResponseEntity.badRequest().build();
        }

        Biometria biometria = request.converterParaEntidade(id);
        biometriaRepository.save(biometria);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(biometria.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/{id}/bloqueios")
    public ResponseEntity<?> bloquearCartao(@PathVariable String id, @RequestHeader(value = "User-Agent") String userAgent, HttpServletRequest request) {
        try {
            CartaoResponse cartao = cartaoIntegration.getCartao(id); // Somente valida se o cartão existe
            cartaoIntegration.bloquearCartaoNoSistemaLegado(id, Map.of("sistemaResponsavel", this.sistemaResponsavel));

            BloqueioCartao bloqueioCartao = new BloqueioCartao(request.getRemoteAddr(), userAgent, id);
            bloqueioCartaoRepository.save(bloqueioCartao);

            return ResponseEntity.ok().build();
        }
        catch (FeignException e) {
            if (e.status() == 404)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensagem", "Não foi possível encontrar um cartão com id:" + id));

            if (e.status() == 422)
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new MensagemDeErroNotFound("Cartão já está bloqueado!"));

            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{id}/avisos")
    public ResponseEntity<?> avisoViagem(@PathVariable String id, @RequestHeader(value = "User-Agent") String userAgent, HttpServletRequest httpRequest, @RequestBody AvisoViagemRequest request) {
        try {
            CartaoResponse cartao = cartaoIntegration.getCartao(id); // Somente valida se o cartão existe

            Map<String, Object> body = new HashMap<>();
            body.put("destino", request.getDestino());
            body.put("validoAte", request.getTerminoViagem());

            cartaoIntegration.avisoViagem(id, body);

            AvisoViagem avisoViagem = request.converterParaEntidade(userAgent, httpRequest, id);
            avisoViagemRepository.save(avisoViagem);

            return ResponseEntity.ok().build();
        }
        catch (FeignException e) {
            if (e.status() == 404)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensagem", "Não foi possível encontrar um cartão com id:" + id));

            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{id}/carteiras")
    public ResponseEntity<?> associarCarteira(@PathVariable String id, @RequestBody CarteiraRequest request) {
        Optional<Carteira> carteiraRequest = carteiraRepository.findByIdCartaoAndTipoCarteira(id, request.getCarteira());

        if (carteiraRequest.isPresent())
            return ResponseEntity.unprocessableEntity().body(Map.of("mensagem", "Este cartão já está associado a carteira: " + request.getCarteira()));

        try {
            CartaoResponse cartao = cartaoIntegration.getCartao(id); // Somente valida se o cartão existe
            cartaoIntegration.associarCarteira(id, request);
            Carteira carteira = request.converterParaEntidade(id);
            carteiraRepository.save(carteira);

            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(carteira.getId()).toUri();
            return ResponseEntity.created(uri).build();
        }
        catch (FeignException e) {
            if (e.status() == 404)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensagem", "Não foi possível encontrar um cartão com id:" + id));

            return ResponseEntity.badRequest().build();
        }
    }

}
