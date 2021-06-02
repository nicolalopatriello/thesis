import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Observable} from 'rxjs';
import {LOCALSTORAGE_KEY_TOKEN} from '../../../constants';
import {RepositoryLight} from '../models/repository-light';
import {RepositoryDetails} from '../models/repository-details';
import {map} from 'rxjs/operators';
import {RepositoryCreateRequest} from '../models/repository-create-request';
import {RepositoryCreateResponse} from '../models/repository-create-response';

@Injectable({
  providedIn: 'root'
})
export class RepositoryService {
  private readonly endpoint: string;

  constructor(private httpClient: HttpClient) {
    this.endpoint = environment.endpoint;
  }

  create(request: RepositoryCreateRequest): Observable<RepositoryCreateResponse> {
    return this.httpClient.post<RepositoryCreateResponse>(`${this.endpoint}/repository/`, request, {
      headers: {
        'Authorization': localStorage.getItem(LOCALSTORAGE_KEY_TOKEN)
      }
    });
  }

  delete(repositoryId: number): Observable<any> {
    return this.httpClient.delete<any>(`${this.endpoint}/repository/${repositoryId}/`, {
      headers: {
        'Authorization': localStorage.getItem(LOCALSTORAGE_KEY_TOKEN)
      }
    });
  }

  findAll(): Observable<Array<RepositoryLight>> {
    return this.httpClient.get<Array<RepositoryLight>>(`${this.endpoint}/repository/`, {
      headers: {
        'Authorization': localStorage.getItem(LOCALSTORAGE_KEY_TOKEN)
      }
    });
  }

  findById(repositoryId: number): Observable<RepositoryDetails> {
    return this.httpClient.get<RepositoryDetails>(`${this.endpoint}/repository/${repositoryId}/`, {
      headers: {
        'Authorization': localStorage.getItem(LOCALSTORAGE_KEY_TOKEN)
      }
    }).pipe(
      map(resp => {
        return {
          ...resp,
          dependencies: resp.dependencies.sort((a, b) => a.vulnerabilities.length > b.vulnerabilities.length ? -1 : 1)
        };
      })
    );
  }

}
