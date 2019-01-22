import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CompanyModel } from '../../../../models/company.model';

@Component({
  selector: 'app-company-item',
  templateUrl: './company-item.component.html',
  styleUrls: ['./company-item.component.css']
})
export class CompanyItemComponent implements OnInit {

  @Input() company: CompanyModel;
  @Output() edit = new EventEmitter<number>();
  @Output() delete = new EventEmitter<number>();

  constructor() {}

  ngOnInit() {}

  onEdit() {
    this.edit.emit(this.company.id);
  }

  onDelete() {
    this.delete.emit(this.company.id);
  }

}
