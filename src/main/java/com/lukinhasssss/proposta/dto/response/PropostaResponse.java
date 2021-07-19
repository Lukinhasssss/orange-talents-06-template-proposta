package com.lukinhasssss.proposta.dto.response;

import com.lukinhasssss.proposta.entities.Proposta;
import com.lukinhasssss.proposta.entities.enums.StatusProposta;

public class PropostaResponse {

    private StatusProposta status;

    public PropostaResponse() {}

    public PropostaResponse(Proposta proposta) {
        status = StatusProposta.convert(proposta.getStatus().toString());
    }

    public StatusProposta getStatus() {
        return status;
    }
}
