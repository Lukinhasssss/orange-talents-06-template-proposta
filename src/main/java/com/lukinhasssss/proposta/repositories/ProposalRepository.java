package com.lukinhasssss.proposta.repositories;

import com.lukinhasssss.proposta.entities.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProposalRepository extends JpaRepository<Proposal, String> {
}
