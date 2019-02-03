import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { CompanyEditService } from '../company-edit.service';

@Component({
  selector: 'app-company-edit-header',
  templateUrl: './company-edit-header.component.html',
  styleUrls: ['./company-edit-header.component.css']
})
export class CompanyEditHeaderComponent implements OnInit, OnDestroy {

  titleKey = 'COMPANY_INSERT';

  private editModeSubscription: Subscription;

  constructor(private companyEditService: CompanyEditService) { }

  ngOnInit() {
    this.editModeSubscription = this.companyEditService.editModeObserver
      .subscribe(() => {
        this.titleKey = 'COMPANY_EDIT';
      });
  }

  onBack() {
    this.companyEditService.backObserver.next();
  }

  onUndo() {
    this.companyEditService.undoObserver.next();
  }

  onSave() {
    this.companyEditService.saveObserver.next();
  }

  ngOnDestroy() {
    this.editModeSubscription.unsubscribe();
  }

}
