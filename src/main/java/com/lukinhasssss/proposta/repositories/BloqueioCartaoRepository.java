package com.lukinhasssss.proposta.repositories;

import com.lukinhasssss.proposta.entities.BloqueioCartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BloqueioCartaoRepository extends JpaRepository<BloqueioCartao, String> {

    Optional<BloqueioCartao> findByIdCartao(String idCartao);

}
