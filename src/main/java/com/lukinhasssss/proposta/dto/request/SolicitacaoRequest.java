package com.lukinhasssss.proposta.dto.request;

import com.lukinhasssss.proposta.entities.Proposta;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class SolicitacaoRequest {

    private String nome;
    private String documento;
    private String idProposta;

    public SolicitacaoRequest() {}

    public SolicitacaoRequest(Proposta proposta) {
        nome = proposta.getNome();
        documento = proposta.getDocumento();
        idProposta = proposta.getId();
    }

    public String getNome() {
        return nome;
    }

    public String getDocumento() {
        return documento;
    }

    public String getIdProposta() {
        return idProposta;
    }
}
