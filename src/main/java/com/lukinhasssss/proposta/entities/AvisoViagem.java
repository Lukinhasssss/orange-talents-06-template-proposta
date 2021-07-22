package com.lukinhasssss.proposta.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "tb_aviso_viagem")
public class AvisoViagem {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(nullable = false)
    private String destino;

    private LocalDate terminoViagem;

    private String ipCliente;

    private String userAgent;

    @Column(nullable = false)
    private String idCartao;

    private Instant criadoEm = Instant.now();

    @Deprecated
    public AvisoViagem() {}

    public AvisoViagem(String destino, LocalDate terminoViagem, String ipCliente, String userAgent, String idCartao) {
        this.destino = destino;
        this.terminoViagem = terminoViagem;
        this.ipCliente = ipCliente;
        this.userAgent = userAgent;
        this.idCartao = idCartao;
    }
}
