package com.lukinhasssss.proposta.repositories;

import com.lukinhasssss.proposta.entities.Carteira;
import com.lukinhasssss.proposta.entities.enums.TipoCarteira;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarteiraRepository extends JpaRepository<Carteira, String> {

    Optional<Carteira> findByIdCartaoAndTipoCarteira(String id, TipoCarteira tipoCarteira);

}
