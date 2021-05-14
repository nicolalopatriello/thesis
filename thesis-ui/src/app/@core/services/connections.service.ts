import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Observable} from 'rxjs';
import {User} from '../models/user';
import {TestVector} from '../models/test-vector';
import {LOCALSTORAGE_KEY_TOKEN} from '../../../constants';
import {Gitrace} from '../models/gitrace';
import {Connection} from '../models/connection';

@Injectable({
  providedIn: 'root'
})
export class ConnectionsService {
  private readonly endpoint: string;

  constructor(private httpClient: HttpClient) {
    this.endpoint = environment.endpoint;
  }

  findAll(): Observable<Array<Connection>> {
    return this.httpClient.get<Array<Connection>>(`${this.endpoint}/connection/`, {
      headers: {
        'Authorization': localStorage.getItem(LOCALSTORAGE_KEY_TOKEN)
      }
    });
  }

  create(connection: Connection): Observable<Connection> {
    return this.httpClient.post<Connection>(`${this.endpoint}/connection/`, connection, {
      headers: {
        'Authorization': localStorage.getItem(LOCALSTORAGE_KEY_TOKEN)
      }
    });
  }
}
