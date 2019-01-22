import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-template-index',
  templateUrl: './template-index.component.html',
  styleUrls: ['./template-index.component.css']
})
export class TemplateIndexComponent implements OnInit {

  constructor() {}

  ngOnInit() {}

  onSearch(filterString: string) {
    console.log(filterString);
  }

}
