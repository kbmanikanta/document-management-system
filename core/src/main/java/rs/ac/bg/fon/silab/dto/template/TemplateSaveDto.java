package rs.ac.bg.fon.silab.dto.template;

import rs.ac.bg.fon.silab.dto.template.item.TemplateItemDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public class TemplateSaveDto extends TemplateDto {

    @NotNull(message = "{template.company.id.not.empty}")
    private Integer companyId;

    @NotNull(message = "{template.items.not.empty}")
    @Valid
    private List<TemplateItemDto> templateItems;

    public TemplateSaveDto() {}

    public TemplateSaveDto(String name, String description, Integer companyId, List<TemplateItemDto> templateItems) {
        super(name, description);
        this.companyId = companyId;
        this.templateItems = templateItems;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public List<TemplateItemDto> getTemplateItems() {
        return templateItems;
    }

    public void setTemplateItems(List<TemplateItemDto> templateItems) {
        this.templateItems = templateItems;
    }

}
