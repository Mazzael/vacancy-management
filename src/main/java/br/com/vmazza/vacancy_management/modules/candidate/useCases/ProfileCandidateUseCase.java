package br.com.vmazza.vacancy_management.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vmazza.vacancy_management.exceptions.UserNotFoundException;
import br.com.vmazza.vacancy_management.modules.candidate.CandidateRepository;
import br.com.vmazza.vacancy_management.modules.candidate.dto.ProfileCandidateResponseDTO;

@Service
public class ProfileCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public ProfileCandidateResponseDTO execute(UUID candidateId) {

        var candidate = this.candidateRepository.findById(candidateId)
            .orElseThrow(() -> {
                throw new UserNotFoundException();
            });

        var candidateResponseDTO = ProfileCandidateResponseDTO.builder()
                .description(candidate.getDescription())
                .userName(candidate.getUserName())
                .email(candidate.getEmail())
                .name(candidate.getName())
                .id(candidate.getId())
                .build();

        return candidateResponseDTO;

    }
    
}
