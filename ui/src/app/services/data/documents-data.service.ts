import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { DocumentModel } from '../../models/document.model';
import { map } from 'rxjs/operators';

const BACKEND_URL = `${environment.backendUrl}/documents`;

@Injectable()
export class DocumentsDataService {

  constructor(private http: HttpClient) {}

  save(document: DocumentModel, id?: number) {
    if (id) {
      return this.http.put(`${BACKEND_URL}/${id}`, document);
    } else {
      return this.http.post(BACKEND_URL, document);
    }
  }

  delete(id: number) {
    return this.http.delete(`${BACKEND_URL}/${id}`);
  }

  getById(id: number) {
    return this.http.get<DocumentModel>(`${BACKEND_URL}/${id}`);
  }

  getAll() {
    return this.http.get<DocumentModel[]>(BACKEND_URL)
      .pipe(map((documents) =>
        documents.map((document) => {
          document.templateName = document.template.name;
          return document;
        })));
  }

}
