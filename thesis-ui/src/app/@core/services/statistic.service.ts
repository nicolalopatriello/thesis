import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Observable} from 'rxjs';
import {LOCALSTORAGE_KEY_TOKEN} from '../../../constants';
import {Statistic} from '../models/statistic';

@Injectable({
  providedIn: 'root'
})
export class StatisticService {
  private readonly endpoint: string;

  constructor(private httpClient: HttpClient) {
    this.endpoint = environment.endpoint;
  }

  find(): Observable<Statistic> {
    return this.httpClient.get<Statistic>(`${this.endpoint}/statistic/`, {
      headers: {
        'Authorization': localStorage.getItem(LOCALSTORAGE_KEY_TOKEN)
      }
    });
  }
}
