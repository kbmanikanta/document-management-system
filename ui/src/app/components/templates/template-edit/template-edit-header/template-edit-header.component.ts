import { Component, OnDestroy, OnInit } from '@angular/core';
import { TemplateEditService } from '../template-edit.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-template-edit-header',
  templateUrl: './template-edit-header.component.html',
  styleUrls: ['./template-edit-header.component.css']
})
export class TemplateEditHeaderComponent implements OnInit, OnDestroy {

  titleKey = 'TEMPLATE_INSERT';

  private editModeSubscription: Subscription;

  constructor(private templateEditService: TemplateEditService) { }

  ngOnInit() {
    this.editModeSubscription = this.templateEditService.editModeObserver
      .subscribe(() => {
        this.titleKey = 'TEMPLATE_EDIT';
      });
  }

  onBack() {
    this.templateEditService.backObserver.next();
  }

  onUndo() {
    this.templateEditService.undoObserver.next();
  }

  onSaveAsDraft() {
    this.templateEditService.saveAsDraftObserver.next();
  }

  onSaveAsCompleted() {
    this.templateEditService.saveAsCompletedObserver.next();
  }

  ngOnDestroy() {
    this.editModeSubscription.unsubscribe();
  }

}
