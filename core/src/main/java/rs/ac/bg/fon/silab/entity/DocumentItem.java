package rs.ac.bg.fon.silab.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "document_item")
public class DocumentItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "integer_value")
    private Integer integerValue;

    @Column(name = "double_value")
    private Double doubleValue;

    @Column(name = "string_value")
    private String stringValue;

    @Column(name = "boolean_value")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean booleanValue;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_value")
    private Date dateValue;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "template_item_id")
    private TemplateItem templateItem;

    public DocumentItem() {}

    public DocumentItem(Integer integerValue, Double doubleValue, String stringValue, Boolean booleanValue, Date dateValue, TemplateItem templateItem) {
        this.integerValue = integerValue;
        this.doubleValue = doubleValue;
        this.stringValue = stringValue;
        this.booleanValue = booleanValue;
        this.dateValue = dateValue;
        this.templateItem = templateItem;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIntegerValue() {
        return integerValue;
    }

    public void setIntegerValue(Integer integerValue) {
        this.integerValue = integerValue;
    }

    public Double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(Double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public Boolean getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public Date getDateValue() {
        return dateValue;
    }

    public void setDateValue(Date dateValue) {
        this.dateValue = dateValue;
    }

    public TemplateItem getTemplateItem() {
        return templateItem;
    }

    public void setTemplateItem(TemplateItem templateItem) {
        this.templateItem = templateItem;
    }

}
