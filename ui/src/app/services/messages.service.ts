import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class MessagesService {

  messages: any;

  constructor(private http: HttpClient) {}

  init() {
    return new Promise((resolve, reject) => {
      this.http.get('/assets/messages.json')
        .subscribe((messages: any) => {
          this.messages = messages;
          resolve();
        }, (error: any) => {
          reject(error);
        });
    });
  }

  getMessage(key: string) {
    return this.messages[key] || key;
  }

}

export function messagesServiceFactory(messagesService: MessagesService) {
  return () => messagesService.init();
}

