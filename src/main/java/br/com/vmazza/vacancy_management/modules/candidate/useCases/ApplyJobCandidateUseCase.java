package br.com.vmazza.vacancy_management.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vmazza.vacancy_management.exceptions.JobNotFoundException;
import br.com.vmazza.vacancy_management.exceptions.UserNotFoundException;
import br.com.vmazza.vacancy_management.modules.candidate.CandidateRepository;
import br.com.vmazza.vacancy_management.modules.candidate.entity.ApplyJobEntity;
import br.com.vmazza.vacancy_management.modules.candidate.repository.ApplyJobRepository;
import br.com.vmazza.vacancy_management.modules.company.repositories.JobRepository;

@Service
public class ApplyJobCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplyJobRepository applyJobRepository;


    public ApplyJobEntity execute(UUID candidateId, UUID jobId) {
        candidateRepository.findById(candidateId)
        .orElseThrow(() -> {
            throw new UserNotFoundException();
        });

        jobRepository.findById(jobId)
        .orElseThrow(() -> {
            throw new JobNotFoundException();
        });

        var applyJob = ApplyJobEntity.builder()
        .candidateId(candidateId)
        .jobId(jobId).build();

        applyJob = applyJobRepository.save(applyJob);

        return applyJob;
    }
    
}
