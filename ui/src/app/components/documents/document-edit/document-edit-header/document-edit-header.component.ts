import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { DocumentEditService } from '../document-edit.service';

@Component({
  selector: 'app-document-edit-header',
  templateUrl: './document-edit-header.component.html',
  styleUrls: ['./document-edit-header.component.css']
})
export class DocumentEditHeaderComponent implements OnInit, OnDestroy {

  titleKey = 'DOCUMENT_INSERT';

  private editModeSubscription: Subscription;

  constructor(private documentEditService: DocumentEditService) { }

  ngOnInit() {
    this.editModeSubscription = this.documentEditService.editModeObserver
      .subscribe(() => {
        this.titleKey = 'DOCUMENT_EDIT';
      });
  }

  onBack() {
    this.documentEditService.backObserver.next();
  }

  onUndo() {
    this.documentEditService.undoObserver.next();
  }

  onSave() {
    this.documentEditService.saveObserver.next();
  }

  ngOnDestroy() {
    this.editModeSubscription.unsubscribe();
  }

}
