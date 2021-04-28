import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Observable} from 'rxjs';
import {User} from '../models/user';
import {TestVector} from '../models/test-vector';
import {LOCALSTORAGE_KEY_TOKEN} from '../../../constants';
import {Gitrace} from '../models/gitrace';
import {UserTest} from '../models/user-test';
import {UserTestWithDeps} from '../models/user-test-with-deps';

@Injectable({
  providedIn: 'root'
})
export class UserTestService {
  private readonly endpoint: string;

  constructor(private httpClient: HttpClient) {
    this.endpoint = environment.endpoint;
  }

  findAll(): Observable<Array<UserTest>> {
    return this.httpClient.get<Array<UserTest>>(`${this.endpoint}/user-test/`, {
      headers: {
        'Authorization': localStorage.getItem(LOCALSTORAGE_KEY_TOKEN)
      }
    });
  }

  findById(id: number): Observable<UserTestWithDeps> {
    return this.httpClient.get<UserTestWithDeps>(`${this.endpoint}/user-test/${id}/`, {
      headers: {
        'Authorization': localStorage.getItem(LOCALSTORAGE_KEY_TOKEN)
      }
    });
  }

  create(userTest: UserTest): Observable<UserTest> {
    return this.httpClient.post<UserTest>(`${this.endpoint}/user-test/`, userTest, {
      headers: {
        'Authorization': localStorage.getItem(LOCALSTORAGE_KEY_TOKEN)
      }
    });
  }
}
