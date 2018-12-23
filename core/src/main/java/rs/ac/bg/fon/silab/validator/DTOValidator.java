package rs.ac.bg.fon.silab.validator;

import org.springframework.beans.factory.annotation.Autowired;
import rs.ac.bg.fon.silab.exception.ValidationException;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;

public abstract class DTOValidator<DTO> {

    @Autowired
    private Validator validator;

    public void validate(DTO dto) {
        beanValidation(dto);
        customValidation(dto);
    }

    public void validate(DTO dto, Integer id) {
        beanValidation(dto);
        customValidation(dto, id);
    }

    private void beanValidation(DTO dto) {
        Set<ConstraintViolation<DTO>> constraintViolations = validator.validate(dto);

        if (!constraintViolations.isEmpty()) {
            Map<String, List<String>> errors = constraintViolations.stream()
                    .reduce(new HashMap<>(), (acc, el) -> {
                        List<String> errorMessages = acc.computeIfAbsent(el.getPropertyPath().toString(),
                                k -> new ArrayList<>());
                        errorMessages.add(el.getMessage());

                        return acc;
                    }, (__, ___) -> __);

            throw new ValidationException(errors);
        }
    }

    abstract void customValidation(DTO dto);

    abstract void customValidation(DTO dto, Integer id);

}
