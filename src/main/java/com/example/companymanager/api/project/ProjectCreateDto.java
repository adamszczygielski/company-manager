package com.example.companymanager.api.project;

import com.example.companymanager.api.manager.ManagerCreateDto;
import jakarta.validation.constraints.NotBlank;


public record ProjectCreateDto(@NotBlank String name, ManagerCreateDto manager) {
}
