import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import {LOCALSTORAGE_KEY_TOKEN} from '../../../constants';

@Injectable()
export class TokenInterceptorService implements HttpInterceptor {
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    request = request.clone({
      setHeaders: {
        Authorization: localStorage.getItem(LOCALSTORAGE_KEY_TOKEN)
      }
    });
    return next.handle(request);
  }
}
