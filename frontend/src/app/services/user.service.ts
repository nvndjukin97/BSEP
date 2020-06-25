import { VERIFY_ACC_URL, REGISTER_URL, LOGGED_USER} from './../config/api-paths'; //ADMIN_ALL_USERS_URL, ADMIN_ALL_KORISNIKE_URL, ADMIN_DELETE_KORISNIKA_URL, ADMIN_ACTIVATE_KORISNIKA_URL, ADMIN_ALL_COMMENTS_URL, ADMIN_ACTIVATE_COMMENT_KORISNIKA_URL } from './../config/api-paths';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
//import UserRegistrationDTO from '../models/user-registration-dto.model';
import { User } from './user';


@Injectable({
  providedIn: 'root'
})
export class UserService {
  _url = 'http://localhost:8080/api/users/getLoggedIn';

  constructor(private http: HttpClient) { 
  }

  verifyAccount(token: string): Observable<any> {
    return this.http.get(`${VERIFY_ACC_URL}/${token}`);
  }

  getLoggedUser(): Observable<any> {
    //return this.http.get<User>(this._url);
    return this.http.get(`${LOGGED_USER}`);
  }
  ulogovani(): Observable<any>{
    return this.http.get(`${LOGGED_USER}`);
  }

  /*register(user: UserRegistrationDTO): Observable<any> {
    console.log(user);
    return this.http.post(REGISTER_URL, user);
  }

  getAll(): Observable<any> {
    return this.http.get(ADMIN_ALL_USERS_URL);
  }

  getAllKorisnike(): Observable<any> {
    return this.http.get(ADMIN_ALL_KORISNIKE_URL);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${ADMIN_DELETE_KORISNIKA_URL}/${id}`);
  }

  activate(id: number): Observable<any> {
    return this.http.get(`${ADMIN_ACTIVATE_KORISNIKA_URL}/${id}`);
  }

  getAllComments(): Observable<any> {
    return this.http.get(ADMIN_ALL_COMMENTS_URL);
  }

  activateComment(id: number): Observable<any> {
    return this.http.get(`${ADMIN_ACTIVATE_COMMENT_KORISNIKA_URL}/${id}`);
  }*/

}
