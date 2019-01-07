package rs.ac.bg.fon.silab.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "template")
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "state")
    private TemplateState state;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "template_id")
    private List<TemplateItem> templateItems;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "template")
    private List<Document> documents;

    public Template() {}

    public Template(String name, String description, TemplateState state, Company company, List<TemplateItem> templateItems) {
        this.name = name;
        this.description = description;
        this.state = state;
        this.company = company;
        this.templateItems = templateItems;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public TemplateState getState() {
        return state;
    }

    public void setState(TemplateState state) {
        this.state = state;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<TemplateItem> getTemplateItems() {
        return templateItems;
    }

    public void setTemplateItems(List<TemplateItem> templateItems) {
        if (this.templateItems == null) {
            this.templateItems = new ArrayList<>();
        }

        this.templateItems.clear();
        this.templateItems.addAll(templateItems);
    }

}
