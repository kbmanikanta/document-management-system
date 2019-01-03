package rs.ac.bg.fon.silab.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import rs.ac.bg.fon.silab.dao.CompanyDao;
import rs.ac.bg.fon.silab.dto.template.TemplateSaveDto;
import rs.ac.bg.fon.silab.dto.template.item.TemplateItemDto;
import rs.ac.bg.fon.silab.exception.ValidationException;

import java.util.*;

@Component
public class TemplateSaveDtoValidator extends DtoValidator<TemplateSaveDto> {

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private MessageSource messageSource;

    @Override
    void customValidation(TemplateSaveDto templateSaveDto) {
        if (companyDao.getById(templateSaveDto.getCompanyId()) == null) {
            throw new ValidationException(getCompanyNotFoundError(templateSaveDto));
        }

        List<TemplateItemDto> templateItemDtos = templateSaveDto.getTemplateItems();
        if (templateItemDtos.stream().distinct().count() < templateItemDtos.size()) {
            throw new ValidationException(getTemplateItemsDuplicateError());
        }
    }

    @Override
    void customValidation(TemplateSaveDto templateSaveDto, Integer id) {
        customValidation(templateSaveDto);
    }

    private Map<String, List<String>> getCompanyNotFoundError(TemplateSaveDto templateSaveDto) {
        String taxIdNumberErrorMessage = messageSource.getMessage("template.company.id.not.found",
                new Object[] { templateSaveDto.getCompanyId() }, Locale.getDefault());

        return new HashMap<String, List<String>>() {{
            put("companyId", Collections.singletonList(taxIdNumberErrorMessage));
        }};
    }

    private Map<String, List<String>> getTemplateItemsDuplicateError() {
        String templateItemsErrorMessage = messageSource.getMessage("template.items.duplicate",
                null, Locale.getDefault());

        return new HashMap<String, List<String>>() {{
            put("templateItems", Collections.singletonList(templateItemsErrorMessage));
        }};
    }

}
