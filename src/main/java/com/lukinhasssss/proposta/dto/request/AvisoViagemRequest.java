package com.lukinhasssss.proposta.dto.request;

import com.lukinhasssss.proposta.entities.AvisoViagem;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvisoViagemRequest {

    @NotBlank @NotEmpty
    private String destino;

    @NotNull @Future
    private LocalDate terminoViagem;

    public AvisoViagemRequest() {}

    public AvisoViagemRequest(String destino, LocalDate terminoViagem) {
        this.destino = destino;
        this.terminoViagem = terminoViagem;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getTerminoViagem() {
        return terminoViagem;
    }

    public AvisoViagem converterParaEntidade(String userAgent, HttpServletRequest httpRequest, String id) {
        return new AvisoViagem(destino, terminoViagem, httpRequest.getRemoteAddr(), userAgent, id);
    }
}
