package rs.ac.bg.fon.silab.dto.document;

import rs.ac.bg.fon.silab.dto.document.item.DocumentItemSaveDto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class DocumentSaveDto extends DocumentDto {

    @NotNull(message = "{document.template.id.not.empty}")
    private Integer templateId;

    @NotEmpty(message = "{document.items.not.empty}")
    @Valid
    private List<DocumentItemSaveDto> documentItems;

    public DocumentSaveDto() {}

    public DocumentSaveDto(String name, Integer templateId, List<DocumentItemSaveDto> documentItems) {
        super(name);
        this.templateId = templateId;
        this.documentItems = documentItems;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public List<DocumentItemSaveDto> getDocumentItems() {
        return documentItems;
    }

    public void setDocumentItems(List<DocumentItemSaveDto> documentItems) {
        this.documentItems = documentItems;
    }

}
