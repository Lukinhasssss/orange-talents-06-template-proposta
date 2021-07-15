package com.lukinhasssss.proposta.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lukinhasssss.proposta.dto.request.PropostaRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PropostaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper jsonMapper;

    @Test
    void deveriaCriarUmaNovaPropostaERetornarStatus201() throws Exception {
        mockMvc.perform(post("/propostas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(new PropostaRequest("Uchiha Madara", "uchiha.madara@konoha.com", "91877964069", "Rua dos Bobos, 0", new BigDecimal("2500")))))
            .andExpect(status().isCreated());
    }

    @Test
    void deveriaRetornarStatus400QuandoOEmailEstiverComOFormatoInvalido() throws Exception { // TODO -> Adicionar regex para validar o email porque só o @Email não é suficiente
        mockMvc.perform(post("/propostas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new PropostaRequest("Luffy", "luffy.gmail", "78004074030", "Rua dos Bobos, 0", new BigDecimal("2500")))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveriaRetornarStatus400QuandoOEmailForVazio() throws Exception {
        mockMvc.perform(post("/propostas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new PropostaRequest("Luffy", "", "78004074030", "Rua dos Bobos, 0", new BigDecimal("2500")))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveriaRetornar400QuandoOEmailTiverApenasEspacosEmBranco() throws Exception {
        mockMvc.perform(post("/propostas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new PropostaRequest("Luffy", " ", "78004074030", "Rua dos Bobos, 0", new BigDecimal("2500")))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveriaRetornarStatus422QuandoODocumentoJaEstiverAssociadoAUmaProposta() throws Exception {
        mockMvc.perform(post("/propostas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new PropostaRequest("Izuko Midoriya", "midoriya@gmail.com", "52667623061", "Rua dos Bobos, 0", new BigDecimal("2500")))))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void deveriaRetornarStatus400QuandoODocumentoForInvalido() throws Exception {
        mockMvc.perform(post("/propostas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new PropostaRequest("Nami", "nami@gmail.com", "123456789", "Rua dos Bobos, 0", new BigDecimal("2500")))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveriaRetornarStatus400QuandoONomeForVazio() throws Exception {
        mockMvc.perform(post("/propostas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new PropostaRequest("", "zoro@gmail.com", "54683357003", "Rua dos Bobos, 0", new BigDecimal("2500")))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveriaRetornarStatus400QuandoONomeTiverApenasEspacosEmBranco() throws Exception {
        mockMvc.perform(post("/propostas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new PropostaRequest(" ", "zoro@gmail.com", "54683357003", "Rua dos Bobos, 0", new BigDecimal("2500")))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveriaRetornarStatus400QuandoOEnderecoForVazio() throws Exception {
        mockMvc.perform(post("/propostas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new PropostaRequest("Zoro", "zoro@gmail.com", "54683357003", "", new BigDecimal("2500")))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveriaRetornarStatus400QuandoOEnderecoTiverApenasEspacosEmBranco() throws Exception {
        mockMvc.perform(post("/propostas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new PropostaRequest("Zoro", "zoro@gmail.com", "54683357003", " ", new BigDecimal("2500")))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveriaRetornarStatus400QuandoOSalarioForNulo() throws Exception {
        mockMvc.perform(post("/propostas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new PropostaRequest("Zoro", "zoro@gmail.com", "54683357003", "Rua dos Bobos, 0", null))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveriaRetornarStatus400QuandoOSalarioForNegativo() throws Exception {
        mockMvc.perform(post("/propostas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new PropostaRequest("Zoro", "zoro@gmail.com", "54683357003", "Rua dos Bobos, 0", new BigDecimal("-2500")))))
                .andExpect(status().isBadRequest());
    }

    private String toJson(PropostaRequest request) throws JsonProcessingException {
        return jsonMapper.writeValueAsString(request);
    }

}
