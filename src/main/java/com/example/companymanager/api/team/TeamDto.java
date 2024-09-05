package com.example.companymanager.api.team;

import com.example.companymanager.api.project.ProjectDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record TeamDto(@NotNull Long id, @NotBlank String name, ProjectDto project) {
}
