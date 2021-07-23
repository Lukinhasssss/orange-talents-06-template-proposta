package com.lukinhasssss.proposta.integrations;

import com.lukinhasssss.proposta.dto.request.CarteiraRequest;
import com.lukinhasssss.proposta.dto.response.CartaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "${cartao.url}", name = "cartaoIntegration")
public interface CartaoIntegration {

    @GetMapping("/{id}")
    CartaoResponse getCartao(@PathVariable String id);

    @PostMapping("/{id}/bloqueios")
    void bloquearCartaoNoSistemaLegado(@PathVariable String id, Object sistemaResponsavel);

    @PostMapping("/{id}/avisos")
    void avisoViagem(@PathVariable String id, Object request);

    @PostMapping("/{id}/avisos")
    void associarCarteira(@PathVariable String id, @RequestBody CarteiraRequest request);

}
