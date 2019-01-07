package rs.ac.bg.fon.silab.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.bg.fon.silab.dao.CompanyDao;
import rs.ac.bg.fon.silab.dao.TemplateDao;
import rs.ac.bg.fon.silab.dto.template.TemplateSaveDto;
import rs.ac.bg.fon.silab.dto.template.item.TemplateItemDto;
import rs.ac.bg.fon.silab.entity.TemplateState;
import rs.ac.bg.fon.silab.exception.ValidationException;

import java.util.*;

@Component
public class TemplateSaveDtoValidator extends DtoValidator<TemplateSaveDto> {

    @Autowired
    private TemplateDao templateDao;

    @Autowired
    private CompanyDao companyDao;

    @Override
    protected void customValidation(TemplateSaveDto templateSaveDto) {
        if (companyDao.getById(templateSaveDto.getCompanyId()) == null) {
            throw new ValidationException(getError("template.company.id.not.found",
                    new Object[] { templateSaveDto.getCompanyId() }, "companyId"));
        }

        List<TemplateItemDto> templateItemDtos = templateSaveDto.getTemplateItems();
        if (templateItemDtos.stream().distinct().count() < templateItemDtos.size()) {
            throw new ValidationException(getError("template.items.duplicate", "templateItems"));
        }
    }

    @Override
    protected void customValidation(TemplateSaveDto templateSaveDto, Integer id) {
        customValidation(templateSaveDto);

        if (templateDao.getById(id).getState() == TemplateState.COMPLETED) {
            throw new ValidationException(getError("template.state.not.draft", "state"));
        }
    }

}
