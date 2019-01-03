package rs.ac.bg.fon.silab.dto.template;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class TemplateDto {

    @NotEmpty(message = "{template.name.not.empty}")
    @Size(max = 100, message = "{template.name.size}")
    private String name;

    @NotEmpty(message = "{template.description.not.empty}")
    @Size(max = 200, message = "{template.description.size}")
    private String description;

    public TemplateDto() {}

    public TemplateDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
