package rs.ac.bg.fon.silab.dto.template;

import rs.ac.bg.fon.silab.dto.company.CompanyGetDto;
import rs.ac.bg.fon.silab.dto.template.item.TemplateItemDto;

import java.util.List;

public class TemplateDetailsDto extends TemplateGetDto {

    private List<TemplateItemDto> templateItems;

    public TemplateDetailsDto() {}

    public TemplateDetailsDto(String name, String description, Integer id, CompanyGetDto company, List<TemplateItemDto> templateItems) {
        super(name, description, id, company);
        this.templateItems = templateItems;
    }

    public List<TemplateItemDto> getTemplateItems() {
        return templateItems;
    }

    public void setTemplateItems(List<TemplateItemDto> templateItems) {
        this.templateItems = templateItems;
    }

}
