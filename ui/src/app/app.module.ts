import { APP_INITIALIZER, NgModule } from '@angular/core';

import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RxReactiveFormsModule } from '@rxweb/reactive-form-validators';

import { AppRoutingModule } from './app-routing.module';
import { MaterialModule } from './material.module';

import { AppComponent } from './app.component';

import { HeaderComponent } from './components/shared/header/header.component';
import { FooterComponent } from './components/shared/footer/footer.component';
import { IndexHeaderComponent } from './components/shared/index-header/index-header.component';

import { DashboardComponent } from './components/dashboard/dashboard.component';

import { NotFoundComponent } from './components/not-found/not-found.component';

import { CompanyEditComponent } from './components/companies/company-edit/company-edit.component';
import { CompanyEditFormComponent } from './components/companies/company-edit/company-edit-form/company-edit-form.component';
import { CompanyEditHeaderComponent } from './components/companies/company-edit/company-edit-header/company-edit-header.component';

import { CompanyIndexComponent } from './components/companies/company-index/company-index.component';
import { CompanyItemComponent } from './components/companies/company-index/company-item/company-item.component';

import { DocumentEditComponent } from './components/documents/document-edit/document-edit.component';
import { DocumentEditFormComponent } from './components/documents/document-edit/document-edit-form/document-edit-form.component';
import { DocumentEditHeaderComponent } from './components/documents/document-edit/document-edit-header/document-edit-header.component';

import { DocumentIndexComponent } from './components/documents/document-index/document-index.component';
import { DocumentItemComponent } from './components/documents/document-index/document-item/document-item.component';

import { TemplateEditComponent } from './components/templates/template-edit/template-edit.component';
import { TemplateEditFormComponent } from './components/templates/template-edit/template-edit-form/template-edit-form.component';
import { TemplateEditHeaderComponent } from './components/templates/template-edit/template-edit-header/template-edit-header.component';

import { TemplateIndexComponent } from './components/templates/template-index/template-index.component';
import { TemplateItemComponent } from './components/templates/template-index/template-item/template-item.component';

import { TypeaheadPipe } from './pipes/typeahead.pipe';
import { MessagePipe } from './pipes/message.pipe';

import { ConfirmDialogService } from './services/helpers/confirm-dialog.service';
import { FormValidationService } from './services/helpers/form-validation.service';
import { MessagesService, messagesServiceFactory } from './services/helpers/messages.service';
import { NotificationService } from './services/helpers/notification.service';

import { CompaniesDataService } from './services/data/companies-data.service';
import { DocumentsDataService } from './services/data/documents-data.service';
import { TemplatesDataService } from './services/data/templates-data.service';

import { CompanyEditService } from './components/companies/company-edit/company-edit.service';

import { DocumentEditService } from './components/documents/document-edit/document-edit.service';

import { TemplateEditService } from './components/templates/template-edit/template-edit.service';
import { TemplateEditFormService } from './components/templates/template-edit/template-edit-form/template-edit-form.service';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent, FooterComponent, IndexHeaderComponent,
    DashboardComponent,
    NotFoundComponent,
    CompanyEditComponent, CompanyEditFormComponent, CompanyEditHeaderComponent,
    CompanyIndexComponent, CompanyItemComponent,
    DocumentEditComponent, DocumentEditFormComponent, DocumentEditHeaderComponent,
    DocumentIndexComponent, DocumentItemComponent,
    TemplateEditComponent, TemplateEditFormComponent, TemplateEditHeaderComponent,
    TemplateIndexComponent, TemplateItemComponent,
    MessagePipe,
    TypeaheadPipe,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    RxReactiveFormsModule,
    AppRoutingModule,
    MaterialModule,
  ],
  providers: [
    TypeaheadPipe,
    ConfirmDialogService, FormValidationService, MessagesService, NotificationService,
    CompaniesDataService, TemplatesDataService, DocumentsDataService,
    CompanyEditService,
    DocumentEditService,
    TemplateEditService, TemplateEditFormService,
    { provide: APP_INITIALIZER, useFactory: messagesServiceFactory, deps: [MessagesService], multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule {}
