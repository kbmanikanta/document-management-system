import { Injectable } from '@angular/core';
import { MessagesService } from './messages.service';
import { WaMatConfirmDialog } from '@webacad/material-confirm-dialog';

@Injectable()
export class ConfirmDialogService {

  private yes = this.messagesService.getMessage('YES');
  private no = this.messagesService.getMessage('NO');

  constructor(private messagesService: MessagesService,
              private confirmDialog: WaMatConfirmDialog) {}

  openDialog(messageKey: string) {
    const question = this.messagesService.getMessage(messageKey);

    return this.confirmDialog.open(question, {
      trueButtonTitle: this.yes,
      falseButtonTitle: this.no
    }).afterClosed();
  }
}
