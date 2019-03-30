package rs.ac.bg.fon.silab.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import rs.ac.bg.fon.silab.dao.CompanyDao;
import rs.ac.bg.fon.silab.dto.company.CompanySaveDto;
import rs.ac.bg.fon.silab.entity.Company;
import rs.ac.bg.fon.silab.exception.ValidationException;
import rs.ac.bg.fon.silab.mock.ConstraintViolationMock;
import rs.ac.bg.fon.silab.mock.PropertyPathMock;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CompanySaveDtoValidatorTest {

    @Mock
    private Validator validator;

    @Mock
    private MessageSource messageSource;

    @Mock
    private CompanyDao companyDao;

    @InjectMocks
    private CompanySaveDtoValidator companySaveDtoValidator;

    @Test
    void testValidateForInsertWhenCompanyIsValid() {
        CompanySaveDto companyForInsert = new CompanySaveDto("SAP", "123123123");

        doReturn(new HashSet<ConstraintViolation<CompanySaveDto>>()).when(validator).validate(companyForInsert);
        doReturn(new ArrayList<Company>()).when(companyDao).getByTaxIdNumber(companyForInsert.getTaxIdNumber());


        assertDoesNotThrow(() -> companySaveDtoValidator.validate(companyForInsert));
        verify(validator).validate(companyForInsert);
        verify(companyDao).getByTaxIdNumber(companyForInsert.getTaxIdNumber());
    }

    @Test
    void testValidateForInsertWhenCompanyDoesNotPassBeanValidation() {
        CompanySaveDto companyForInsert = new CompanySaveDto();

        String nameErrorKey = "name";
        String taxIdNumberErrorKey = "taxIdNumber";

        List<String> nameErrorMessages = Arrays.asList(
                "Name is required.",
                "Name must be less than or equal to 100 characters."
        );
        List<String> taxIdNumberErrorMessages = Collections.singletonList("Tax identification number is required.");

        doReturn(new HashSet<ConstraintViolation<CompanySaveDto>>() {{
            add(new ConstraintViolationMock(new PropertyPathMock(nameErrorKey), nameErrorMessages.get(0)));
            add(new ConstraintViolationMock(new PropertyPathMock(nameErrorKey), nameErrorMessages.get(1)));
            add(new ConstraintViolationMock(new PropertyPathMock(taxIdNumberErrorKey), taxIdNumberErrorMessages.get(0)));
        }}).when(validator).validate(companyForInsert);

        ValidationException validationException = assertThrows(ValidationException.class,
                () -> companySaveDtoValidator.validate(companyForInsert));
        assertAll(
                () -> assertEquals(validationException.getErrors().get(nameErrorKey).size(), nameErrorMessages.size()),
                () -> assertTrue(validationException.getErrors().get(nameErrorKey).containsAll(nameErrorMessages)),
                () -> assertEquals(validationException.getErrors().get(taxIdNumberErrorKey).size(),
                        taxIdNumberErrorMessages.size()),
                () -> assertTrue(validationException.getErrors().get(taxIdNumberErrorKey).containsAll(taxIdNumberErrorMessages))
        );
        verify(validator).validate(companyForInsert);
        verify(companyDao, never()).getByTaxIdNumber(companyForInsert.getTaxIdNumber());
    }

    @Test
    void testValidateForInsertWhenCompanyWithPassedTaxIdNumberAlreadyExistsInDB() {
        CompanySaveDto companyForInsert = new CompanySaveDto("SAP", "123123123");
        Company companyFromDB = new Company("CallidusCloud", "123123123");

        String taxIdNumberErrorKey = "taxIdNumber";
        String taxIdNumberErrorMessageKey = "company.tax.id.number.unique";
        String taxIdNumberErrorMessage = "Company with tax identification number 123123123 already exists.";

        doReturn(new HashSet<ConstraintViolation<CompanySaveDto>>()).when(validator).validate(companyForInsert);
        doReturn(Collections.singletonList(companyFromDB))
                .when(companyDao).getByTaxIdNumber(companyForInsert.getTaxIdNumber());
        doReturn(taxIdNumberErrorMessage).when(messageSource).getMessage(taxIdNumberErrorMessageKey,
                new Object[] { companyForInsert.getTaxIdNumber() }, Locale.getDefault());

        ValidationException validationException = assertThrows(ValidationException.class,
                () -> companySaveDtoValidator.validate(companyForInsert),
                "Validate should throw ValidationException");
        assertArrayEquals(validationException.getErrors().get(taxIdNumberErrorKey).toArray(),
                new String[] { taxIdNumberErrorMessage });
        verify(validator).validate(companyForInsert);
        verify(companyDao).getByTaxIdNumber(companyForInsert.getTaxIdNumber());
    }

    @Test
    void testValidateForUpdateWhenCompanyIsValid() {
        CompanySaveDto companyForUpdate = new CompanySaveDto("SAP", "123123123");
        Integer companyForUpdateId = 1;

        doReturn(new HashSet<ConstraintViolation<CompanySaveDto>>()).when(validator).validate(companyForUpdate);
        doReturn(new ArrayList<Company>()).when(companyDao).getByTaxIdNumber(companyForUpdate.getTaxIdNumber());


        assertDoesNotThrow(() -> companySaveDtoValidator.validate(companyForUpdate, companyForUpdateId));
        verify(validator).validate(companyForUpdate);
        verify(companyDao).getByTaxIdNumber(companyForUpdate.getTaxIdNumber());
    }

    @Test
    void testValidateForUpdateWhenCompanyDoesNotPassBeanValidation() {
        CompanySaveDto companyForUpdate = new CompanySaveDto();
        Integer companyForUpdateId = 1;

        String nameErrorKey = "name";
        String taxIdNumberErrorKey = "taxIdNumber";

        List<String> nameErrorMessages = Arrays.asList(
                "Name is required.",
                "Name must be less than or equal to 100 characters."
        );
        List<String> taxIdNumberErrorMessages = Collections.singletonList("Tax identification number is required.");

        doReturn(new HashSet<ConstraintViolation<CompanySaveDto>>() {{
            add(new ConstraintViolationMock(new PropertyPathMock(nameErrorKey), nameErrorMessages.get(0)));
            add(new ConstraintViolationMock(new PropertyPathMock(nameErrorKey), nameErrorMessages.get(1)));
            add(new ConstraintViolationMock(new PropertyPathMock(taxIdNumberErrorKey), taxIdNumberErrorMessages.get(0)));
        }}).when(validator).validate(companyForUpdate);

        ValidationException validationException = assertThrows(ValidationException.class,
                () -> companySaveDtoValidator.validate(companyForUpdate, companyForUpdateId));
        assertAll(
                () -> assertEquals(validationException.getErrors().get(nameErrorKey).size(), nameErrorMessages.size()),
                () -> assertTrue(validationException.getErrors().get(nameErrorKey).containsAll(nameErrorMessages)),
                () -> assertEquals(validationException.getErrors().get(taxIdNumberErrorKey).size(),
                        taxIdNumberErrorMessages.size()),
                () -> assertTrue(validationException.getErrors().get(taxIdNumberErrorKey).containsAll(taxIdNumberErrorMessages))
        );
        verify(validator).validate(companyForUpdate);
        verify(companyDao, never()).getByTaxIdNumber(companyForUpdate.getTaxIdNumber());
    }

    @Test
    void testValidateForUpdateWhenCompanyWithPassedTaxIdNumberAlreadyExistsInDB() {
        CompanySaveDto companyForUpdate = new CompanySaveDto("SAP", "123123123");
        Integer companyForUpdateId = 1;

        Company companyFromDB = new Company("CallidusCloud", "123123123");
        companyFromDB.setId(2);

        String taxIdNumberErrorKey = "taxIdNumber";
        String taxIdNumberErrorMessageKey = "company.tax.id.number.unique";
        String taxIdNumberErrorMessage = "Company with tax identification number 123123123 already exists.";

        doReturn(new HashSet<ConstraintViolation<CompanySaveDto>>()).when(validator).validate(companyForUpdate);
        doReturn(Collections.singletonList(companyFromDB)).when(companyDao).getByTaxIdNumber(companyForUpdate.getTaxIdNumber());
        doReturn(taxIdNumberErrorMessage).when(messageSource).getMessage(taxIdNumberErrorMessageKey,
                new Object[] { companyForUpdate.getTaxIdNumber() }, Locale.getDefault());

        ValidationException validationException = assertThrows(ValidationException.class,
                () -> companySaveDtoValidator.validate(companyForUpdate, companyForUpdateId));
        assertArrayEquals(validationException.getErrors().get(taxIdNumberErrorKey).toArray(),
                new String[] { taxIdNumberErrorMessage });
        verify(validator).validate(companyForUpdate);
        verify(companyDao).getByTaxIdNumber(companyForUpdate.getTaxIdNumber());
    }

    @Test
    void testValidateForUpdateWhenTaxIdNumberHasNotBeenChanged() {
        CompanySaveDto companyForUpdate = new CompanySaveDto("SAP", "123123123");
        Integer companyForUpdateId = 1;

        Company companyFromDB = new Company("CallidusCloud", "123123123");
        companyFromDB.setId(companyForUpdateId);

        doReturn(new HashSet<ConstraintViolation<CompanySaveDto>>()).when(validator).validate(companyForUpdate);
        doReturn(Collections.singletonList(companyFromDB)).when(companyDao).getByTaxIdNumber(companyForUpdate.getTaxIdNumber());

        assertDoesNotThrow(() -> companySaveDtoValidator.validate(companyForUpdate, companyForUpdateId));
        verify(validator).validate(companyForUpdate);
        verify(companyDao).getByTaxIdNumber(companyForUpdate.getTaxIdNumber());
    }

}
