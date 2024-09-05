package com.example.companymanager.service;

import com.example.companymanager.api.company.CompanyDto;
import com.example.companymanager.api.company.CompanyCreateDto;
import com.example.companymanager.api.company.CompanyUpdateDto;
import com.example.companymanager.data.enitity.Company;
import com.example.companymanager.data.mapper.CompanyMapper;
import com.example.companymanager.data.repository.CompanyRepository;
import com.example.companymanager.service.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Transactional
    public CompanyDto get(Long id) {
        validateCompanyId(id);
        Company company = companyRepository.getReferenceById(id);
        return companyMapper.toCompanyDto(company);
    }

    public List<CompanyDto> getAll() {
        return companyRepository.findAll().stream()
                .map(companyMapper::toCompanyDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public CompanyDto create(CompanyCreateDto companyDto) {
        Company company = companyMapper.toCompany(companyDto);
        Company result = companyRepository.save(company);
        return companyMapper.toCompanyDto(result);
    }

    @Transactional
    public CompanyDto update(Long id, CompanyUpdateDto companyUpdateDto) {
        validateCompanyId(id);
        Company company = companyMapper.toCompany(id, companyUpdateDto);
        Company result = companyRepository.save(company);
        return companyMapper.toCompanyDto(result);
    }

    @Transactional
    public void delete(Long id) {
        validateCompanyId(id);
        companyRepository.deleteById(id);
    }

    private void validateCompanyId(Long companyId) {
        if (!companyRepository.existsById(companyId)) {
            throw new NotFoundException(String.format("Company with id: %d does not exist!", companyId));
        }
    }
}
