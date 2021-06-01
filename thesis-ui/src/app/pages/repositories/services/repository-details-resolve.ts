import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {RepositoryDetails} from '../../../@core/models/repository-details';
import {RepositoryService} from '../../../@core/services/repository.service';

@Injectable({
  providedIn: 'root'
})

export class RepositoryDetailsResolve implements Resolve<RepositoryDetails> {
  constructor(private repositoryService: RepositoryService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<RepositoryDetails> | Promise<RepositoryDetails> | RepositoryDetails {
    return this.repositoryService.findById(Number(route.paramMap.get('repositoryId')));
  }
}
