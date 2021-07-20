package com.lukinhasssss.proposta.controllers;

import com.lukinhasssss.proposta.dto.request.BiometriaRequest;
import com.lukinhasssss.proposta.dto.response.CartaoResponse;
import com.lukinhasssss.proposta.entities.Biometria;
import com.lukinhasssss.proposta.integrations.CartaoIntegration;
import com.lukinhasssss.proposta.repositories.BiometriaRepository;
import com.lukinhasssss.proposta.utils.MensagemDeErroNotFound;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    private BiometriaRepository biometriaRepository;
    private CartaoIntegration cartaoIntegration;

    public CartaoController(BiometriaRepository biometriaRepository, CartaoIntegration cartaoIntegration) {
        this.biometriaRepository = biometriaRepository;
        this.cartaoIntegration = cartaoIntegration;
    }

    @PostMapping("{id}/biometria")
    public ResponseEntity<?> associarBiometria(@PathVariable String id, @RequestBody @Valid BiometriaRequest request) {
        try {
            CartaoResponse cartao = cartaoIntegration.getCartao(id); // Somente valida se o cartão existe
        } catch (FeignException e) {
            if (e.status() == 404)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensagemDeErroNotFound("Não foi possível encontrar um cartão com id: " + id));

            return ResponseEntity.badRequest().build();
        }

        Biometria biometria = request.converterParaEntidade(id);
        biometriaRepository.save(biometria);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(biometria.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}
