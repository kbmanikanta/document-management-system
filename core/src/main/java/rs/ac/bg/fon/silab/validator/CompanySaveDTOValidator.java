package rs.ac.bg.fon.silab.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import rs.ac.bg.fon.silab.dao.CompanyDao;
import rs.ac.bg.fon.silab.entity.Company;
import rs.ac.bg.fon.silab.dto.company.CompanySaveDTO;
import rs.ac.bg.fon.silab.exception.ValidationException;

import java.util.*;

@Component
public class CompanySaveDTOValidator extends DTOValidator<CompanySaveDTO> {

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private MessageSource messageSource;

    @Override
    void customValidation(CompanySaveDTO companySaveDTO) {
        if (companyDao.getByTaxIdNumber(companySaveDTO.getTaxIdNumber()) != null) {
            throw new ValidationException(getErrors(companySaveDTO));
        }
    }

    @Override
    void customValidation(CompanySaveDTO companySaveDTO, Integer id) {
        Company company = companyDao.getByTaxIdNumber(companySaveDTO.getTaxIdNumber());

        if (company != null && company.getTaxIdNumber().equals(companySaveDTO.getTaxIdNumber())
                && !company.getId().equals(id)) {
            throw new ValidationException(getErrors(companySaveDTO));
        }
    }

    private Map<String, List<String>> getErrors(CompanySaveDTO companySaveDTO) {
        String taxIdNumberErrorMessage = messageSource.getMessage("company.tax.id.number.unique",
                new Object[] { companySaveDTO.getTaxIdNumber() }, Locale.getDefault());

        return new HashMap<String, List<String>>() {{
            put("taxIdNumber", Collections.singletonList(taxIdNumberErrorMessage));
        }};
    }

}
