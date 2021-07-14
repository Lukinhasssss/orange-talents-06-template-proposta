package com.lukinhasssss.proposta.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_proposal")
public class Proposal {

    @Id @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, unique = true)
    private String document;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private BigDecimal salary;

    @Deprecated
    public Proposal() {}

    public Proposal(String name, String email, String document, String address, BigDecimal salary) {
        this.name = name;
        this.email = email;
        this.document = document;
        this.address = address;
        this.salary = salary;
    }

    public String getId() {
        return id;
    }
}
