package com.lukinhasssss.proposta.repositories;

import com.lukinhasssss.proposta.entities.Proposta;
import com.lukinhasssss.proposta.entities.enums.StatusProposta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropostaRepository extends JpaRepository<Proposta, String> {

    List<Proposta> findByStatus(StatusProposta statusProposta);

    List<Proposta> findByStatusAndCartaoIsNull(StatusProposta statusProposta);

}
