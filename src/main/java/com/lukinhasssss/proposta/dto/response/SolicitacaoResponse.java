package com.lukinhasssss.proposta.dto.response;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class SolicitacaoResponse {

    @NotBlank @NotEmpty
    private String nome;

    @NotBlank @NotEmpty
    private String documento;

    @NotBlank @NotEmpty
    private String resultadoSolicitacao;

    @NotBlank @NotEmpty
    private String idProposta;

    public SolicitacaoResponse() {}

    public SolicitacaoResponse(String nome, String documento, String resultadoSolicitacao, String idProposta) {
        this.nome = nome;
        this.documento = documento;
        this.resultadoSolicitacao = resultadoSolicitacao;
        this.idProposta = idProposta;
    }

    public String getNome() {
        return nome;
    }

    public String getDocumento() {
        return documento;
    }

    public String getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }

    public String getIdProposta() {
        return idProposta;
    }
}
