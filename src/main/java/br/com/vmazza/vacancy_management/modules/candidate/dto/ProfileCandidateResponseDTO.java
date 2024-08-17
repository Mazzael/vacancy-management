package br.com.vmazza.vacancy_management.modules.candidate.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCandidateResponseDTO {

    private String description;
    private String userName;
    private String email;
    private UUID id;
    private String name;
    
}
