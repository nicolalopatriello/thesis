import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Observable} from 'rxjs';
import {User} from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly endpoint: string;

  constructor(private httpClient: HttpClient) {
    this.endpoint = environment.endpoint;
  }

  login(user: {username: string, password: string}): Observable<User> {
    return this.httpClient.post<User>(`${this.endpoint}/login`, {username: user.username, password: user.password});
  }
}
