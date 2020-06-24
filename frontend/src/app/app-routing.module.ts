import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {LoginComponent} from '../app/login/login.component';
import {CertFormComponent} from '../app/cert-form/cert-form.component'

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'certForm', component: CertFormComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
