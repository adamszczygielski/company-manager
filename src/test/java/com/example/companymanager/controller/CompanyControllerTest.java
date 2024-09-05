package com.example.companymanager.controller;

import com.example.companymanager.api.ErrorDto;
import com.example.companymanager.api.company.CompanyCreateDto;
import com.example.companymanager.api.company.CompanyDto;
import com.example.companymanager.api.departament.DepartmentCreateDto;
import com.example.companymanager.api.departament.DepartmentDto;
import com.example.companymanager.api.manager.ManagerCreateDto;
import com.example.companymanager.api.manager.ManagerDto;
import com.example.companymanager.api.project.ProjectCreateDto;
import com.example.companymanager.api.project.ProjectDto;
import com.example.companymanager.api.team.TeamCreateDto;
import com.example.companymanager.api.team.TeamDto;
import com.example.companymanager.data.enitity.Company;
import com.example.companymanager.data.enitity.Department;
import com.example.companymanager.data.enitity.Manager;
import com.example.companymanager.data.enitity.Project;
import com.example.companymanager.data.enitity.Team;
import com.example.companymanager.data.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.stream.Stream;

import static com.example.companymanager.CompanyFixtures.companyCreateDto;
import static com.example.companymanager.RestUtils.consumeRestEndpoint;
import static com.example.companymanager.RestUtils.toHttpEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class CompanyControllerTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Value("${local.server.port}")
    private int port;

    private static Stream<String> blankOrNullStrings() {
        return Stream.of("", " ", null);
    }

    @BeforeEach
    public void clear() {
        companyRepository.deleteAll();
    }

    @Test
    public void shouldCreateCompany() {
        //given
        String url = "http://localhost:" + port + "/companies";
        HttpEntity<CompanyCreateDto> httpEntity = toHttpEntity(companyCreateDto());

        //when
        ResponseEntity<CompanyDto> responseEntity = consumeRestEndpoint(HttpMethod.POST, url, httpEntity,
                CompanyDto.class);

        //then
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

        CompanyDto companyDto = responseEntity.getBody();

        assertNotNull(companyDto);
        assertNotNull(companyDto.id());
        assertEquals("Company name", companyDto.name());

        List<DepartmentDto> departments = companyDto.departments();
        assertNotNull(departments);
        assertEquals(1, departments.size());

        DepartmentDto departmentDto = departments.getFirst();
        assertNotNull(departmentDto.id());
        assertEquals("Department name", departmentDto.name());

        List<TeamDto> teams = departmentDto.teams();
        assertNotNull(teams);
        assertEquals(1, teams.size());

        TeamDto teamDto = teams.getFirst();
        assertNotNull(teamDto);
        assertNotNull(teamDto.id());
        assertEquals("Team name", teamDto.name());

        ProjectDto project = teamDto.project();
        assertNotNull(project);
        assertNotNull(project.id());
        assertEquals("Project name", project.name());

        ManagerDto manager = project.manager();
        assertNotNull(manager);
        assertNotNull(manager.id());
        assertEquals("Manager contact info", manager.contactInfo());
    }

    @ParameterizedTest
    @MethodSource("blankOrNullStrings")
    public void shouldReturnError_OnCompanyCreate_WhenNameIsBlank(String companyName) {
        //given
        String url = "http://localhost:" + port + "/companies";
        HttpEntity<CompanyCreateDto> httpEntity = toHttpEntity(new CompanyCreateDto("", null));

        //when
        ResponseEntity<ErrorDto> responseEntity = consumeRestEndpoint(HttpMethod.POST, url, httpEntity,
                ErrorDto.class);

        //then
        assertTrue(responseEntity.getStatusCode().is4xxClientError());
        ErrorDto body = responseEntity.getBody();
        assertNotNull(body);
        assertEquals("name: must not be blank", body.message());
    }

    @Test
    public void shouldGetCompany() {
        //given
        createCompany("Company name 1", "Department name 1",
                "Contact info 1", "Team name 1", "Project name 1");
        Company company2 = createCompany("Company name 2", "Department name 2",
                "Contact info 2", "Team name 2", "Project name 2");

        //when
        String url = "http://localhost:" + port + "/companies/" + company2.getId();
        ResponseEntity<CompanyDto> responseEntity = consumeRestEndpoint(HttpMethod.GET, url, null, CompanyDto.class);

        //then
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        CompanyDto companyDto = responseEntity.getBody();
        assertNotNull(companyDto);
        assertEquals(company2.getId(), companyDto.id());
    }

    @Test
    public void shouldReturnError_OnGetCompany_WhenNoCompany() {
        //when
        String url = "http://localhost:" + port + "/companies/" + 999;
        ResponseEntity<ErrorDto> responseEntity = consumeRestEndpoint(HttpMethod.GET, url, null, ErrorDto.class);

        //then
        assertTrue(responseEntity.getStatusCode().is4xxClientError());
        ErrorDto body = responseEntity.getBody();
        assertNotNull(body);
        assertEquals("Company with id: 999 does not exist!", body.message());
    }

    @Test
    public void shouldRetrieveAllCompanies() {
        //given
        createCompany("Company name 1", "Department name 1",
                "Contact info 1", "Team name 1", "Project name 1");
        createCompany("Company name 2", "Department name 2",
                "Contact info 2", "Team name 2", "Project name 2");

        //when
        String url = "http://localhost:" + port + "/companies";
        ResponseEntity<CompanyDto[]> responseEntity = consumeRestEndpoint(HttpMethod.GET, url, null, CompanyDto[].class);

        //then
        CompanyDto[] companies = responseEntity.getBody();
        assertNotNull(companies);
        assertEquals(2, companies.length);
    }

    @Test
    public void shouldDeleteCompany() {
        //given
        Company company = createCompany("Company name 1", "Department name 1",
                "Contact info 1", "Team name 1", "Project name 1");
        createCompany("Company name 2", "Department name 2",
                "Contact info 2", "Team name 2", "Project name 2");

        //when
        String url = "http://localhost:" + port + "/companies/" + company.getId();
        consumeRestEndpoint(HttpMethod.DELETE, url, null, Void.class);

        //then
        List<Company> companies = companyRepository.findAll();
        assertEquals(1, companies.size());
        assertEquals("Company name 2", companies.getFirst().getName());
    }

    @Test
    public void shouldReturnError_OnCompanyDelete_WhenNoCompany() {
        //when
        String url = "http://localhost:" + port + "/companies/" + 999;
        ResponseEntity<ErrorDto> responseEntity = consumeRestEndpoint(HttpMethod.DELETE, url, null, ErrorDto.class);

        //then
        assertTrue(responseEntity.getStatusCode().is4xxClientError());
        ErrorDto body = responseEntity.getBody();
        assertNotNull(body);
        assertEquals("Company with id: 999 does not exist!", body.message());
    }

    private Company createCompany(String companyName, String departmentName, String contactInfo, String teamName,
                                  String projectName) {
        Manager manager = new Manager();
        manager.setContactInfo(contactInfo);

        Project project = new Project();
        project.setName(projectName);
        project.setManager(manager);

        Team team = new Team();
        team.setName(teamName);

        Department department = new Department();
        department.setName(departmentName);
        department.setTeams(List.of(team));

        Company company = new Company();
        company.setName(companyName);
        company.setDepartments(List.of(department));

        return companyRepository.save(company);
    }
}
