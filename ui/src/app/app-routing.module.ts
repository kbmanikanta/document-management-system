import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { DashboardComponent } from './components/dashboard/dashboard.component';
import { NotFoundComponent } from './components/not-found/not-found.component';

import { CompanyEditComponent } from './components/companies/company-edit/company-edit.component';
import { CompanyIndexComponent } from './components/companies/company-index/company-index.component';
import { DocumentEditComponent } from './components/documents/document-edit/document-edit.component';
import { DocumentIndexComponent } from './components/documents/document-index/document-index.component';
import { TemplateEditComponent } from './components/templates/template-edit/template-edit.component';
import { TemplateIndexComponent } from './components/templates/template-index/template-index.component';

const routes: Routes = [
  { path: '', component: DashboardComponent },
  { path: 'companies', component: CompanyIndexComponent },
  { path: 'companies/add', component: CompanyEditComponent },
  { path: 'companies/edit/:id', component: CompanyEditComponent },
  { path: 'templates', component: TemplateIndexComponent },
  { path: 'templates/add', component: TemplateEditComponent },
  { path: 'templates/edit/:id', component: TemplateEditComponent },
  { path: 'documents', component: DocumentIndexComponent },
  { path: 'documents/add', component: DocumentEditComponent },
  { path: 'documents/edit/:id', component: DocumentEditComponent },
  { path: '**', component: NotFoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
