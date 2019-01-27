import { NgModule } from '@angular/core';
import {
  MatAutocompleteModule,
  MatButtonModule, MatCardModule,
  MatDialogModule, MatFormFieldModule,
  MatIconModule, MatInputModule,
  MatMenuModule, MatProgressBarModule,
  MatProgressSpinnerModule,
  MatSelectModule, MatSlideToggleModule,
  MatSnackBarModule, MatToolbarModule,
  MatTooltipModule
} from '@angular/material';
import { NgxMasonryModule } from 'ngx-masonry';
import { WaMatConfirmDialogModule } from '@webacad/material-confirm-dialog';

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
    MatAutocompleteModule,
    MatSlideToggleModule,
    MatSelectModule,
    MatTooltipModule,
    NgxMasonryModule,
    WaMatConfirmDialogModule
  ]
})
export class MaterialModule {}
