package br.com.vmazza.vacancy_management.modules.candidate.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.vmazza.vacancy_management.modules.candidate.CandidateEntity;
import br.com.vmazza.vacancy_management.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.vmazza.vacancy_management.modules.candidate.useCases.ApplyJobCandidateUseCase;
import br.com.vmazza.vacancy_management.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.vmazza.vacancy_management.modules.candidate.useCases.ListAllJobsByFilterUseCase;
import br.com.vmazza.vacancy_management.modules.candidate.useCases.ProfileCandidateUseCase;
import br.com.vmazza.vacancy_management.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidate", description = "Candidate informations")
public class CandidateController {

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;

    @Autowired
    private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

    @Autowired
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @PostMapping("/")
    @Operation(summary = "Candidate registration", description = "This route is responsible for register a candidate")
    @ApiResponses({
     @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = CandidateEntity.class))
     }),
     @ApiResponse(responseCode = "400", description = "User already exists.")
    })
    public ResponseEntity<Object> create( @Valid @RequestBody CandidateEntity candidateEntity) {
       try {
            var result = this.createCandidateUseCase.execute(candidateEntity);
            return ResponseEntity.ok().body(result);
       } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
       }
    }
    
    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Candidate profile", description = "This route is responsible for fetching the profile's informations of the candidate")
    @ApiResponses({
     @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))
     }),
     @ApiResponse(responseCode = "400", description = "User not found")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> get(HttpServletRequest request) {

     var candidateId = request.getAttribute("candidate_id");

      try {
          var profile = this.profileCandidateUseCase.execute(UUID.fromString(candidateId.toString()));
          return ResponseEntity.ok().body(profile);
     } catch (Exception e) {
          return ResponseEntity.badRequest().body(e.getMessage());
     }

    }

    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Fetch of vacancies for the candidate", description = "This route is responsible for fetching all vacancies availables based on the given filter")
    @ApiResponses({
     @ApiResponse(responseCode = "200", content = {
          @Content(
               array = @ArraySchema(schema = @Schema(implementation = JobEntity.class))
          )
     })
    })
    @SecurityRequirement(name = "jwt_auth")
    public List<JobEntity> findJobByFilter(@RequestParam String filter) {
          return this.listAllJobsByFilterUseCase.execute(filter);
    }
    
    @PostMapping("/job/apply")
    @PreAuthorize("hasROLE('CANDIDATE')")
    @SecurityRequirement(name = "jwt_auth")
    @Operation(summary = "Candidate apply to a job", description = "This route is responsible for apllying a candidate to a job")
    public ResponseEntity<Object> applyJob(HttpServletRequest request, @RequestBody UUID jobId) {

          var candidateId = request.getAttribute("candidate_id");

          try {
               var result = applyJobCandidateUseCase.execute(UUID.fromString(candidateId.toString()), jobId);

               return ResponseEntity.ok().body(result);
          } catch(Exception e) {
               return ResponseEntity.badRequest().body(e.getMessage());
          }
    }
}
