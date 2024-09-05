package com.example.companymanager.api.departament;

import com.example.companymanager.api.team.TeamDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;


public record DepartmentDto(@NotNull Long id, @NotBlank String name, List<TeamDto> teams) {
}
