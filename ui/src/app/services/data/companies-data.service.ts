import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { CompanyModel } from '../../models/company.model';

const BACKEND_URL = `${environment.backendUrl}/companies`;

@Injectable()
export class CompaniesDataService {

  constructor(private http: HttpClient) {}

  save(company: CompanyModel, id?: number) {
    if (id) {
      return this.http.put(`${BACKEND_URL}/${id}`, company);
    } else {
      return this.http.post(BACKEND_URL, company);
    }
  }

  delete(id: number) {
    return this.http.delete(`${BACKEND_URL}/${id}`);
  }

  getById(id: number) {
    return this.http.get<CompanyModel>(`${BACKEND_URL}/${id}`);
  }

  getAll() {
    return this.http.get<CompanyModel[]>(BACKEND_URL);
  }

  getByTaxIdNumber(taxIdNumber: string) {
    return this.http.get<CompanyModel[]>(`${BACKEND_URL}`,
      { params: { taxIdNumber: taxIdNumber } });
  }

}
