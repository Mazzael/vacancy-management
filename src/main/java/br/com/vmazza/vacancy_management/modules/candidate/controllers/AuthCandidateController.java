package br.com.vmazza.vacancy_management.modules.candidate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vmazza.vacancy_management.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.vmazza.vacancy_management.modules.candidate.useCases.AuthCandadateUseCase;

@RestController
@RequestMapping("/candidate")
public class AuthCandidateController {

    @Autowired
    private AuthCandadateUseCase authCandadateUseCase;

    @PostMapping("/auth")
    public ResponseEntity<Object> auth(@RequestBody AuthCandidateRequestDTO authCandidateRequestDTO) {

        try {

            var token = this.authCandadateUseCase.execute(authCandidateRequestDTO);

            return ResponseEntity.ok().body(token);

        } catch(Exception e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());

        }

    }
    
}
