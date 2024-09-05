package com.example.companymanager.controller;

import com.example.companymanager.api.company.CompanyDto;
import com.example.companymanager.api.company.CompanyCreateDto;
import com.example.companymanager.api.company.CompanyUpdateDto;
import com.example.companymanager.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/companies")
@Validated
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyDto> createCompany(@RequestBody @Valid CompanyCreateDto companyCreateDto) {
        CompanyDto result = companyService.create(companyCreateDto);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDto> updateCompany(@PathVariable Long id,
                                                    @RequestBody @Valid CompanyUpdateDto companyUpdateDto) {
        CompanyDto companyDto = companyService.update(id, companyUpdateDto);
        return ResponseEntity.ok(companyDto);
    }

    @GetMapping
    public ResponseEntity<List<CompanyDto>> getAllCompanies() {
        List<CompanyDto> companies = companyService.getAll();
        return ResponseEntity.ok(companies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getCompany(@PathVariable Long id) {
        CompanyDto company = companyService.get(id);
        return ResponseEntity.ok(company);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
