package br.com.vmazza.vacancy_management.modules.candidate.useCases;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.vmazza.vacancy_management.exceptions.JobNotFoundException;
import br.com.vmazza.vacancy_management.exceptions.UserNotFoundException;
import br.com.vmazza.vacancy_management.modules.candidate.CandidateEntity;
import br.com.vmazza.vacancy_management.modules.candidate.CandidateRepository;
import br.com.vmazza.vacancy_management.modules.candidate.entity.ApplyJobEntity;
import br.com.vmazza.vacancy_management.modules.candidate.repository.ApplyJobRepository;
import br.com.vmazza.vacancy_management.modules.company.entities.JobEntity;
import br.com.vmazza.vacancy_management.modules.company.repositories.JobRepository;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {

    @InjectMocks
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private ApplyJobRepository applyJobRepository;

    @Test
    @DisplayName("Should not be able to apply job with candidate not found")
    public void should_not_be_able_to_apply_job_with_candidate_not_found() {
        try {
            applyJobCandidateUseCase.execute(null, null);
        } catch (Exception e) {
            assertInstanceOf(UserNotFoundException.class, e);
        }
    }

    @Test
    @DisplayName("Should not be able to apply job with job not found")
    public void should_not_be_able_to_apply_job_with_job_not_found() {
        var candidateId = UUID.randomUUID();

        var candidate = new CandidateEntity();
        candidate.setId(candidateId);

        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));

        try {
            applyJobCandidateUseCase.execute(candidateId, null);
        } catch (Exception e) {
            assertInstanceOf(JobNotFoundException.class, e);
        }
    }

    @Test
    @DisplayName("Should be able to apply to a job")
    public void should_be_able_to_apply_to_a_job() {
        var candidateId = UUID.randomUUID();
        var jobId = UUID.randomUUID();

        var applyJob = ApplyJobEntity.builder()
        .candidateId(candidateId)
        .jobId(jobId).build();

        var applyJobCreated = ApplyJobEntity.builder().id(UUID.randomUUID()).build();

        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(new CandidateEntity()));
        when(jobRepository.findById(jobId)).thenReturn(Optional.of(new JobEntity()));

        when(applyJobRepository.save(applyJob)).thenReturn(applyJobCreated);

        var result = applyJobCandidateUseCase.execute(candidateId, jobId);
        
        assertThat(result, hasProperty("id"));
        assertNotNull(result.getId());
    }
    
}