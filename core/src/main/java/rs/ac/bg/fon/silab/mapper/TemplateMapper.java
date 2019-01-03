package rs.ac.bg.fon.silab.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.bg.fon.silab.dao.CompanyDao;
import rs.ac.bg.fon.silab.dto.template.TemplateDetailsDto;
import rs.ac.bg.fon.silab.dto.template.TemplateGetDto;
import rs.ac.bg.fon.silab.dto.template.TemplateSaveDto;
import rs.ac.bg.fon.silab.dto.template.item.TemplateItemDto;
import rs.ac.bg.fon.silab.entity.Template;
import rs.ac.bg.fon.silab.entity.TemplateItem;
import rs.ac.bg.fon.silab.entity.TemplateItemType;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TemplateMapper {

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private CompanyDao companyDao;

    public TemplateGetDto toTemplateGetDto(Template template) {
        return new TemplateGetDto(
                template.getName(),
                template.getDescription(),
                template.getId(),
                companyMapper.toCompanyGetDto(template.getCompany()));
    }

    public TemplateDetailsDto toTemplateDetailsDto(Template template) {
        return new TemplateDetailsDto(
                template.getName(),
                template.getDescription(),
                template.getId(),
                companyMapper.toCompanyGetDto(template.getCompany()),
                toTemplateItemDtos(template.getTemplateItems()));
    }

    public Template toTemplate(TemplateSaveDto templateSaveDto) {
        return new Template(
                templateSaveDto.getName(),
                templateSaveDto.getDescription(),
                companyDao.getById(templateSaveDto.getCompanyId()),
                toTemplateItems(templateSaveDto.getTemplateItems()));
    }

    public List<TemplateItemDto> toTemplateItemDtos(List<TemplateItem> templateItems) {
        return templateItems.stream()
                .map(templateItem -> new TemplateItemDto(
                        templateItem.getLabel(),
                        templateItem.getType().ordinal(),
                        templateItem.getMandatory(),
                        templateItem.getMultiple()
                )).collect(Collectors.toList());
    }

    public List<TemplateItem> toTemplateItems(List<TemplateItemDto> templateItemDtos) {
        return templateItemDtos.stream()
                .map(templateItemDto -> new TemplateItem(
                        templateItemDto.getLabel(),
                        TemplateItemType.values()[templateItemDto.getType()],
                        templateItemDto.getMandatory(),
                        templateItemDto.getMultiple()
                )).collect(Collectors.toList());
    }

}
