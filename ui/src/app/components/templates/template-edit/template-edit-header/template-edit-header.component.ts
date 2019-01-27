import { Component, OnDestroy, OnInit } from '@angular/core';
import { TemplatesService } from '../../templates.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-template-edit-header',
  templateUrl: './template-edit-header.component.html',
  styleUrls: ['./template-edit-header.component.css']
})
export class TemplateEditHeaderComponent implements OnInit, OnDestroy {

  titleKey = 'TEMPLATE_INSERT';

  editModeSubscription: Subscription;

  constructor(private templatesService: TemplatesService) { }

  ngOnInit() {
    this.editModeSubscription = this.templatesService.editModeObserver
      .subscribe(() => {
        this.titleKey = 'TEMPLATE_EDIT';
      });
  }

  onBack() {
    this.templatesService.backObserver.next();
  }

  onUndo() {
    this.templatesService.undoObserver.next();
  }

  onSaveAsDraft() {
    this.templatesService.saveAsDraftObserver.next();
  }

  onSaveAsCompleted() {
    this.templatesService.saveAsCompletedObserver.next();
  }

  ngOnDestroy() {
    this.editModeSubscription.unsubscribe();
  }

}
