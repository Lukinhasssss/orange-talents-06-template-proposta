package com.lukinhasssss.proposta.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_cartao")
public class Cartao {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(nullable = false)
    private String titular;

    @Column(nullable = false, unique = true)
    private String numeroCartao;

    @Column(nullable = false)
    private BigDecimal limite;

    private LocalDateTime emitidoEm;

    @Deprecated
    public Cartao() {}

    public Cartao(String titular, String numeroCartao, BigDecimal limite, LocalDateTime emitidoEm) {
        this.titular = titular;
        this.numeroCartao = numeroCartao;
        this.limite = limite;
        this.emitidoEm = emitidoEm;
    }

    public String getId() {
        return id;
    }

    public String getTitular() {
        return titular;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

}
