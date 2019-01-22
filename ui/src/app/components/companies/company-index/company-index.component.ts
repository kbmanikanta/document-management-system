import { Component, OnDestroy, OnInit } from '@angular/core';
import { CompaniesService } from '../../../services/data/companies.service';
import { Subscription } from 'rxjs';
import { CompanyModel } from '../../../models/company.model';
import { NotificationService } from '../../../services/notification.service';
import { environment } from '../../../../environments/environment';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-company-index',
  templateUrl: './company-index.component.html',
  styleUrls: ['./company-index.component.css']
})
export class CompanyIndexComponent implements OnInit, OnDestroy {

  companiesDataSubscription: Subscription;
  companiesProgressSubscription: Subscription;
  deletedCompanySubscription: Subscription;
  companies: CompanyModel[] = [];
  filteredCompanies: CompanyModel[] = [];
  paginatedCompanies: CompanyModel[] = [];
  getLoading = false;
  deleteLoading = false;
  progress: number;
  limit = environment.pageLimit;

  constructor(private companiesService: CompaniesService,
              private notificationService: NotificationService,
              private route: ActivatedRoute,
              private router: Router) {}

  ngOnInit() {
    this.onRefresh();

    this.companiesProgressSubscription = this.companiesService.getCompaniesProgressListener()
      .subscribe((progress: number) => {
        this.progress = progress;
      });

    this.companiesDataSubscription = this.companiesService.getCompaniesDataListener()
      .subscribe((companies: CompanyModel[]) => {
        if (companies) {
          this.companies = companies;
          this.filteredCompanies = this.companies;
          this.paginatedCompanies = this.filteredCompanies.slice(0, this.limit);
        } else {
          this.getLoading = false;
          this.notificationService.pushErrorNotification('COMPANIES_LOADING_ERROR');
        }
      });

    this.deletedCompanySubscription = this.companiesService.getDeletedCompanyListener()
      .subscribe((isDeleted) => {
        this.deleteLoading = false;
        this.onRefresh();

        if (isDeleted) {
          this.notificationService.pushSuccessNotification('COMPANY_DELETE_SUCCESS');
        } else {
          this.notificationService.pushErrorNotification('COMPANY_DELETE_ERROR');
        }
      });
  }

  onRefresh() {
    this.progress = 0;
    this.getLoading = true;

    setTimeout(() => {
      this.companiesService.getAll();
    }, 1000);
  }

  onSearch(filterString: string) {
    const filters = filterString.split(' ').filter((filter) => filter);

    if (filters.length > 0) {
      this.filteredCompanies = this.companies.filter((company) =>
        filters.reduce((acc, filter) => {
          if (company.name.toLowerCase().indexOf(filter) > -1 ||
            company.taxIdNumber.toLowerCase().indexOf(filter) > -1) {
            acc++;
          }
          return acc;
        }, 0) === filters.length);

    } else {
      this.filteredCompanies = this.companies;
    }

    this.paginatedCompanies = this.filteredCompanies.slice(0, this.limit);
  }

  onLoadMore() {
    this.limit += environment.pageLimit;
    this.paginatedCompanies = this.filteredCompanies.slice(0, this.limit);
  }

  onEdit(id: number) {
    this.router.navigate(['edit', id], { relativeTo: this.route });
  }

  onDelete(id: number) {
    this.deleteLoading = true;

    setTimeout(() => {
      this.companiesService.delete(id);
    }, 1000);
  }

  ngOnDestroy() {
    this.companiesDataSubscription.unsubscribe();
    this.companiesProgressSubscription.unsubscribe();
    this.deletedCompanySubscription.unsubscribe();
  }

}
