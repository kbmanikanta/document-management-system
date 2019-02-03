import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { DocumentModel } from '../../../../models/document.model';

@Component({
  selector: 'app-document-item',
  templateUrl: './document-item.component.html',
  styleUrls: ['./document-item.component.css']
})
export class DocumentItemComponent implements OnInit {

  @Input() document: DocumentModel;

  @Output() edit = new EventEmitter<number>();
  @Output() delete = new EventEmitter<number>();

  constructor() {}

  ngOnInit() {}

  onEdit() {
    this.edit.emit(this.document.id);
  }

  onDelete() {
    this.delete.emit(this.document.id);
  }

}
