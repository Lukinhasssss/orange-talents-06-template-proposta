package com.lukinhasssss.proposta.entities;

import com.lukinhasssss.proposta.entities.enums.TipoCarteira;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "tb_carteira")
public class Carteira {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private TipoCarteira tipoCarteira;

    private String idCartao;

    @Deprecated
    public Carteira() {}

    public Carteira(String email, TipoCarteira tipoCarteira, String idCartao) {
        this.email = email;
        this.tipoCarteira = tipoCarteira;
        this.idCartao = idCartao;
    }

    public String getId() {
        return id;
    }
}
