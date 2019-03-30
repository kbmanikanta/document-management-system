package rs.ac.bg.fon.silab.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.ac.bg.fon.silab.dto.company.CompanyGetDto;
import rs.ac.bg.fon.silab.dto.company.CompanySaveDto;
import rs.ac.bg.fon.silab.entity.Company;

import static org.junit.jupiter.api.Assertions.*;

class CompanyMapperTest {

    private CompanyMapper companyMapper;

    @BeforeEach
    void setUp() {
        companyMapper = new CompanyMapper();
    }

    @Test
    void testCompanyToCompanyGetDto() {
        Company company = new Company("SAP", "123123123");
        company.setId(1);

        CompanyGetDto companyGetDto = companyMapper.toCompanyGetDto(company);

        assertAll(
                () -> assertEquals(companyGetDto.getId(), company.getId()),
                () -> assertEquals(companyGetDto.getName(), company.getName()),
                () -> assertEquals(companyGetDto.getTaxIdNumber(), company.getTaxIdNumber())
        );
    }

    @Test
    void testCompanySaveDtoToCompany() {
        CompanySaveDto companySaveDto = new CompanySaveDto("SAP", "123123123");

        Company company = companyMapper.toCompany(companySaveDto);

        assertAll(
                () -> assertEquals(company.getName(), companySaveDto.getName()),
                () -> assertEquals(company.getTaxIdNumber(), companySaveDto.getTaxIdNumber())
        );
    }

}
