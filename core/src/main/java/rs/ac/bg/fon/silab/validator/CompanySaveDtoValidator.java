package rs.ac.bg.fon.silab.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.bg.fon.silab.dao.CompanyDao;
import rs.ac.bg.fon.silab.entity.Company;
import rs.ac.bg.fon.silab.dto.company.CompanySaveDto;
import rs.ac.bg.fon.silab.exception.ValidationException;

@Component
public class CompanySaveDtoValidator extends DtoValidator<CompanySaveDto> {

    @Autowired
    private CompanyDao companyDao;

    @Override
    protected void customValidation(CompanySaveDto companySaveDTO) {
        if (companyDao.getByTaxIdNumber(companySaveDTO.getTaxIdNumber()) != null) {
            throw new ValidationException(getError("company.tax.id.number.unique",
                    new Object[] { companySaveDTO.getTaxIdNumber() }, "taxIdNumber"));
        }
    }

    @Override
    protected void customValidation(CompanySaveDto companySaveDTO, Integer id) {
        Company company = companyDao.getByTaxIdNumber(companySaveDTO.getTaxIdNumber());

        if (company != null && company.getTaxIdNumber().equals(companySaveDTO.getTaxIdNumber())
                && !company.getId().equals(id)) {
            throw new ValidationException(getError("company.tax.id.number.unique",
                    new Object[] { companySaveDTO.getTaxIdNumber() }, "taxIdNumber"));
        }
    }

}
