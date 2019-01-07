package rs.ac.bg.fon.silab.dto.template;

import javax.validation.constraints.*;

public class TemplateDto {

    @NotEmpty(message = "{template.name.not.empty}")
    @Size(max = 100, message = "{template.name.size}")
    private String name;

    @NotEmpty(message = "{template.description.not.empty}")
    @Size(max = 200, message = "{template.description.size}")
    private String description;

    @NotNull(message = "{template.state.not.empty}")
    @Min(value = 0, message = "{template.state.size}")
    @Max(value = 1, message = "{template.state.size}")
    private Integer state;

    public TemplateDto() {}

    public TemplateDto(String name, String description, Integer state) {
        this.name = name;
        this.description = description;
        this.state = state;
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

}
