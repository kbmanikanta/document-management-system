package rs.ac.bg.fon.silab.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "template_item")
public class TemplateItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "label")
    private String label;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    private TemplateItemType type;

    @Column(name = "mandatory")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean mandatory;

    @Column(name = "multiple")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean multiple;

    public TemplateItem() {}

    public TemplateItem(String label, TemplateItemType type, Boolean mandatory, Boolean multiple) {
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

    public TemplateItemType getType() {
        return type;
    }

    public void setType(TemplateItemType type) {
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

}
