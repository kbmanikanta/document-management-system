package rs.ac.bg.fon.silab.dto.company;

public class CompanyGetDto extends CompanySaveDto {

    private Integer id;

    public CompanyGetDto() {}

    public CompanyGetDto(Integer id, String name, String taxIdNumber) {
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
