import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Observable} from 'rxjs';
import {User} from '../models/user';
import {LOCALSTORAGE_KEY_TOKEN} from '../../../constants';

@Injectable({
  providedIn: 'root'
})
export class UsersService {
  private readonly endpoint: string;

  constructor(private httpClient: HttpClient) {
    this.endpoint = environment.endpoint;
  }

  self(): Observable<User> {
    return this.httpClient.get<User>(`${this.endpoint}/users/self/`, {
      headers: {
        'Authorization': localStorage.getItem(LOCALSTORAGE_KEY_TOKEN)
      }
    });
  }
}
