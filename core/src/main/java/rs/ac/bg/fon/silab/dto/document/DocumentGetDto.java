package rs.ac.bg.fon.silab.dto.document;

import rs.ac.bg.fon.silab.dto.template.TemplateDto;

public class DocumentGetDto extends DocumentDto {

    private Integer id;
    private TemplateDto template;

    public DocumentGetDto() {}

    public DocumentGetDto(String name, Integer id, TemplateDto template) {
        super(name);
        this.id = id;
        this.template = template;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TemplateDto getTemplate() {
        return template;
    }

    public void setTemplate(TemplateDto template) {
        this.template = template;
    }

}
