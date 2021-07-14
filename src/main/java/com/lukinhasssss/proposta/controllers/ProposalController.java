package com.lukinhasssss.proposta.controllers;

import com.lukinhasssss.proposta.config.validation.StandardErrorMessage;
import com.lukinhasssss.proposta.dto.request.ProposalRequest;
import com.lukinhasssss.proposta.entities.Proposal;
import com.lukinhasssss.proposta.repositories.ProposalRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/proposals")
public class ProposalController {

    private ProposalRepository proposalRepository;

    public ProposalController(ProposalRepository proposalRepository) {
        this.proposalRepository = proposalRepository;
    }

    @PostMapping
    public ResponseEntity<?> newProposal(@RequestBody @Valid ProposalRequest request) {
        Proposal proposal = request.convertToEntity();
        Optional<Proposal> validProposal = proposalRepository.findByDocument(request.getDocument());

        if (validProposal.isEmpty()) {
            proposalRepository.save(proposal);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/proposals/{id}").buildAndExpand(proposal.getId()).toUri();
            return ResponseEntity.created(uri).build();
        }


        return ResponseEntity.unprocessableEntity().body(new StandardErrorMessage("document", "Já existe outra proposta associada a este CPF/CNPJ"));
    }

}
