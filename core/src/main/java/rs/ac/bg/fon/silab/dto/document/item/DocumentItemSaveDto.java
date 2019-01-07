package rs.ac.bg.fon.silab.dto.document.item;

import javax.validation.constraints.NotNull;

public class DocumentItemSaveDto extends DocumentItemDto {

    @NotNull(message = "{document.item.template.item.id.not.empty}")
    private Integer templateItemId;

    public DocumentItemSaveDto() {}

    public DocumentItemSaveDto(Integer integerValue, Double doubleValue, String stringValue, Boolean booleanValue, String dateValue, Integer templateItemId) {
        super(integerValue, doubleValue, stringValue, booleanValue, dateValue);
        this.templateItemId = templateItemId;
    }

    public Integer getTemplateItemId() {
        return templateItemId;
    }

    public void setTemplateItemId(Integer templateItemId) {
        this.templateItemId = templateItemId;
    }

}
