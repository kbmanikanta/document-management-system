package rs.ac.bg.fon.silab.dto.template.item;

import javax.validation.constraints.*;

public class TemplateItemDto {

    private Integer id;

    @NotEmpty(message = "{template.item.label.not.empty}")
    @Size(max = 50, message = "{template.item.label.size}")
    private String label;

    @NotNull(message = "{template.item.type.not.empty}")
    @Min(value = 0, message = "{template.item.type.size}")
    @Max(value = 4, message = "{template.item.type.size}")
    private Integer type;

    @NotNull(message = "{template.item.mandatory.not.empty}")
    private Boolean mandatory;

    @NotNull(message = "{template.item.multiple.not.empty}")
    private Boolean multiple;

    public TemplateItemDto() {}

    public TemplateItemDto(Integer id, String label, Integer type, Boolean mandatory, Boolean multiple) {
        this.id = id;
        this.label = label;
        this.type = type;
        this.mandatory = mandatory;
        this.multiple = multiple;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getMandatory() {
        return mandatory;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    public Boolean getMultiple() {
        return multiple;
    }

    public void setMultiple(Boolean multiple) {
        this.multiple = multiple;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TemplateItemDto that = (TemplateItemDto) o;

        return label != null ? label.equals(that.label) : that.label == null;
    }

    @Override
    public int hashCode() {
        return label != null ? label.hashCode() : 0;
    }

}
