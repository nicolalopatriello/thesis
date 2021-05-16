import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../@core/services/auth.service';
import {LOCALSTORAGE_KEY_TOKEN} from '../../constants';
import {Router} from '@angular/router';

@Component({
  selector: 'ngx-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {

  public loginFormGroup: FormGroup;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.loginFormGroup = new FormGroup({
      username: new FormControl(null, [Validators.required]),
      password: new FormControl(null, [Validators.required]),
    });
  }

  login() {
    this.authService.login(this.loginFormGroup.getRawValue()).subscribe(t => {
      localStorage.setItem(LOCALSTORAGE_KEY_TOKEN, t.token);
      this.loginFormGroup.reset();
      this.router.navigate(['pages', 'dashboard']);
    });
  }
}
