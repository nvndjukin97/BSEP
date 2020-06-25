import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {LoginComponent} from '../app/login/login.component';
import {CertFormComponent} from '../app/cert-form/cert-form.component';
import { ADMIN_HOME_PATH} from './config/router-paths';


const routes: Routes = [
  {
    path: ADMIN_HOME_PATH,
    component: CertFormComponent
  },
  { path: 'login', component: LoginComponent },
  { path: 'login/cert', component: CertFormComponent},
  { path: 'certForm', component: CertFormComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
