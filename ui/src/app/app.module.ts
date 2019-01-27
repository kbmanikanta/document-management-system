import { BrowserModule } from '@angular/platform-browser';
import { APP_INITIALIZER, NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { MaterialModule } from './material.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppComponent } from './app.component';
import { FooterComponent } from './components/shared/footer/footer.component';
import { HeaderComponent } from './components/shared/header/header.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { CompanyIndexComponent } from './components/companies/company-index/company-index.component';
import { CompanyEditComponent } from './components/companies/company-edit/company-edit.component';
import { TemplateIndexComponent } from './components/templates/template-index/template-index.component';
import { TemplateEditComponent } from './components/templates/template-edit/template-edit.component';
import { DocumentIndexComponent } from './components/documents/document-index/document-index.component';
import { DocumentEditComponent } from './components/documents/document-edit/document-edit.component';
import { IndexHeaderComponent } from './components/shared/index-header/index-header.component';
import { CompaniesService } from './services/data/companies.service';
import { HttpClientModule } from '@angular/common/http';
import { NotificationService } from './services/notification.service';
import { MessagesService, messagesServiceFactory } from './services/messages.service';
import { MessagePipe } from './pipes/message.pipe';
import { CompanyItemComponent } from './components/companies/company-index/company-item/company-item.component';
import { EditHeaderComponent } from './components/shared/edit-header/edit-header.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TemplateEditHeaderComponent } from './components/templates/template-edit/template-edit-header/template-edit-header.component';
import { TemplateEditFormComponent } from './components/templates/template-edit/template-edit-form/template-edit-form.component';
import { TemplatesService } from './components/templates/templates.service';
import { CompaniesDataService } from './services/data/companies-data.service';
import { TypeaheadPipe } from './pipes/typeahead.pipe';
import { TemplatesDataService } from './services/data/templates-data.service';
import { RxReactiveFormsModule } from '@rxweb/reactive-form-validators';
import { TemplateItemComponent } from './components/templates/template-index/template-item/template-item.component';
import { ConfirmDialogService } from './services/confirm-dialog.service';

@NgModule({
  declarations: [
    AppComponent,
    FooterComponent,
    HeaderComponent,
    DashboardComponent,
    NotFoundComponent,
    CompanyIndexComponent,
    CompanyEditComponent,
    TemplateIndexComponent,
    TemplateEditComponent,
    DocumentIndexComponent,
    DocumentEditComponent,
    IndexHeaderComponent,
    MessagePipe,
    CompanyItemComponent,
    EditHeaderComponent,
    TemplateEditHeaderComponent,
    TemplateEditFormComponent,
    TypeaheadPipe,
    TemplateItemComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MaterialModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    RxReactiveFormsModule,
  ],
  providers: [CompaniesService, NotificationService, MessagesService, TemplatesService, CompaniesDataService,
    TemplatesDataService, TypeaheadPipe, ConfirmDialogService,
    { provide: APP_INITIALIZER, useFactory: messagesServiceFactory, deps: [MessagesService], multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule {}
