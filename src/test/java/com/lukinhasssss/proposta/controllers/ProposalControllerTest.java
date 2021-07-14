package com.lukinhasssss.proposta.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lukinhasssss.proposta.dto.request.ProposalRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
public class ProposalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper jsonMapper;

    @Test
    void shouldCreateANewProposalAndReturnStatus201() throws Exception {
        mockMvc.perform(post("/proposals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(new ProposalRequest("Uchiha Madara", "uchiha.madara@konoha.com", "91877964069", "Rua dos Bobos, 0", new BigDecimal("2500")))))
            .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnStatus400WhenEmailAlreadyExists() throws Exception {
        mockMvc.perform(post("/proposals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new ProposalRequest("Luffy", "luffy@gmail.com", "03277222071", "Rua dos Bobos, 0", new BigDecimal("2500")))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnStatus400WhenEmailIsWithInvalidFormat() throws Exception { // TODO -> Adicionar regex para validar o email porque só o @Email não é suficiente
        mockMvc.perform(post("/proposals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new ProposalRequest("Luffy", "luffy.gmail", "78004074030", "Rua dos Bobos, 0", new BigDecimal("2500")))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnStatus400WhenEmailIsEmpty() throws Exception {
        mockMvc.perform(post("/proposals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new ProposalRequest("Luffy", "", "78004074030", "Rua dos Bobos, 0", new BigDecimal("2500")))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnStatus400WhenEmailHasOnlyWhiteSpaces() throws Exception {
        mockMvc.perform(post("/proposals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new ProposalRequest("Luffy", " ", "78004074030", "Rua dos Bobos, 0", new BigDecimal("2500")))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnStatus400WhenDocumentAlreadyExists() throws Exception {
        mockMvc.perform(post("/proposals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new ProposalRequest("Nami", "nami@gmail.com", "85857488001", "Rua dos Bobos, 0", new BigDecimal("2500")))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnStatus400WhenDocumentIsInvalid() throws Exception {
        mockMvc.perform(post("/proposals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new ProposalRequest("Nami", "nami@gmail.com", "123456789", "Rua dos Bobos, 0", new BigDecimal("2500")))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnStatus400WhenNameIsEmpty() throws Exception {
        mockMvc.perform(post("/proposals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new ProposalRequest("", "zoro@gmail.com", "54683357003", "Rua dos Bobos, 0", new BigDecimal("2500")))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnStatus400WhenNameHasOnlyWithSpaces() throws Exception {
        mockMvc.perform(post("/proposals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new ProposalRequest(" ", "zoro@gmail.com", "54683357003", "Rua dos Bobos, 0", new BigDecimal("2500")))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnStatus400WhenAddressIsEmpty() throws Exception {
        mockMvc.perform(post("/proposals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new ProposalRequest("Zoro", "zoro@gmail.com", "54683357003", "", new BigDecimal("2500")))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnStatus400WhenAddressHasOnlyWhiteSpaces() throws Exception {
        mockMvc.perform(post("/proposals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new ProposalRequest("Zoro", "zoro@gmail.com", "54683357003", " ", new BigDecimal("2500")))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnStatus400WhenSalaryIsNull() throws Exception {
        mockMvc.perform(post("/proposals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new ProposalRequest("Zoro", "zoro@gmail.com", "54683357003", "Rua dos Bobos, 0", null))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnStatus400WhenSalaryIsNegative() throws Exception {
        mockMvc.perform(post("/proposals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new ProposalRequest("Zoro", "zoro@gmail.com", "54683357003", "Rua dos Bobos, 0", new BigDecimal("-2500")))))
                .andExpect(status().isBadRequest());
    }

    private String toJson(ProposalRequest request) throws JsonProcessingException {
        return jsonMapper.writeValueAsString(request);
    }

}
