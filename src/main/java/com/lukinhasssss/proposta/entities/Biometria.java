package com.lukinhasssss.proposta.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "tb_biometria")
public class Biometria {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private String fingerprint;

    private String idCartao;

    private Instant adicionadaEm = Instant.now();

    public Biometria(String fingerprint, String idCartao) {
        this.fingerprint = fingerprint;
        this.idCartao = idCartao;
    }

    public String getId() {
        return id;
    }
}
