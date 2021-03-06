import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Observable} from 'rxjs';
import {User} from '../models/user';
import {TestVector} from '../models/test-vector';
import {LOCALSTORAGE_KEY_TOKEN} from '../../../constants';

@Injectable({
  providedIn: 'root'
})
export class TestVectorsService {
  private readonly endpoint: string;

  constructor(private httpClient: HttpClient) {
    this.endpoint = environment.endpoint;
  }

  findAll(): Observable<Array<TestVector>> {
    return this.httpClient.get<Array<TestVector>>(`${this.endpoint}/test-vectors/`, {
      headers: {
        'Authorization': localStorage.getItem(LOCALSTORAGE_KEY_TOKEN)
      }
    });
  }
}
