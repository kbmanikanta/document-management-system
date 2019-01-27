import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material';
import { MessagesService } from './messages.service';

@Injectable()
export class NotificationService {

  constructor(private snackBar: MatSnackBar, private messagesService: MessagesService) {}

  pushSuccess(messageKey: string) {
    this.push(messageKey, 'success-snackbar');
  }

  pushError(messageKey: string) {
    this.push(messageKey, 'error-snackbar');
  }

  private push(messageKey: string, className: string) {
    const message = this.messagesService.getMessage(messageKey);

    this.snackBar.open(message, 'X', {
      duration: 10000,
      panelClass: [className],
      verticalPosition: 'bottom',
      horizontalPosition: 'right'
    });
  }

}
