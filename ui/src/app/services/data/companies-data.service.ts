import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { CompanyModel } from '../../models/company.model';

const BACKEND_URL = `${environment.backendUrl}/companies`;

@Injectable()
export class CompaniesDataService {

  constructor(private http: HttpClient) {}

  getAll() {
    return this.http.get<CompanyModel[]>(BACKEND_URL);
  }

}
