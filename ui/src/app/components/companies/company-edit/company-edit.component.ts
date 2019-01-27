import { Component, OnDestroy, OnInit } from '@angular/core';
import { CompaniesService } from '../../../services/data/companies.service';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { CompanyModel } from '../../../models/company.model';
import { ActivatedRoute, Router } from '@angular/router';
import { NotificationService } from '../../../services/notification.service';
import { Observable, Subscription } from 'rxjs';
import { map } from 'rxjs/operators';
import { formValidationHelper } from '../../../helpers/form-validation.helper';

@Component({
  selector: 'app-company-edit',
  templateUrl: './company-edit.component.html',
  styleUrls: ['./company-edit.component.css']
})
export class CompanyEditComponent implements OnInit, OnDestroy {

  companyDataSubscription: Subscription;
  companiesProgressSubscription: Subscription;
  savedCompanySubscription: Subscription;
  companyForm: FormGroup;
  company: CompanyModel;
  editMode = false;
  formValidationHelper = formValidationHelper;
  getLoading = false;
  saveLoading = false;
  progress: number;

  constructor(private companiesService: CompaniesService,
              private notificationService: NotificationService,
              private route: ActivatedRoute,
              private router: Router) {}

  ngOnInit() {
    this.route.params
      .subscribe((params) => {
        const id = params['id'];
        if (id) {
          this.progress = 0;
          this.getLoading = true;
          this.editMode = true;

          setTimeout(() => {
            this.companiesService.getById(id);
          }, 1000);
        } else {
          this.editMode = false;
          this.initForm();
        }
      });

    this.companiesProgressSubscription = this.companiesService.getCompaniesProgressListener()
      .subscribe((progress: number) => {
        this.progress = progress;
      });

    this.companyDataSubscription = this.companiesService.getCompanyDataListener()
      .subscribe((company: CompanyModel) => {
        if (company) {
          this.company = company;
          this.initForm(company);
        } else {
          this.notificationService.pushError('COMPANY_LOADING_ERROR');
          this.router.navigate(['../../'], { relativeTo: this.route });
        }
      });

    this.savedCompanySubscription = this.companiesService.getSavedCompanyListener()
      .subscribe((isSaved) => {
        this.saveLoading = false;

        if (isSaved) {
          this.notificationService.pushSuccess('COMPANY_SAVE_SUCCESS');

          if (!this.editMode) {
            this.companyForm.reset();
            this.companyForm.markAsPristine();
          }
        } else {
          this.notificationService.pushError('COMPANY_SAVE_ERROR');
        }
      });
  }

  initForm(company: CompanyModel = {}) {
    this.companyForm = new FormGroup({
      name: new FormControl(company.name, [
        Validators.required,
        Validators.maxLength(100)
      ]),
      taxIdNumber: new FormControl(company.taxIdNumber, [
        Validators.required,
        Validators.minLength(9),
        Validators.maxLength(9),
        Validators.pattern(/^\d+$/),
      ], [
        this.taxIdNumberUniqueValidator
      ])
    });
  }

  onReset() {
    if (this.editMode) {
      this.companyForm.setValue({
        name: this.company.name,
        taxIdNumber: this.company.taxIdNumber
      });
    } else {
      this.companyForm.reset();
      this.companyForm.markAsPristine();
    }
  }

  onSave() {
    if (this.companyForm.valid) {
      this.saveLoading = true;

      if (this.editMode) {
        setTimeout(() => {
          this.companiesService.update(this.companyForm.value, this.company.id);
        }, 1000);
      } else {
        setTimeout(() => {
          this.companiesService.insert(this.companyForm.value);
        }, 1000);
      }
    } else {
      this.formValidationHelper.markAllFieldsAsTouched(this.companyForm);
    }

  }

  taxIdNumberUniqueValidator = (control: AbstractControl): Observable<{ [key: string]: any }> => {
    return this.companiesService.getByTaxIdNumber(control.value)
      .pipe(map((companies) => {
        if (this.editMode) {
          return companies.length > 0 && companies[0].id !== this.company.id ?
            { taxIdNumberUnique: true } : null;
        }
        return companies.length > 0 ? { taxIdNumberUnique: true } : null;
      }));
  };

  ngOnDestroy() {
    this.companiesProgressSubscription.unsubscribe();
    this.companyDataSubscription.unsubscribe();
    this.savedCompanySubscription.unsubscribe();
  }

}
