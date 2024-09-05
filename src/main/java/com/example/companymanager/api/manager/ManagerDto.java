package com.example.companymanager.api.manager;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record ManagerDto(@NotNull Long id, @NotBlank String contactInfo) {
}
