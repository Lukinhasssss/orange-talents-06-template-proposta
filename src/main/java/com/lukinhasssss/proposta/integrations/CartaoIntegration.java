package com.lukinhasssss.proposta.integrations;

import com.lukinhasssss.proposta.dto.response.CartaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(url = "${cartao.url}", name = "cartaoIntegration")
public interface CartaoIntegration {

    @GetMapping("/{id}")
    CartaoResponse getCartao(@PathVariable String id);

}
