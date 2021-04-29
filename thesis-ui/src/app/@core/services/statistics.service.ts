import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Observable} from 'rxjs';
import {LOCALSTORAGE_KEY_TOKEN} from '../../../constants';
import {Statistics} from '../models/statistics';

@Injectable({
  providedIn: 'root'
})
export class StatisticsService {
  private readonly endpoint: string;

  constructor(private httpClient: HttpClient) {
    this.endpoint = environment.endpoint;
  }

  find(): Observable<Statistics> {
    return this.httpClient.get<Statistics>(`${this.endpoint}/statistics/`, {
      headers: {
        'Authorization': localStorage.getItem(LOCALSTORAGE_KEY_TOKEN)
      }
    });
  }
}
