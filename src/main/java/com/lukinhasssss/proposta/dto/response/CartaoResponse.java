package com.lukinhasssss.proposta.dto.response;

import com.lukinhasssss.proposta.entities.Cartao;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CartaoResponse {

    @NotBlank @NotEmpty
    private String id;

    @NotBlank @NotEmpty
    private String titular;

    @NotNull
    private BigDecimal limite;

    @NotNull @NotEmpty @NotBlank
    private String idProposta;

    @NotNull
    private LocalDateTime emitidoEm;

    public CartaoResponse() {}

    public CartaoResponse(String id, String titular, BigDecimal limite, String idProposta, LocalDateTime emitidoEm) {
        this.id = id;
        this.titular = titular;
        this.limite = limite;
        this.idProposta = idProposta;
        this.emitidoEm = emitidoEm;
    }

    public String getId() {
        return id;
    }

    public String getTitular() {
        return titular;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public String getIdProposta() {
        return idProposta;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public Cartao converterParaEntidade() {
        return new Cartao(titular, id, limite, emitidoEm);
    }

}
