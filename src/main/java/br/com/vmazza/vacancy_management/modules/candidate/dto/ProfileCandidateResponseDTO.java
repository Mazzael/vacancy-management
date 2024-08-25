package br.com.vmazza.vacancy_management.modules.candidate.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCandidateResponseDTO {

    @Schema(example = "Java developer")
    private String description;

    @Schema(example = "john_doe")
    private String userName;

    @Schema(example = "johndoe@example.com")
    private String email;

    private UUID id;

    @Schema(example = "John Doe")
    private String name;
    
}
