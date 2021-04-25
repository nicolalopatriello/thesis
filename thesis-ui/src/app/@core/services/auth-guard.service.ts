import { Injectable } from '@angular/core';
import {CanActivate, Router, UrlTree} from '@angular/router';
import {Observable, of} from 'rxjs';
import { map } from 'rxjs/operators';
import {LOCALSTORAGE_KEY_TOKEN} from '../../../constants';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {
  constructor(private router: Router) {
  }

  canActivate(): Observable<boolean | UrlTree> {
    const token = localStorage.getItem(LOCALSTORAGE_KEY_TOKEN);
    if (token !== null) {
      return of(true);
    } else {
      this.router.navigate(['auth', 'login']);
      return of(false);
    }
  }
}
