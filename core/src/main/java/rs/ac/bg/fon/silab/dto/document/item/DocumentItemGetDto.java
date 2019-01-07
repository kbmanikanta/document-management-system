package rs.ac.bg.fon.silab.dto.document.item;

import rs.ac.bg.fon.silab.dto.template.item.TemplateItemDto;

public class DocumentItemGetDto extends DocumentItemDto {

    private TemplateItemDto templateItem;

    public DocumentItemGetDto() {}

    public DocumentItemGetDto(Integer integerValue, Double doubleValue, String stringValue, Boolean booleanValue, String dateValue, TemplateItemDto templateItem) {
        super(integerValue, doubleValue, stringValue, booleanValue, dateValue);
        this.templateItem = templateItem;
    }

    public TemplateItemDto getTemplateItem() {
        return templateItem;
    }

    public void setTemplateItem(TemplateItemDto templateItem) {
        this.templateItem = templateItem;
    }

}
