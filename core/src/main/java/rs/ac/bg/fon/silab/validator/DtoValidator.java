package rs.ac.bg.fon.silab.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import rs.ac.bg.fon.silab.exception.ValidationException;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;

public abstract class DtoValidator<Dto> {

    @Autowired
    private Validator validator;

    @Autowired
    private MessageSource messageSource;

    public void validate(Dto dto) {
        beanValidation(dto);
        customValidation(dto);
    }

    public void validate(Dto dto, Integer id) {
        beanValidation(dto);
        customValidation(dto, id);
    }

    private void beanValidation(Dto dto) {
        Set<ConstraintViolation<Dto>> constraintViolations = validator.validate(dto);

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

    protected Map<String, List<String>> getError(String messageKey, Object[] messageParams, String errorKey) {
        String errorMessage = messageSource.getMessage(messageKey,
                messageParams, Locale.getDefault());

        return new HashMap<String, List<String>>() {{
            put(errorKey, Collections.singletonList(errorMessage));
        }};
    }

    protected Map<String, List<String>> getError(String messageKey, String errorKey) {
        return getError(messageKey, null, errorKey);
    }

    protected abstract void customValidation(Dto dto);

    protected abstract void customValidation(Dto dto, Integer id);

}
