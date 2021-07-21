package com.lukinhasssss.proposta.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "tb_bloqueio_cartao")
public class BloqueioCartao {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private String ipCliente;

    private String userAgent;

    private String idCartao;

    private Instant bloqueadoEm = Instant.now();

    @Deprecated
    private BloqueioCartao() {}

    public BloqueioCartao(String ipCliente, String userAgent, String idCartao) {
        this.ipCliente = ipCliente;
        this.userAgent = userAgent;
        this.idCartao = idCartao;
    }

    public String getId() {
        return id;
    }
}
