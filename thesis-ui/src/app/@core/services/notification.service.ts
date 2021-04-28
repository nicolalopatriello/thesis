import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Observable} from 'rxjs';
import {User} from '../models/user';
import {TestVector} from '../models/test-vector';
import {LOCALSTORAGE_KEY_TOKEN} from '../../../constants';
import {Notification} from '../models/notification';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private readonly endpoint: string;

  constructor(private httpClient: HttpClient) {
    this.endpoint = environment.endpoint;
  }

  findAll(): Observable<Array<Notification>> {
    return this.httpClient.get<Array<Notification>>(`${this.endpoint}/notification/`, {
      headers: {
        'Authorization': localStorage.getItem(LOCALSTORAGE_KEY_TOKEN)
      }
    });
  }
}
