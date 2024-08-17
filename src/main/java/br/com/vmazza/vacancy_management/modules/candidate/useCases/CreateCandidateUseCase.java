package br.com.vmazza.vacancy_management.modules.candidate.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.vmazza.vacancy_management.exceptions.UserFoundException;
import br.com.vmazza.vacancy_management.modules.candidate.CandidateEntity;
import br.com.vmazza.vacancy_management.modules.candidate.CandidateRepository;

@Service
public class CreateCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CandidateEntity execute(CandidateEntity candidateEntity) {
        this.candidateRepository
        .findByUserNameOrEmail(candidateEntity.getUserName(), candidateEntity.getEmail())
        .ifPresent((user) -> {
            throw new UserFoundException();
        });

        var passwordHash = passwordEncoder.encode(candidateEntity.getPassword());

        candidateEntity.setPassword(passwordHash);

        return this.candidateRepository.save(candidateEntity);
    }
    
}
