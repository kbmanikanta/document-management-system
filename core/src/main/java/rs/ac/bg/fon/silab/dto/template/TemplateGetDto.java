package rs.ac.bg.fon.silab.dto.template;

import rs.ac.bg.fon.silab.dto.company.CompanyGetDto;

public class TemplateGetDto extends TemplateDto {

    private Integer id;
    private CompanyGetDto company;

    public TemplateGetDto() {}

    public TemplateGetDto(String name, String description, Integer state, Integer id, CompanyGetDto company) {
        super(name, description, state);
        this.id = id;
        this.company = company;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CompanyGetDto getCompany() {
        return company;
    }

    public void setCompany(CompanyGetDto company) {
        this.company = company;
    }

}
