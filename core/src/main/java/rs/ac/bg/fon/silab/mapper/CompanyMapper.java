package rs.ac.bg.fon.silab.mapper;

import org.springframework.stereotype.Component;
import rs.ac.bg.fon.silab.entity.Company;
import rs.ac.bg.fon.silab.dto.company.CompanyGetDto;
import rs.ac.bg.fon.silab.dto.company.CompanySaveDto;

@Component
public class CompanyMapper {

    public CompanyGetDto toCompanyGetDto(Company company) {
        return new CompanyGetDto(
                company.getId(),
                company.getName(),
                company.getTaxIdNumber());
    }

    public Company toCompany(CompanySaveDto companySaveDto) {
        return new Company(
                companySaveDto.getName(),
                companySaveDto.getTaxIdNumber());
    }

}
