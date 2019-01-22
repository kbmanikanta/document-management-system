import { Injectable } from '@angular/core';
import { CompanyModel } from '../../models/company.model';
import { Subject } from 'rxjs';
import { HttpClient, HttpEvent, HttpEventType } from '@angular/common/http';
import { environment } from '../../../environments/environment';

const BACKEND_URL = `${environment.backendUrl}/companies`;

@Injectable()
export class CompaniesService {

  private companiesDataListener = new Subject<CompanyModel[]>();
  private companyDataListener = new Subject<CompanyModel>();
  private savedCompanyListener = new Subject<boolean>();
  private deletedCompanyListener = new Subject<boolean>();
  private companiesProgressListener = new Subject<number>();

  constructor(private http: HttpClient) {}

  getCompaniesDataListener() {
    return this.companiesDataListener.asObservable();
  }

  getCompanyDataListener() {
    return this.companyDataListener.asObservable();
  }

  getSavedCompanyListener() {
    return this.savedCompanyListener.asObservable();
  }

  getDeletedCompanyListener() {
    return this.deletedCompanyListener.asObservable();
  }

  getCompaniesProgressListener() {
    return this.companiesProgressListener.asObservable();
  }

  getAll() {
    this.http.get<CompanyModel[]>(`${BACKEND_URL}`, {
      reportProgress: true,
      observe: 'events'
    }).subscribe((event: HttpEvent<CompanyModel[]>) => {
      if (event.type === HttpEventType.DownloadProgress) {
        this.companiesProgressListener.next(Math.round(event.loaded / event.total * 100));
      } else if (event.type === HttpEventType.Response) {
        this.companiesDataListener.next(event.body);
      }
    }, () => {
      this.companiesDataListener.next(null);
    });
  }

  getById(id: number) {
    this.http.get<CompanyModel>(`${BACKEND_URL}/${id}`, {
      reportProgress: true,
      observe: 'events'
    }).subscribe((event: HttpEvent<CompanyModel>) => {
      if (event.type === HttpEventType.DownloadProgress) {
        this.companiesProgressListener.next(Math.round(event.loaded / event.total * 100));
      } else if (event.type === HttpEventType.Response) {
        this.companyDataListener.next(event.body);
      }
    }, () => {
      this.companyDataListener.next(null);
    });
  }

  insert(company: CompanyModel) {
    this.http.post(`${BACKEND_URL}`, company)
      .subscribe(() => {
        this.savedCompanyListener.next(true);
      }, () => {
        this.savedCompanyListener.next(false);
      });
  }

  update(company: CompanyModel, id: number) {
    this.http.put(`${BACKEND_URL}/${id}`, company)
      .subscribe(() => {
        this.savedCompanyListener.next(true);
      }, () => {
        this.savedCompanyListener.next(false);
      });
  }

  delete(id: number) {
    this.http.delete(`${BACKEND_URL}/${id}`)
      .subscribe(() => {
        this.deletedCompanyListener.next(true);
      }, () => {
        this.deletedCompanyListener.next(false);
      });
  }

  getByTaxIdNumber(taxIdNumber: string) {
    return this.http.get<CompanyModel[]>(`${BACKEND_URL}`,
      { params: { taxIdNumber: taxIdNumber } });
  }

}
