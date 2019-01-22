import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  tiles = [
    {
      titleKey: 'COMPANIES',
      descriptionKey: 'COMPANIES_DESCRIPTION',
      routerLink: '/companies'
    },
    {
      titleKey: 'TEMPLATES',
      descriptionKey: 'TEMPLATES_DESCRIPTION',
      routerLink: '/templates'
    },
    {
      titleKey: 'DOCUMENTS',
      descriptionKey: 'DOCUMENTS_DESCRIPTION',
      routerLink: '/documents'
    }
  ];

  constructor() {}

  ngOnInit() {}

}
