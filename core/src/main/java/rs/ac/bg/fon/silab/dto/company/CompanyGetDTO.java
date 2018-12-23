package rs.ac.bg.fon.silab.dto.company;

public class CompanyGetDTO extends CompanySaveDTO {

    private Integer id;

    public CompanyGetDTO() {}

    public CompanyGetDTO(Integer id, String name, String taxIdNumber) {
        super(name, taxIdNumber);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
