package rs.ac.bg.fon.silab.dto.document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class DocumentDto {

    @NotEmpty(message = "{document.name.not.empty}")
    @Size(max = 100, message = "{document.name.size}")
    private String name;

    public DocumentDto() {}

    public DocumentDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
