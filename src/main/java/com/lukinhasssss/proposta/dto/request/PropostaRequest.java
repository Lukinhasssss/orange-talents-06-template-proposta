package com.lukinhasssss.proposta.dto.request;

import com.lukinhasssss.proposta.config.validation.Document;
import com.lukinhasssss.proposta.config.validation.UniqueValue;
import com.lukinhasssss.proposta.entities.Proposta;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class PropostaRequest {

    @NotBlank @NotEmpty
    private String nome;

    @NotBlank @NotEmpty
    @Email
    private String email;

    @NotBlank @NotEmpty
    @Document
    @UniqueValue(domainClass = Proposta.class, fieldName = "documento", message = "Já existe uma proposta associada a este CPF/CNPJ")
    private String documento;

    @NotBlank @NotEmpty
    private String endereco;

    @NotNull @PositiveOrZero
    private BigDecimal salario;

    public PropostaRequest(String nome, String email, String documento, String endereco, BigDecimal salario) {
        this.nome = nome;
        this.email = email;
        this.documento = documento;
        this.endereco = endereco;
        this.salario = salario;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getDocumento() {
        return documento;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public Proposta converterParaEntidade(TextEncryptor encriptor) {
        return new Proposta(nome, email, encriptor.encrypt(documento), endereco, salario);
    }

}
