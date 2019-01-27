import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-index-header',
  templateUrl: './index-header.component.html',
  styleUrls: ['./index-header.component.css']
})
export class IndexHeaderComponent implements OnInit {

  @Input() titleKey: string;

  @Output() refresh = new EventEmitter();
  @Output() search = new EventEmitter<string>();

  expandedSearch = false;

  constructor(private route: ActivatedRoute, private router: Router) {}

  ngOnInit() {}

  onBack() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }

  onRefresh() {
    this.refresh.emit();
  }

  onAdd() {
    this.router.navigate(['add'], { relativeTo: this.route });
  }

  onClose() {
    this.expandedSearch = false;
    this.search.emit('');
  }

  onEnter(event: any) {
    this.search.emit(event.target.value.trim().toLowerCase());
  }

}
