package com.lukinhasssss.proposta.integrations;

import com.lukinhasssss.proposta.dto.response.CartaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(url = "${contas.url}", name = "contasIntegration")
public interface ContasIntegration {

    @GetMapping
    ResponseEntity<List<CartaoResponse>> getCartoes();

}
