import { Component, OnInit } from '@angular/core';
import { TemplatesDataService } from '../../../services/data/templates-data.service';
import { TemplateModel } from '../../../models/template.model';
import { environment } from '../../../../environments/environment';
import { NotificationService } from '../../../services/helpers/notification.service';
import { ActivatedRoute, Router } from '@angular/router';
import { TypeaheadPipe } from '../../../pipes/typeahead.pipe';
import { ConfirmDialogService } from '../../../services/helpers/confirm-dialog.service';

@Component({
  selector: 'app-template-index',
  templateUrl: './template-index.component.html',
  styleUrls: ['./template-index.component.css']
})
export class TemplateIndexComponent implements OnInit {

  templates: TemplateModel[] = [];
  filteredTemplates: TemplateModel[] = [];
  paginatedTemplates: TemplateModel[] = [];

  loading = false;
  limit = environment.pageLimit;

  constructor(private templatesDataService: TemplatesDataService,
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

    this.templatesDataService.getAll()
      .subscribe((templates) => {
        this.loading = false;

        this.templates = templates;
        this.filteredTemplates = this.templates;
        this.paginatedTemplates = this.filteredTemplates.slice(0, this.limit);
      }, () => {
        this.loading = false;
        this.notificationService.pushError('TEMPLATES_LOADING_ERROR');
      });
  }

  onSearch(filterString: string) {
    if (filterString.trim().length === 0) {
      this.filteredTemplates = this.templates;
    } else {
      this.filteredTemplates = this.typeaheadPipe.transform(this.templates, filterString,
        ['name', 'companyName', 'companyTaxIdNumber']);
    }

    this.paginatedTemplates = this.filteredTemplates.slice(0, this.limit);
  }

  onRefresh() {
    this.fetchData();
  }

  onSaveAsCompleted(id: number) {
    this.loading = true;

    this.templatesDataService.saveAsCompleted(id)
      .subscribe(() => {
        this.notificationService.pushSuccess('TEMPLATE_SAVE_AS_COMPLETED_SUCCESS');
        this.fetchData();
      }, () => {
        this.loading = false;
        this.notificationService.pushError('TEMPLATE_SAVE_ERROR');
      });
  }

  onEdit(id: number) {
    this.router.navigate(['edit', id], { relativeTo: this.route });
  }

  onDelete(id: number) {
    this.confirmDialogService.openDialog('TEMPLATE_DELETE_QUESTION')
      .subscribe((confirmed: boolean) => {
        if (confirmed) {
          this.loading = true;
          this.templatesDataService.delete(id)
            .subscribe(() => {
              this.notificationService.pushSuccess('TEMPLATE_DELETE_SUCCESS');
              this.fetchData();
            }, () => {
              this.loading = false;
              this.notificationService.pushError('TEMPLATE_DELETE_ERROR');
            });
        }
      });
  }

  onLoadMore() {
    this.limit += environment.pageLimit;
    this.paginatedTemplates = this.filteredTemplates.slice(0, this.limit);
  }

}
