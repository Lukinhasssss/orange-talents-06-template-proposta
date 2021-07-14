package com.lukinhasssss.proposta.repositories;

import com.lukinhasssss.proposta.entities.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProposalRepository extends JpaRepository<Proposal, String> {

    Optional<Proposal> findByDocument(String document);

}
