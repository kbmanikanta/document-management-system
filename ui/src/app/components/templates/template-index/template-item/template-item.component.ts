import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { TemplateModel } from '../../../../models/template.model';
import { TemplateState } from '../../../../models/template-state.enum';

@Component({
  selector: 'app-template-item',
  templateUrl: './template-item.component.html',
  styleUrls: ['./template-item.component.css']
})
export class TemplateItemComponent implements OnInit {

  @Input() template: TemplateModel;
  templateState = TemplateState;

  @Output() edit = new EventEmitter<number>();
  @Output() delete = new EventEmitter<number>();
  @Output() saveAsCompleted = new EventEmitter<number>();

  constructor() {}

  ngOnInit() {}

  onSaveAsCompleted() {
    this.saveAsCompleted.emit(this.template.id);
  }

  onEdit() {
    this.edit.emit(this.template.id);
  }

  onDelete() {
    this.delete.emit(this.template.id);
  }

}
