import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-document-index',
  templateUrl: './document-index.component.html',
  styleUrls: ['./document-index.component.css']
})
export class DocumentIndexComponent implements OnInit {

  constructor() {}

  ngOnInit() {}

  onSearch(filterString: string) {
    console.log(filterString);
  }

}
