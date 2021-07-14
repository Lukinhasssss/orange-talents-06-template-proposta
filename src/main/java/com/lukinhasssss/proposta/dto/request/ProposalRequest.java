package com.lukinhasssss.proposta.dto.request;

import com.lukinhasssss.proposta.config.validation.Document;
import com.lukinhasssss.proposta.entities.Proposal;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class ProposalRequest {

    @NotBlank @NotEmpty
    private String name;

    @NotBlank @NotEmpty
    @Email
    private String email;

    @NotBlank @NotEmpty
    @Document
    private String document;

    @NotBlank @NotEmpty
    private String address;

    @NotNull @PositiveOrZero
    private BigDecimal salary;

    public ProposalRequest(String name, String email, String document, String address, BigDecimal salary) {
        this.name = name;
        this.email = email;
        this.document = document;
        this.address = address;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDocument() {
        return document;
    }

    public String getAddress() {
        return address;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public Proposal convertToEntity() {
        return new Proposal(name, email, document, address, salary);
    }

}
