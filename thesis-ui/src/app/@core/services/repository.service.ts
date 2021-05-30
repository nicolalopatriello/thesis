import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Observable} from 'rxjs';
import {User} from '../models/user';
import {TestVector} from '../models/test-vector';
import {LOCALSTORAGE_KEY_TOKEN} from '../../../constants';
import {Gitrace} from '../models/gitrace';

@Injectable({
  providedIn: 'root'
})
export class RepositoryService {
  private readonly endpoint: string;

  constructor(private httpClient: HttpClient) {
    this.endpoint = environment.endpoint;
  }

  findAll(): Observable<Array<Gitrace>> {
    return this.httpClient.get<Array<Gitrace>>(`${this.endpoint}/repository/`, {
      headers: {
        'Authorization': localStorage.getItem(LOCALSTORAGE_KEY_TOKEN)
      }
    });
  }

  create(gitrace: Gitrace): Observable<Gitrace> {
    return this.httpClient.post<Gitrace>(`${this.endpoint}/repository/`, gitrace, {
      headers: {
        'Authorization': localStorage.getItem(LOCALSTORAGE_KEY_TOKEN)
      }
    });
  }
}
