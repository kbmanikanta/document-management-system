import { FormGroup } from '@angular/forms';

export const formValidationHelper = {
  isNotValid: (form: FormGroup, field: string, validator: string) => {
    return form.hasError(validator, [field]);
  },
  isNotValidRequired: (form: FormGroup, field: string) => {
    return formValidationHelper.isNotValid(form, field, 'required');
  },
  isNotValidMinLength: (form: FormGroup, field: string) => {
    return formValidationHelper.isNotValid(form, field, 'minlength');
  },
  isNotValidMaxLength: (form: FormGroup, field: string) => {
    return formValidationHelper.isNotValid(form, field, 'maxlength');
  },
  isNotValidLength: (form: FormGroup, field: string) => {
    return formValidationHelper.isNotValidMinLength(form, field) || formValidationHelper.isNotValidMaxLength(form, field);
  },
  isNotValidPattern: (form: FormGroup, field: string) => {
    return formValidationHelper.isNotValid(form, field, 'pattern');
  },
  isNotValidUnique: (form: FormGroup, field: string) => {
    return formValidationHelper.isNotValid(form, field, 'unique');
  },
  markAllFieldsAsTouched: (form: FormGroup) => {
    (<any> Object).values(form.controls).forEach(control => {
      control.markAsTouched();

      if (control.controls) {
        formValidationHelper.markAllFieldsAsTouched(control);
      }
    });
  }
};
