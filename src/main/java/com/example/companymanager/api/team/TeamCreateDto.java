package com.example.companymanager.api.team;

import com.example.companymanager.api.project.ProjectCreateDto;
import jakarta.validation.constraints.NotBlank;


public record TeamCreateDto(@NotBlank String name, ProjectCreateDto project) {
}
