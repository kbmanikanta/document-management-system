package rs.ac.bg.fon.silab.dto.document;

import rs.ac.bg.fon.silab.dto.document.item.DocumentItemGetDto;
import rs.ac.bg.fon.silab.dto.template.TemplateDto;

import java.util.List;

public class DocumentDetailsDto extends DocumentGetDto {

    private List<DocumentItemGetDto> documentItems;

    public DocumentDetailsDto() {}

    public DocumentDetailsDto(String name, Integer id, TemplateDto template, List<DocumentItemGetDto> documentItems) {
        super(name, id, template);
        this.documentItems = documentItems;
    }

    public List<DocumentItemGetDto> getDocumentItems() {
        return documentItems;
    }

    public void setDocumentItems(List<DocumentItemGetDto> documentItems) {
        this.documentItems = documentItems;
    }

}
