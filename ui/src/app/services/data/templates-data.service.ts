import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { TemplateModel } from '../../models/template.model';
import { map } from 'rxjs/operators';
import { TemplateState } from '../../models/template-state.enum';

const BACKEND_URL = `${environment.backendUrl}/templates`;

@Injectable()
export class TemplatesDataService {

  constructor(private http: HttpClient) {}

  save(template: TemplateModel, id?: number) {
    if (id) {
      return this.http.put(`${BACKEND_URL}/${id}`, template);
    } else {
      return this.http.post(BACKEND_URL, template);
    }
  }

  saveAsCompleted(id: number) {
    return this.http.patch(`${BACKEND_URL}/${id}/complete`, {});
  }

  delete(id: number) {
    return this.http.delete(`${BACKEND_URL}/${id}`);
  }

  getById(id: number) {
    return this.http.get<TemplateModel>(`${BACKEND_URL}/${id}`);
  }

  getAll() {
    return this.http.get<TemplateModel[]>(BACKEND_URL)
      .pipe(map((templates) =>
        templates.map((template) => {
          template.companyName = template.company.name;
          template.companyTaxIdNumber = template.company.taxIdNumber;

          return template;
        })));
  }

  getAllCompleted() {
    return this.http.get<TemplateModel[]>(BACKEND_URL, {
      params: {
        state: String(TemplateState.COMPLETED)
      }
    });
  }

}
