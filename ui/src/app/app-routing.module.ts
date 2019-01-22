import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ErrorComponent } from './components/error/error.component';
import { CompanyIndexComponent } from './components/companies/company-index/company-index.component';
import { TemplateIndexComponent } from './components/templates/template-index/template-index.component';
import { DocumentIndexComponent } from './components/documents/document-index/document-index.component';
import { CompanyEditComponent } from './components/companies/company-edit/company-edit.component';
import { TemplateEditComponent } from './components/templates/template-edit/template-edit.component';
import { DocumentEditComponent } from './components/documents/document-edit/document-edit.component';

const routes: Routes = [
  { path: '', component: DashboardComponent },
  { path: 'companies', component: CompanyIndexComponent },
  { path: 'companies/new', component: CompanyEditComponent },
  { path: 'companies/edit/:id', component: CompanyEditComponent },
  { path: 'templates', component: TemplateIndexComponent },
  { path: 'templates/new', component: TemplateEditComponent },
  { path: 'templates/edit/:id', component: TemplateEditComponent },
  { path: 'documents', component: DocumentIndexComponent },
  { path: 'documents/new', component: DocumentEditComponent },
  { path: 'documents/edit/:id', component: DocumentEditComponent },
  { path: '**', component: ErrorComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
