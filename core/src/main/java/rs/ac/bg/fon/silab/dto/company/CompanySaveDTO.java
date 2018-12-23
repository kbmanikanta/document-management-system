package rs.ac.bg.fon.silab.dto.company;

import javax.validation.constraints.*;

public class CompanySaveDTO {

    @NotEmpty(message = "{company.name.not.empty}")
    @Size(max = 100, message = "{company.name.size}")
    private String name;

    @NotEmpty(message = "{company.tax.id.number.not.empty}")
    @Size(min = 9, max = 9, message = "{company.tax.id.number.size}")
    @Pattern(regexp = "^\\d+$", message = "{company.tax.id.number.pattern}")
    private String taxIdNumber;

    public CompanySaveDTO() {}

    public CompanySaveDTO(String name, String taxIdNumber) {
        this.name = name;
        this.taxIdNumber = taxIdNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaxIdNumber() {
        return taxIdNumber;
    }

    public void setTaxIdNumber(String taxIdNumber) {
        this.taxIdNumber = taxIdNumber;
    }

}
