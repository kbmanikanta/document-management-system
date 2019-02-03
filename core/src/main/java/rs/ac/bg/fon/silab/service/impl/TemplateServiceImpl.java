package rs.ac.bg.fon.silab.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.silab.dao.CompanyDao;
import rs.ac.bg.fon.silab.dao.TemplateDao;
import rs.ac.bg.fon.silab.dto.template.TemplateDetailsDto;
import rs.ac.bg.fon.silab.dto.template.TemplateGetDto;
import rs.ac.bg.fon.silab.dto.template.TemplateSaveDto;
import rs.ac.bg.fon.silab.entity.Template;
import rs.ac.bg.fon.silab.entity.TemplateState;
import rs.ac.bg.fon.silab.exception.EntityNotFoundException;
import rs.ac.bg.fon.silab.exception.WrongQueryParamsException;
import rs.ac.bg.fon.silab.mapper.TemplateMapper;
import rs.ac.bg.fon.silab.service.TemplateService;
import rs.ac.bg.fon.silab.validator.DtoValidator;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private TemplateDao templateDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private TemplateMapper templateMapper;

    @Autowired
    private DtoValidator<TemplateSaveDto> dtoValidator;

    @Transactional
    @Override
    public void insert(TemplateSaveDto templateSaveDto) {
        dtoValidator.validate(templateSaveDto);
        templateDao.insert(templateMapper.toTemplate(templateSaveDto));
    }

    @Transactional
    @Override
    public void update(TemplateSaveDto templateSaveDto, Integer id) {
        Template template = templateDao.getById(id);

        if (template != null) {
            dtoValidator.validate(templateSaveDto, id);

            template.setName(templateSaveDto.getName());
            template.setDescription(templateSaveDto.getDescription());
            template.setCompany(companyDao.getById(templateSaveDto.getCompanyId()));
            template.setState(TemplateState.values()[templateSaveDto.getState()]);
            template.setTemplateItems(templateMapper.toTemplateItems(templateSaveDto.getTemplateItems()));
        } else {
            throw new EntityNotFoundException("template.not.found");
        }
    }

    @Transactional
    @Override
    public void complete(Integer id) {
        Template template = templateDao.getById(id);

        if (template != null) {
            template.setState(TemplateState.COMPLETED);
        } else {
            throw new EntityNotFoundException("template.not.found");
        }
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Template template = templateDao.getById(id);

        if (template != null) {
            templateDao.delete(template);
        } else {
            throw new EntityNotFoundException("template.not.found");
        }
    }

    @Transactional
    @Override
    public TemplateDetailsDto getById(Integer id) {
        Template template = templateDao.getById(id);

        if (template != null) {
            return templateMapper.toTemplateDetailsDto(template);
        }

        throw new EntityNotFoundException("template.not.found");
    }

    @Transactional
    @Override
    public List<TemplateGetDto> getAll() {
        return templateDao.getAll().stream()
                .map(template -> templateMapper.toTemplateGetDto(template))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<TemplateGetDto> getByState(Integer state) {
        if (TemplateState.values().length > state) {
            return templateDao.getByState(TemplateState.values()[state]).stream()
                    .map(template -> templateMapper.toTemplateGetDto(template))
                    .collect(Collectors.toList());
        }

        throw new WrongQueryParamsException("template.query.param.state.invalid");
    }

}
