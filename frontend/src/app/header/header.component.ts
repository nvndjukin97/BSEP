import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { HOME_PATH, REGISTRATION_PATH } from 'src/app/config/router-paths';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private router: Router,
    private authService: AuthService) { }

  ngOnInit(): void {
  }

  isUserLoggedIn(): boolean {
    return this.authService.isUserLoggedIn();
  }

  onClickLogout(): void {
    this.authService.logout();
    this.router.navigate([HOME_PATH]);
  }



}
