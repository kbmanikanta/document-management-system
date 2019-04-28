package rs.ac.bg.fon.silab.mock;

import rs.ac.bg.fon.silab.dto.company.CompanySaveDto;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;

public class ConstraintViolationMock implements ConstraintViolation<CompanySaveDto> {

    private PropertyPathMock propertyPath;
    private String message;

    public ConstraintViolationMock(PropertyPathMock propertyPath, String message) {
        this.propertyPath = propertyPath;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getMessageTemplate() {
        return null;
    }

    @Override
    public CompanySaveDto getRootBean() {
        return null;
    }

    @Override
    public Class<CompanySaveDto> getRootBeanClass() {
        return null;
    }

    @Override
    public Object getLeafBean() {
        return null;
    }

    @Override
    public Object[] getExecutableParameters() {
        return new Object[0];
    }

    @Override
    public Object getExecutableReturnValue() {
        return null;
    }

    @Override
    public Path getPropertyPath() {
        return propertyPath;
    }

    @Override
    public Object getInvalidValue() {
        return null;
    }

    @Override
    public ConstraintDescriptor<?> getConstraintDescriptor() {
        return null;
    }

    @Override
    public <U> U unwrap(Class<U> aClass) {
        return null;
    }

}
