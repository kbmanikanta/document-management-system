import { Component, OnInit } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { NotificationService } from '../../../services/helpers/notification.service';
import { ConfirmDialogService } from '../../../services/helpers/confirm-dialog.service';
import { TypeaheadPipe } from '../../../pipes/typeahead.pipe';
import { ActivatedRoute, Router } from '@angular/router';
import { DocumentModel } from '../../../models/document.model';
import { DocumentsDataService } from '../../../services/data/documents-data.service';

@Component({
  selector: 'app-document-index',
  templateUrl: './document-index.component.html',
  styleUrls: ['./document-index.component.css']
})
export class DocumentIndexComponent implements OnInit {

  documents: DocumentModel[] = [];
  filteredDocuments: DocumentModel[] = [];
  paginatedDocuments: DocumentModel[] = [];

  loading = false;
  limit = environment.pageLimit;

  constructor(private documentsDataService: DocumentsDataService,
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

    this.documentsDataService.getAll()
      .subscribe((documents) => {
        this.loading = false;

        this.documents = documents;
        this.filteredDocuments = this.documents;
        this.paginatedDocuments = this.filteredDocuments.slice(0, this.limit);
      }, () => {
        this.loading = false;
        this.notificationService.pushError('DOCUMENTS_LOADING_ERROR');
      });
  }

  onSearch(filterString: string) {
    if (filterString.trim().length === 0) {
      this.filteredDocuments = this.documents;
    } else {
      this.filteredDocuments = this.typeaheadPipe.transform(this.documents, filterString,
        ['name', 'templateName']);
    }

    this.paginatedDocuments = this.filteredDocuments.slice(0, this.limit);
  }

  onRefresh() {
    this.fetchData();
  }

  onEdit(id: number) {
    this.router.navigate(['edit', id], { relativeTo: this.route });
  }

  onDelete(id: number) {
    this.confirmDialogService.openDialog('DOCUMENT_DELETE_QUESTION')
      .subscribe((confirmed: boolean) => {
        if (confirmed) {
          this.loading = true;
          this.documentsDataService.delete(id)
            .subscribe(() => {
              this.notificationService.pushSuccess('DOCUMENT_DELETE_SUCCESS');
              this.fetchData();
            }, () => {
              this.loading = false;
              this.notificationService.pushError('DOCUMENT_DELETE_ERROR');
            });
        }
      });
  }

  onLoadMore() {
    this.limit += environment.pageLimit;
    this.paginatedDocuments = this.filteredDocuments.slice(0, this.limit);
  }

}
