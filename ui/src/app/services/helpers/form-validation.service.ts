import { FormGroup } from '@angular/forms';

export class FormValidationService {

  constructor() {}

  isNotValid(form: FormGroup, field: string, validator: string) {
    return form.hasError(validator, [field]);
  }

  isNotValidRequired(form: FormGroup, field: string) {
    return this.isNotValid(form, field, 'required');
  }

  isNotValidMinLength(form: FormGroup, field: string) {
    return this.isNotValid(form, field, 'minlength');
  }

  isNotValidMaxLength(form: FormGroup, field: string) {
    return this.isNotValid(form, field, 'maxlength');
  }

  isNotValidLength(form: FormGroup, field: string) {
    return this.isNotValidMinLength(form, field) || this.isNotValidMaxLength(form, field);
  }

  isNotValidPattern(form: FormGroup, field: string) {
    return this.isNotValid(form, field, 'pattern');
  }

  isNotValidUnique(form: FormGroup, field: string) {
    return this.isNotValid(form, field, 'unique');
  }

  markAllFieldsAsTouched(form: FormGroup) {
    (<any> Object).values(form.controls).forEach(control => {
      control.markAsTouched();

      if (control.controls) {
        this.markAllFieldsAsTouched(control);
      }
    });
  }

}
