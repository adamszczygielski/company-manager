package com.example.companymanager.api.departament;

import com.example.companymanager.api.team.TeamCreateDto;
import jakarta.validation.constraints.NotBlank;

import java.util.List;


public record DepartmentCreateDto(@NotBlank String name, List<TeamCreateDto> teams) {
}
