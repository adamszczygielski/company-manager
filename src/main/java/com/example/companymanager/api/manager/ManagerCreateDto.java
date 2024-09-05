package com.example.companymanager.api.manager;

import jakarta.validation.constraints.NotBlank;


public record ManagerCreateDto(@NotBlank String contactInfo) {
}
