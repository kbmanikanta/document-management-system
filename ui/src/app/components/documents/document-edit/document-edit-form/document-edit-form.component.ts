import { Component, OnDestroy, OnInit } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TemplateModel } from '../../../../models/template.model';
import { Observable, Subscription, throwError } from 'rxjs';
import { DocumentModel } from '../../../../models/document.model';
import { DocumentEditService } from '../document-edit.service';
import { TemplatesDataService } from '../../../../services/data/templates-data.service';
import { NotificationService } from '../../../../services/helpers/notification.service';
import { ActivatedRoute, Router } from '@angular/router';
import { catchError } from 'rxjs/operators';
import { DocumentsDataService } from '../../../../services/data/documents-data.service';
import { TemplateItemModel } from '../../../../models/template-item.model';
import { DocumentItemModel } from '../../../../models/document-item.model';
import { TemplateItemType } from '../../../../models/template-item-type.enum';
import { FormValidationService } from '../../../../services/helpers/form-validation.service';

@Component({
  selector: 'app-document-edit-form',
  templateUrl: './document-edit-form.component.html',
  styleUrls: ['./document-edit-form.component.css']
})
export class DocumentEditFormComponent implements OnInit, OnDestroy {

  editMode = false;

  documentForm: FormGroup;
  document: DocumentModel = {};

  templates: Observable<TemplateModel[]>;
  templateItemType = TemplateItemType;
  templateItems: TemplateItemModel[];

  loading = false;

  private backSubscription: Subscription;
  private undoSubscription: Subscription;
  private saveSubscription: Subscription;

  constructor(private documentEditService: DocumentEditService,
              private documentsDataService: DocumentsDataService,
              private templatesDataService: TemplatesDataService,
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
    this.templates = this.templatesDataService.getAllCompleted()
      .pipe(catchError((err) => {
        this.notificationService.pushError('TEMPLATES_LOADING_ERROR');
        return throwError(err);
      }));

    this.route.params.subscribe((params) => {
      const id = params['id'];

      if (id) {
        this.editMode = true;
        this.documentEditService.editModeObserver.next();

        this.loading = true;

        this.documentsDataService.getById(id)
          .subscribe((document) => {
            this.loading = false;

            this.document = document;
            this.initForm();
          }, () => {
            this.notificationService.pushError('DOCUMENT_LOADING_ERROR');
            this.router.navigate(['../../'], { relativeTo: this.route });
          });
      } else {
        this.initForm();
      }
    });
  }

  listenToEvents() {
    this.backSubscription = this.documentEditService.backObserver
      .subscribe(() => {
        if (this.editMode) {
          this.router.navigate(['../../'], { relativeTo: this.route });
        } else {
          this.router.navigate(['../'], { relativeTo: this.route });
        }
      });

    this.undoSubscription = this.documentEditService.undoObserver
      .subscribe(() => {
        if (this.editMode) {
          this.documentForm.patchValue({
            name: this.document.name,
            template: this.document.template
          });

          this.createDocumentItemGroups(this.document.documentItems);
        } else {
          this.resetForm();
        }
      });

    this.saveSubscription = this.documentEditService.saveObserver
      .subscribe(() => {
        if (this.documentForm.valid) {
          const document = {
            ...this.documentForm.value,
            templateId: this.documentForm.value.template.id,
          };

          this.save(document);
        } else {
          this.formValidationService.markAllFieldsAsTouched(this.documentForm);
        }
      });
  }

  initForm() {
    this.documentForm = this.formBuilder.group({
      name: this.formBuilder.control(this.document.name,
        [Validators.required, Validators.maxLength(100)]),
      template: this.formBuilder.control(this.document.template, [this.requiredTemplateValidator]),
      documentItems: this.formBuilder.array(this.document.documentItems ?
        this.document.documentItems.map((documentItem) => this.createDocumentItemGroup(documentItem)) : [])
    });

    if (this.document.documentItems) {
      this.templateItems = this.document.documentItems
        .map((documentItem) => documentItem.templateItem).slice(0);
    }
  }

  resetForm() {
    const documentItems = this.documentForm.get('documentItems') as FormArray;
    this.templateItems = null;

    while (documentItems.length > 0) {
      documentItems.removeAt(0);
    }

    this.documentForm.reset();
    this.documentForm.markAsPristine();
  }

  save(document: DocumentModel) {
    this.loading = true;

    this.documentsDataService.save(document, this.editMode ? this.document.id : null)
      .subscribe(() => {
        this.loading = false;
        this.notificationService.pushSuccess('DOCUMENT_SAVE_SUCCESS');

        if (!this.editMode) {
          this.resetForm();
        } else {
          this.router.navigate(['../../'], { relativeTo: this.route });
        }
      }, () => {
        this.loading = false;
        this.notificationService.pushError('DOCUMENT_SAVE_ERROR');
      });
  }

