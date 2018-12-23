package rs.ac.bg.fon.silab.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import rs.ac.bg.fon.silab.entity.Company;
import rs.ac.bg.fon.silab.dto.company.CompanyGetDTO;
import rs.ac.bg.fon.silab.dto.company.CompanySaveDTO;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name")
    })
    CompanyGetDTO companyToCompanyGetDTO(Company company);

    @Mappings({
            @Mapping(target = "name", source = "name")
    })
    Company companySaveDTOToCompany(CompanySaveDTO companySaveDTO);
}
