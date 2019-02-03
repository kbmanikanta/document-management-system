package rs.ac.bg.fon.silab.service;

import rs.ac.bg.fon.silab.dto.template.TemplateDetailsDto;
import rs.ac.bg.fon.silab.dto.template.TemplateGetDto;
import rs.ac.bg.fon.silab.dto.template.TemplateSaveDto;
import rs.ac.bg.fon.silab.entity.TemplateState;

import java.util.List;

public interface TemplateService extends AbstractService<TemplateSaveDto, TemplateGetDto, TemplateDetailsDto> {

    void complete(Integer id);

    List<TemplateGetDto> getByState(Integer state);

}
