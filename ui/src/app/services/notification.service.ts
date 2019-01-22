import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material';
import { MessagesService } from './messages.service';

@Injectable()
export class NotificationService {

  constructor(private snackBar: MatSnackBar, private messagesService: MessagesService) {}

  pushSuccessNotification(messageKey: string) {
    this.pushNotification(messageKey, 'success-snackbar');
  }

  pushErrorNotification(messageKey: string) {
    this.pushNotification(messageKey, 'error-snackbar');
  }

  private pushNotification(messageKey: string, className: string) {
    const message = this.messagesService.getMessage(messageKey);

    this.snackBar.open(message, 'X', {
      duration: 4000,
      panelClass: [className],
      verticalPosition: 'bottom',
      horizontalPosition: 'right'
    });
  }

}
