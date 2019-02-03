import { Component, OnDestroy, OnInit } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TemplateModel } from '../../../../models/template.model';
import { Observable, Subscription, throwError } from 'rxjs';
import { TemplateItemModel } from '../../../../models/template-item.model';
import { TemplateState } from '../../../../models/template-state.enum';
import { CompanyModel } from '../../../../models/company.model';
import { CompaniesDataService } from '../../../../services/data/companies-data.service';
import { TemplateEditService } from '../template-edit.service';
import { ActivatedRoute, Router } from '@angular/router';
import { TemplatesDataService } from '../../../../services/data/templates-data.service';
import { NotificationService } from '../../../../services/helpers/notification.service';
import { catchError } from 'rxjs/operators';
import { RxwebValidators } from '@rxweb/reactive-form-validators';
import { TemplateEditFormService } from './template-edit-form.service';
import { FormValidationService } from '../../../../services/helpers/form-validation.service';

@Component({
  selector: 'app-template-edit-form',
  templateUrl: './template-edit-form.component.html',
  styleUrls: ['./template-edit-form.component.css']
})
export class TemplateEditFormComponent implements OnInit, OnDestroy {

  editMode = false;

  templateForm: FormGroup;
  template: TemplateModel = {};
  templateItemTypes;
  companies: Observable<CompanyModel[]>;

  loading = false;

  private backSubscription: Subscription;
  private undoSubscription: Subscription;
  private saveAsDraftSubscription: Subscription;
  private saveAsCompletedSubscription: Subscription;

  constructor(private templateEditService: TemplateEditService,
              private templateEditFormService: TemplateEditFormService,
              private templatesDataService: TemplatesDataService,
              private companiesDataService: CompaniesDataService,
              public formValidationService: FormValidationService,
              private notificationService: NotificationService,
              private formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private router: Router) {}

  ngOnInit() {
    this.templateItemTypes = this.templateEditFormService.getTemplateItemTypes();

    this.fetchDataAndInitForm();
    this.listenToEvents();
  }

  fetchDataAndInitForm() {
    this.companies = this.companiesDataService.getAll()
      .pipe(catchError((err) => {
        this.notificationService.pushError('COMPANIES_LOADING_ERROR');
        return throwError(err);
      }));

    this.route.params.subscribe((params) => {
      const id = params['id'];

      if (id) {
        this.editMode = true;
        this.templateEditService.editModeObserver.next();

        this.loading = true;

        this.templatesDataService.getById(id)
          .subscribe((template) => {
            this.loading = false;

            if (template.state === TemplateState.DRAFT) {
              this.template = template;
              this.initForm();
            } else {
              this.throwTemplateLoadingError();
            }
          }, () => {
            this.throwTemplateLoadingError();
          });
      } else {
        this.initForm();
      }
    });
  }

  listenToEvents() {
    this.backSubscription = this.templateEditService.backObserver
      .subscribe(() => {
        if (this.editMode) {
          this.router.navigate(['../../'], { relativeTo: this.route });
        } else {
          this.router.navigate(['../'], { relativeTo: this.route });
        }
      });


    this.undoSubscription = this.templateEditService.undoObserver
      .subscribe(() => {
        if (this.editMode) {
          const templateItems = this.templateForm.get('templateItems') as FormArray;

          this.templateForm.patchValue({
            name: this.template.name,
            description: this.template.description,
            company: this.template.company,
          });

          while (templateItems.length > 0) {
            templateItems.removeAt(0);
          }

          this.template.templateItems.forEach((item) => {
            templateItems.push(this.createTemplateItem(item));
          });
        } else {
          this.resetForm();
        }
      });

    this.saveAsDraftSubscription = this.templateEditService.saveAsDraftObserver
      .subscribe(() => {
        if (this.templateForm.valid) {
          const template = {
            ...this.templateForm.value,
            companyId: this.templateForm.value.company.id
          };

          this.save(template);
        } else {
          this.formValidationService.markAllFieldsAsTouched(this.templateForm);
        }
      });

    this.saveAsCompletedSubscription = this.templateEditService.saveAsCompletedObserver
      .subscribe(() => {
        if (this.templateForm.valid) {
          const template = {
            ...this.templateForm.value,
            companyId: this.templateForm.value.company.id,
            state: TemplateState.COMPLETED
          };

          this.save(template);
        } else {
          this.formValidationService.markAllFieldsAsTouched(this.templateForm);
        }
      });
  }

  initForm() {
    this.templateForm = this.formBuilder.group({
      name: this.formBuilder.control(this.template.name,
        [Validators.required, Validators.maxLength(100)]),
      description: this.formBuilder.control(this.template.description,
        [Validators.required, Validators.maxLength(200)]),
      state: this.formBuilder.control(TemplateState.DRAFT),
      company: this.formBuilder.control(this.template.company, [this.requiredCompanyValidator]),
      templateItems: this.formBuilder.array(this.template.templateItems ?
        this.template.templateItems.map((templateItem) => {
          return this.createTemplateItem(templateItem);
        }) :
        [this.createTemplateItem()]
      )
    });
  }

  resetForm() {
    const templateItems = this.templateForm.get('templateItems') as FormArray;

    while (templateItems.length > 1) {
      templateItems.removeAt(0);
    }

    this.templateForm.reset();
    this.templateForm.markAsPristine();
    this.templateForm.patchValue({
      state: TemplateState.DRAFT
    });

    templateItems.at(0).patchValue({
      mandatory: false,
      multiple: false
    });
  }

  save(template: TemplateModel) {
    this.loading = true;

    this.templatesDataService.save(template, this.editMode ? this.template.id : null)
      .subscribe(() => {
        this.loading = false;
        this.notificationService.pushSuccess('TEMPLATE_SAVE_SUCCESS');

        if (!this.editMode) {
          this.resetForm();
        } else {
          this.router.navigate(['../../'], { relativeTo: this.route });
        }
      }, () => {
        this.loading = false;
        this.notificationService.pushError('TEMPLATE_SAVE_ERROR');
      });
  }

  throwTemplateLoadingError() {
    this.notificationService.pushError('TEMPLATE_LOADING_ERROR');
    this.router.navigate(['../../'], { relativeTo: this.route });
  }

  createTemplateItem(templateItem: TemplateItemModel = {}) {
    return this.formBuilder.group({
      label: this.formBuilder.control(templateItem.label,
        [Validators.required, Validators.maxLength(50), RxwebValidators.unique()]),
      type: this.formBuilder.control(templateItem.type, [Validators.required]),
      mandatory: this.formBuilder.control(templateItem.mandatory ? templateItem.mandatory : false),
      multiple: this.formBuilder.control(templateItem.multiple ? templateItem.multiple : false)
    });
  }

  onAddTemplateItem() {
    const templateItems = this.templateForm.get('templateItems') as FormArray;

    if (templateItems.valid) {
      templateItems.push(this.createTemplateItem());
    }
  }

  onDeleteTemplateItem(index: number) {
    const templateItems = this.templateForm.get('templateItems') as FormArray;

    if (templateItems.length > 1) {
      templateItems.removeAt(index);
    }
  }

  requiredCompanyValidator = (control: AbstractControl) => {
    if (control.value && control.value.id) {
      return null;
    }

    return { required: true };
  };

  displayCompany = (company) => company ? company.taxIdNumber + ' ' + company.name : company;

  ngOnDestroy() {
    this.backSubscription.unsubscribe();
    this.undoSubscription.unsubscribe();
    this.saveAsDraftSubscription.unsubscribe();
    this.saveAsCompletedSubscription.unsubscribe();
  }

}
