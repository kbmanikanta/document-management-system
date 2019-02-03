import { Component, OnInit } from '@angular/core';
import { CompanyModel } from '../../../models/company.model';
import { NotificationService } from '../../../services/helpers/notification.service';
import { environment } from '../../../../environments/environment';
import { ActivatedRoute, Router } from '@angular/router';
import { ConfirmDialogService } from '../../../services/helpers/confirm-dialog.service';
import { TypeaheadPipe } from '../../../pipes/typeahead.pipe';
import { CompaniesDataService } from '../../../services/data/companies-data.service';

@Component({
  selector: 'app-company-index',
  templateUrl: './company-index.component.html',
  styleUrls: ['./company-index.component.css']
})
export class CompanyIndexComponent implements OnInit {

  companies: CompanyModel[] = [];
  filteredCompanies: CompanyModel[] = [];
  paginatedCompanies: CompanyModel[] = [];

  loading = false;
  limit = environment.pageLimit;

  constructor(private companiesDataService: CompaniesDataService,
              private notificationService: NotificationService,
              private confirmDialogService: ConfirmDialogService,
              private typeaheadPipe: TypeaheadPipe,
              private route: ActivatedRoute,
              private router: Router) {}

  ngOnInit() {
    this.fetchData();
  }

  fetchData() {
    this.loading = true;

    this.companiesDataService.getAll()
      .subscribe((companies) => {
        this.loading = false;

        this.companies = companies;
        this.filteredCompanies = this.companies;
        this.paginatedCompanies = this.filteredCompanies.slice(0, this.limit);
      }, () => {
        this.loading = false;
        this.notificationService.pushError('COMPANIES_LOADING_ERROR');
      });
  }

  onSearch(filterString: string) {
    if (filterString.trim().length === 0) {
      this.filteredCompanies = this.companies;
    } else {
      this.filteredCompanies = this.typeaheadPipe.transform(this.companies, filterString,
        ['name', 'taxIdNumber']);
    }

    this.paginatedCompanies = this.filteredCompanies.slice(0, this.limit);
  }

  onRefresh() {
    this.fetchData();
  }

  onEdit(id: number) {
    this.router.navigate(['edit', id], { relativeTo: this.route });
  }

  onDelete(id: number) {
    this.confirmDialogService.openDialog('COMPANY_DELETE_QUESTION')
      .subscribe((confirmed: boolean) => {
        if (confirmed) {
          this.loading = true;
          this.companiesDataService.delete(id)
            .subscribe(() => {
              this.notificationService.pushSuccess('COMPANY_DELETE_SUCCESS');
              this.fetchData();
            }, () => {
              this.loading = false;
              this.notificationService.pushError('COMPANY_DELETE_ERROR');
            });
        }
      });
  }

  onLoadMore() {
    this.limit += environment.pageLimit;
    this.paginatedCompanies = this.filteredCompanies.slice(0, this.limit);
  }

}
