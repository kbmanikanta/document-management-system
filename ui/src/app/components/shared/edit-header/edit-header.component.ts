import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-edit-header',
  templateUrl: './edit-header.component.html',
  styleUrls: ['./edit-header.component.css']
})
export class EditHeaderComponent implements OnInit {

  @Input() titleKey: string;
  @Output() reset = new EventEmitter();
  @Output() save = new EventEmitter();
  editMode = false;

  constructor(private route: ActivatedRoute, private router: Router) {}

  ngOnInit() {
    this.route.params.subscribe((params) => {
      if (params['id']) {
        this.editMode = true;
      }
      this.titleKey = this.editMode ? this.titleKey + '_EDIT' : this.titleKey + '_INSERT';
    });
  }

  onBack() {
    if (this.editMode) {
      this.router.navigate(['../../'], { relativeTo: this.route });
    } else {
      this.router.navigate(['../'], { relativeTo: this.route });
    }
  }

  onReset() {
    this.reset.emit();
  }

  onSave() {
    this.save.emit();
  }

}
