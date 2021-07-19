package com.lukinhasssss.proposta.dto.response;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CartaoResponse {

    @NotBlank @NotEmpty
    private String id;

    @NotNull @NotEmpty @NotBlank
    private String idProposta;

    public CartaoResponse() {}

    public CartaoResponse(String id, String idProposta) {
        this.id = id;
        this.idProposta = idProposta;
    }

    public String getId() {
        return id;
    }

    public String getIdProposta() {
        return idProposta;
    }

}
