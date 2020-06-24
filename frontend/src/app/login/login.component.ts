import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { USER_ID_KEY, USER_ROLE_KEY, USERNAME_KEY, USER_TOKEN_KEY } from 'src/app/config/local-storage-keys';
import { ROLE_ADMIN, ROLE_KORISNIK, ROLE_AGENT } from 'src/app/config/user-roles-keys';
import { ADMIN_HOME_PATH, KORISNIK_HOME_PATH } from 'src/app/config/router-paths';
import LoginDTO from '../model/login-dto.model';
import { HOME_PATH } from 'src/app/config/router-paths';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  loginSuccess: boolean = false;
  loginError: boolean = false;
  toastr: any;
  
  constructor(private fb: FormBuilder,
    private router: Router,
    private authService: AuthService) {

this.createForm();
}

  ngOnInit(): void {
  }

  private createForm(): void {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onLogin(): void {
    const credentials: LoginDTO = {
      username: this.loginForm.value.username,
      password: this.loginForm.value.password
    };

    this.authService.login(credentials).subscribe(data => {
      console.log("DATA: ", data);
      localStorage.setItem(USER_ID_KEY, data.id);
      localStorage.setItem(USER_ROLE_KEY, data.authorities[0]);
      localStorage.setItem(USERNAME_KEY, data.username);
      localStorage.setItem(USER_TOKEN_KEY, data.token.accessToken);

      this.loginSuccess = true;
      this.loginError = false;

      switch (data.authorities[0]) {
        case ROLE_ADMIN:  this.router.navigate([ADMIN_HOME_PATH]); break;
        case ROLE_KORISNIK: this.router.navigate([KORISNIK_HOME_PATH]); break;
      }
    }, error => {
      this.loginSuccess = false;
      this.loginError = true;
      this.toastr.error(error.error.message);
    });
  }

  isUserLoggedIn(): boolean {
    return this.authService.isUserLoggedIn();
  }

  onClickLogout(): void {
    this.authService.logout();
    this.router.navigate([HOME_PATH]);
  }

}
