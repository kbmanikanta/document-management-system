import { NgModule } from '@angular/core';
import {
  MatButtonModule, MatCardModule,
  MatDialogModule, MatFormFieldModule,
  MatIconModule, MatInputModule,
  MatMenuModule, MatProgressBarModule,
  MatProgressSpinnerModule,
  MatSnackBarModule, MatToolbarModule
} from '@angular/material';
import { NgxMasonryModule } from 'ngx-masonry';

@NgModule({
  exports: [
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatMenuModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    MatDialogModule,
    NgxMasonryModule,
  ]
})
export class AppStylingModule {}
