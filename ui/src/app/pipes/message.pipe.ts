import { Pipe, PipeTransform } from '@angular/core';
import { MessagesService } from '../services/messages.service';

@Pipe({
  name: 'message'
})
export class MessagePipe implements PipeTransform {

  constructor(private messagesService: MessagesService) {}

  transform(value: string) {
    return this.messagesService.getMessage(value);
  }

}
