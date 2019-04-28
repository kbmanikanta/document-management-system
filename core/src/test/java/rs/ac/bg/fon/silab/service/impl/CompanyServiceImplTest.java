package rs.ac.bg.fon.silab.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.ac.bg.fon.silab.dao.CompanyDao;
import rs.ac.bg.fon.silab.dto.company.CompanyGetDto;
import rs.ac.bg.fon.silab.dto.company.CompanySaveDto;
import rs.ac.bg.fon.silab.entity.Company;
import rs.ac.bg.fon.silab.exception.EntityNotFoundException;
import rs.ac.bg.fon.silab.mapper.CompanyMapper;
import rs.ac.bg.fon.silab.validator.DtoValidator;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {

    @Mock
    private CompanyDao companyDao;

    @Mock
    private CompanyMapper companyMapper;

    @Mock
    private DtoValidator<CompanySaveDto> dtoValidator;

    @InjectMocks
    private CompanyServiceImpl companyService;

    @Test
    void testInsert() {
        CompanySaveDto companySaveDto = new CompanySaveDto();
        Company company = new Company();

        when(companyMapper.toCompany(companySaveDto)).thenReturn(company);

        companyService.insert(companySaveDto);

        verify(dtoValidator).validate(companySaveDto);
        verify(companyMapper).toCompany(companySaveDto);
        verify(companyDao).insert(company);
    }

    @Test
    void testUpdateWhenCompanyExists() {
        Integer id = 10;
        String name = "SAP";
        String taxIdNumber = "123123123";
        CompanySaveDto companySaveDto = new CompanySaveDto(name, taxIdNumber);
        Company company = spy(new Company());

        when(companyDao.getById(id)).thenReturn(company);

        companyService.update(companySaveDto, id);

        verify(companyDao).getById(id);
        verify(dtoValidator).validate(companySaveDto, id);
        verify(company).setName(name);
        verify(company).setTaxIdNumber(taxIdNumber);
    }

    @Test
    void testUpdateWhenCompanyDoesNotExist() {
        Integer id = 10;
        CompanySaveDto companySaveDto = new CompanySaveDto();

        when(companyDao.getById(id)).thenReturn(null);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> companyService.update(companySaveDto, id),
                "Update should throw EntityNotFoundException.");
        assertEquals("company.not.found", exception.getErrorMessageKey(),
                "ErrorMessageKey should be company.not.found.");
        verify(companyDao).getById(id);
    }

    @Test
    void testDeleteWhenCompanyExists() {
        Integer id = 10;
        Company company = new Company();

        when(companyDao.getById(id)).thenReturn(company);

        companyService.delete(id);

        verify(companyDao).getById(id);
        verify(companyDao).delete(company);
    }

    @Test
    void testDeleteWhenCompanyDoesNotExist() {
        Integer id = 10;

        when(companyDao.getById(id)).thenReturn(null);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> companyService.delete(id),
                "Delete should throw EntityNotFoundException.");
        assertEquals("company.not.found", exception.getErrorMessageKey(),
                "ErrorMessageKey should be company.not.found.");
        verify(companyDao).getById(id);
    }

    @Test
    void testGetByIdWhenCompanyExists() {
        Integer id = 10;
        Company company = new Company();
        CompanyGetDto companyGetDto = new CompanyGetDto();

        when(companyDao.getById(id)).thenReturn(company);
        when(companyMapper.toCompanyGetDto(company)).thenReturn(companyGetDto);

        CompanyGetDto returnedCompany = companyService.getById(id);

        assertEquals(returnedCompany, companyGetDto,
                "Returned companyDto should be the same as returned companyDto from mapper.");
        verify(companyDao).getById(id);
        verify(companyMapper).toCompanyGetDto(company);
    }

    @Test
    void testGetByIdWhenCompanyDoesNotExist() {
        Integer id = 10;

        when(companyDao.getById(id)).thenReturn(null);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> companyService.getById(id),
                "GetById should throw EntityNotFoundException.");
        assertEquals("company.not.found", exception.getErrorMessageKey(),
                "ErrorMessageKey should be company.not.found.");
        verify(companyDao).getById(id);
    }

    @Test
    void testGetAll() {
        List<Company> companies = IntStream.rangeClosed(1, 3).mapToObj(i -> new Company())
                .collect(Collectors.toList());
        List<CompanyGetDto> companyGetDtos = IntStream.rangeClosed(1, 3).mapToObj(i -> new CompanyGetDto())
                .collect(Collectors.toList());


        when(companyDao.getAll()).thenReturn(companies);
        IntStream.rangeClosed(0, 2).forEach(i ->
                doReturn(companyGetDtos.get(i)).when(companyMapper).toCompanyGetDto(companies.get(i)));

        List<CompanyGetDto> returnedCompanies = companyService.getAll();

        ArgumentCaptor<Company> companyCaptor = ArgumentCaptor.forClass(Company.class);

        assertAll(
                () -> assertEquals(companyGetDtos, returnedCompanies,
                        "Returned companyDtos should be the same as returned companyDtos from mapper."),
                () -> assertEquals(3, returnedCompanies.size(), "Size of companies list should be 3.")
        );
        verify(companyDao).getAll();
        verify(companyMapper, times(3)).toCompanyGetDto(companyCaptor.capture());
    }

    @Test
    void testGetByTaxIdNumber() {
        String taxIdNumber = "123123123";
        List<Company> companies = Collections.singletonList(new Company());
        List<CompanyGetDto> companyGetDtos = Collections.singletonList(new CompanyGetDto());

        when(companyDao.getByTaxIdNumber(taxIdNumber)).thenReturn(companies);
        when(companyMapper.toCompanyGetDto(companies.get(0))).thenReturn(companyGetDtos.get(0));

        List<CompanyGetDto> returnedCompanies = companyService.getByTaxIdNumber(taxIdNumber);

        assertAll(
                () -> assertEquals(companyGetDtos, returnedCompanies,
                        "Returned companyDtos should be the same as returned companyDtos from mapper."),
                () -> assertEquals(1, returnedCompanies.size(), "Size of companies list should be 1.")
        );
        verify(companyDao).getByTaxIdNumber(taxIdNumber);
        verify(companyMapper).toCompanyGetDto(companies.get(0));
    }

}
