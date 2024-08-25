package br.com.vmazza.vacancy_management.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Data;

@Data
public class CreateJobDTO {

    @Schema(example = "Vacancy for junior developer", requiredMode = RequiredMode.REQUIRED)
    private String description;

    @Schema(example = "Total pass, stock options", requiredMode = RequiredMode.REQUIRED)
    private String benefits;

    @Schema(example = "JUNIOR", requiredMode = RequiredMode.REQUIRED)
    private String level;
    
}
