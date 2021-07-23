package com.lukinhasssss.proposta.dto.request;

import com.lukinhasssss.proposta.entities.enums.TipoCarteira;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class CarteiraRequest {

    @Email
    @NotBlank @NotEmpty
    private String email;

    private TipoCarteira carteira;

    public CarteiraRequest() {}

    public CarteiraRequest(String email, TipoCarteira carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public String getEmail() {
        return email;
    }

    public TipoCarteira getCarteira() {
        return carteira;
    }
}
