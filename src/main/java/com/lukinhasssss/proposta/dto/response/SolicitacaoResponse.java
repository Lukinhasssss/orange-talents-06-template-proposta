package com.lukinhasssss.proposta.dto.response;

public class SolicitacaoResponse {

    private String nome;
    private String documento;
    private String resultadoSolicitacao;
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
