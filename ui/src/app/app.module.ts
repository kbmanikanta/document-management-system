import { BrowserModule } from '@angular/platform-browser';
import { APP_INITIALIZER, NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppStylingModule } from './app-styling.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppComponent } from './app.component';
import { FooterComponent } from './components/footer/footer.component';
import { HeaderComponent } from './components/header/header.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ErrorComponent } from './components/error/error.component';
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

@NgModule({
  declarations: [
    AppComponent,
    FooterComponent,
    HeaderComponent,
    DashboardComponent,
    ErrorComponent,
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
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AppStylingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [CompaniesService, NotificationService, MessagesService,
    { provide: APP_INITIALIZER, useFactory: messagesServiceFactory, deps: [MessagesService], multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule {}
