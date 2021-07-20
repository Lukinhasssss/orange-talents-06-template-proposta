package com.lukinhasssss.proposta.dto.request;

import com.lukinhasssss.proposta.entities.Biometria;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class BiometriaRequest {

    @NotBlank @NotEmpty
    @Pattern(regexp = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$", message = "Ocorreu um erro ao cadastrar a biometria!")
    private String fingerprint;

    public BiometriaRequest() {}

    public BiometriaRequest(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public Biometria converterParaEntidade(String id) {
        return new Biometria(fingerprint, id);
    }
}
