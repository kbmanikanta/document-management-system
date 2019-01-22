package rs.ac.bg.fon.silab.service;

import rs.ac.bg.fon.silab.dto.company.CompanyGetDto;
import rs.ac.bg.fon.silab.dto.company.CompanySaveDto;

import java.util.List;

public interface CompanyService extends AbstractService<CompanySaveDto, CompanyGetDto, CompanyGetDto> {

    List<CompanyGetDto> getByTaxIdNumber(String taxIdNumber);

}
