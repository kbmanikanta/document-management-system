import { Component, OnDestroy, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { TemplateModel } from '../../../../models/template.model';
import { Observable, Subscription } from 'rxjs';
import { CompanyModel } from '../../../../models/company.model';
import { CompaniesDataService } from '../../../../services/data/companies-data.service';
import { NotificationService } from '../../../../services/helpers/notification.service';
import { ActivatedRoute, Router } from '@angular/router';
import { map } from 'rxjs/operators';
import { CompanyEditService } from '../company-edit.service';
import { FormValidationService } from '../../../../services/helpers/form-validation.service';

@Component({
  selector: 'app-company-edit-form',
  templateUrl: './company-edit-form.component.html',
  styleUrls: ['./company-edit-form.component.css']
})
export class CompanyEditFormComponent implements OnInit, OnDestroy {

  editMode = false;

  companyForm: FormGroup;
  company: CompanyModel = {};

  loading = false;

  private backSubscription: Subscription;
  private undoSubscription: Subscription;
  private saveSubscription: Subscription;

  constructor(private companyEditService: CompanyEditService,
              private companiesDataService: CompaniesDataService,
              public formValidationService: FormValidationService,
              private notificationService: NotificationService,
              private formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private router: Router) {}

  ngOnInit() {
    this.fetchDataAndInitForm();
    this.listenToEvents();
  }

  fetchDataAndInitForm() {
    this.route.params.subscribe((params) => {
      const id = params['id'];

      if (id) {
        this.editMode = true;
        this.companyEditService.editModeObserver.next();

        this.loading = true;

        this.companiesDataService.getById(id)
          .subscribe((company) => {
            this.loading = false;

            this.company = company;
            this.initForm();
          }, () => {
            this.notificationService.pushError('COMPANY_LOADING_ERROR');
            this.router.navigate(['../../'], { relativeTo: this.route });
          });
      } else {
        this.initForm();
      }
    });
  }

  listenToEvents() {
    this.backSubscription = this.companyEditService.backObserver
      .subscribe(() => {
        if (this.editMode) {
          this.router.navigate(['../../'], { relativeTo: this.route });
        } else {
          this.router.navigate(['../'], { relativeTo: this.route });
        }
      });


    this.undoSubscription = this.companyEditService.undoObserver
      .subscribe(() => {
        if (this.editMode) {
          this.companyForm.setValue({
            name: this.company.name,
            taxIdNumber: this.company.taxIdNumber,
          });
        } else {
          this.resetForm();
        }
      });

    this.saveSubscription = this.companyEditService.saveObserver
      .subscribe(() => {
        if (this.companyForm.valid) {
          this.save(this.companyForm.value);
        } else {
          this.formValidationService.markAllFieldsAsTouched(this.companyForm);
        }
      });
  }

  initForm() {
    this.companyForm = new FormGroup({
      name: new FormControl(this.company.name, [
        Validators.required,
        Validators.maxLength(100)
      ]),
      taxIdNumber: new FormControl(this.company.taxIdNumber, [
        Validators.required,
        Validators.minLength(9),
        Validators.maxLength(9),
        Validators.pattern(/^\d+$/),
      ], [
        this.taxIdNumberUniqueValidator
      ])
    });
  }

  resetForm() {
    this.companyForm.reset();
    this.companyForm.markAsPristine();
  }

  save(template: TemplateModel) {
    this.loading = true;

    this.companiesDataService.save(template, this.editMode ? this.company.id : null)
      .subscribe(() => {
        this.loading = false;
        this.notificationService.pushSuccess('COMPANY_SAVE_SUCCESS');

        if (!this.editMode) {
          this.resetForm();
        } else {
          this.router.navigate(['../../'], { relativeTo: this.route });
        }
      }, () => {
        this.loading = false;
        this.notificationService.pushError('COMPANY_SAVE_ERROR');
      });
  }

  taxIdNumberUniqueValidator = (control: AbstractControl): Observable<{ [key: string]: any }> => {
    return this.companiesDataService.getByTaxIdNumber(control.value)
      .pipe(map((companies) => {
        if (this.editMode) {
          return companies.length > 0 && companies[0].id !== this.company.id ?
            { taxIdNumberUnique: true } : null;
        }
        return companies.length > 0 ? { taxIdNumberUnique: true } : null;
      }));
  };

  ngOnDestroy() {
    this.backSubscription.unsubscribe();
    this.undoSubscription.unsubscribe();
    this.saveSubscription.unsubscribe();
  }

}
