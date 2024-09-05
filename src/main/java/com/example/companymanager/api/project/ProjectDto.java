package com.example.companymanager.api.project;

import com.example.companymanager.api.manager.ManagerDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record ProjectDto(@NotNull Long id, @NotBlank String name, ManagerDto manager) {
}