  createDocumentItemGroups(documentItems: DocumentItemModel[]) {
    const documentItemGroups = this.documentForm.get('documentItems') as FormArray;

    while (documentItemGroups.length > 0) {
      documentItemGroups.removeAt(0);
    }
    this.templateItems = [];

    documentItems.forEach((documentItem) => {
      documentItemGroups.push(this.createDocumentItemGroup(documentItem));
      this.templateItems.push(documentItem.templateItem);
    });
  }

  createDocumentItemGroup(documentItem: DocumentItemModel) {
    let documentItemGroup: FormGroup;
    const type = documentItem.templateItem.type;
    const mandatory = documentItem.templateItem.mandatory;

    if (type === TemplateItemType.INTEGER) {
      documentItemGroup = this.formBuilder.group({
        integerValue: this.formBuilder.control(documentItem.integerValue, mandatory ? [
          Validators.required, Validators.pattern(/^-?\d+$/)
        ] : [Validators.pattern(/^-?\d+$/)])
      });
    } else if (type === TemplateItemType.DOUBLE) {
      documentItemGroup = this.formBuilder.group({
        doubleValue: this.formBuilder.control(documentItem.doubleValue, mandatory ? [
          Validators.required, Validators.pattern(/^-?\d+(\.\d+)?$/)
        ] : [Validators.pattern(/^-?\d+(\.\d+)?$/)])
      });
    } else if (type === TemplateItemType.STRING) {
      documentItemGroup = this.formBuilder.group({
        stringValue: this.formBuilder.control(documentItem.stringValue, mandatory ? [
          Validators.required
        ] : [])
      });
    } else if (type === TemplateItemType.DATE) {
      documentItemGroup = this.formBuilder.group({
        dateValue: this.formBuilder.control(documentItem.dateValue, mandatory ? [
          Validators.required, Validators.pattern(/^\d{2}\/\d{2}\/\d{4}$/)
        ] : [Validators.pattern(/^\d{2}\/\d{2}\/\d{4}$/)])
      });
    } else {
      documentItemGroup = this.formBuilder.group({
        booleanValue: this.formBuilder.control(documentItem.booleanValue ? documentItem.booleanValue : false)
      });
    }

    documentItemGroup.addControl('templateItemId', this.formBuilder.control(documentItem.templateItem.id));
    return documentItemGroup;
  }

  onSelectTemplate(id: number) {
    this.templatesDataService.getById(id)
      .subscribe((template: TemplateModel) => {
        this.createDocumentItemGroups(template.templateItems
          .map((templateItem) => ({
            templateItem: templateItem
          })));
      }, () => {
        this.notificationService.pushError('TEMPLATE_LOADING_ERROR');
      });
  }

  onAddItem(index: number) {
    const documentItems = this.documentForm.get('documentItems') as FormArray;
    const currentItem = documentItems.at(index);
    const currentItemValue = (currentItem.get('integerValue') || currentItem.get('doubleValue')
      || currentItem.get('stringValue') || currentItem.get('dateValue')
      || currentItem.get('booleanValue')).value;

    if ((currentItemValue && currentItem.valid) || currentItemValue === false) {
      const templateItem = {
        ...this.templateItems[index]
      };

      const documentItem = {
        templateItem: templateItem
      };

      this.templateItems.splice(index + 1, 0, templateItem);
      documentItems.insert(index + 1, this.createDocumentItemGroup(documentItem));
    } else {
      currentItem.markAsTouched();
    }
  }

  onRemoveItem(index: number) {
    const documentItems = this.documentForm.get('documentItems') as FormArray;

    documentItems.removeAt(index);
    this.templateItems.splice(index, 1);
  }

  isLastItemInGroup(index: number) {
    if (this.templateItems.length > index + 1) {
      return this.templateItems[index].id !== this.templateItems[index + 1].id;
    }

    return true;
  }

  isNotValidRequired(index: number, formControlName: string) {
    const documentItemGroup = (<FormArray>this.documentForm.get('documentItems')).at(index) as FormGroup;
    return this.formValidationService.isNotValidRequired(documentItemGroup, formControlName);
  }

  isNotValidPattern(index: number, formControlName: string) {
    const documentItemGroup = (<FormArray>this.documentForm.get('documentItems')).at(index) as FormGroup;
    return this.formValidationService.isNotValidPattern(documentItemGroup, formControlName);
  }

  requiredTemplateValidator = (control: AbstractControl) => {
    if (control.value && control.value.id) {
      return null;
    }

    return { required: true };
  };

  displayTemplate = (template) => template ? template.name : template;

  ngOnDestroy() {
    this.backSubscription.unsubscribe();
    this.undoSubscription.unsubscribe();
    this.saveSubscription.unsubscribe();
  }

}
