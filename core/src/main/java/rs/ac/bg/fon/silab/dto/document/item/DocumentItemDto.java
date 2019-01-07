package rs.ac.bg.fon.silab.dto.document.item;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import javax.validation.constraints.Pattern;

@JsonInclude(Include.NON_NULL)
public class DocumentItemDto {

    private Integer integerValue;

    private Double doubleValue;

    private String stringValue;

    private Boolean booleanValue;

    @Pattern(regexp = "(^\\d{2}/\\d{2}/\\d{4}$)|(^$)", message = "{document.item.date.value.pattern}")
    private String dateValue;

    public DocumentItemDto() {}

    public DocumentItemDto(Integer integerValue, Double doubleValue, String stringValue, Boolean booleanValue, String dateValue) {
        this.integerValue = integerValue;
        this.doubleValue = doubleValue;
        this.stringValue = stringValue;
        this.booleanValue = booleanValue;
        this.dateValue = dateValue;
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

    public String getDateValue() {
        return dateValue;
    }

    public void setDateValue(String dateValue) {
        this.dateValue = dateValue;
    }

}
