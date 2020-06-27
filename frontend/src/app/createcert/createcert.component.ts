import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import {SubjectDTO} from '../model/subject-dto.model';
import {CreateCertService} from '../createcert/createcert.service';
import {UserService} from '../services/user.service';
import { User } from '../services/user';
import { USER_ID_KEY, USER_ROLE_KEY, USERNAME_KEY, USER_TOKEN_KEY } from './../config/local-storage-keys';

@Component({
  selector: 'app-createcert',
  templateUrl: './createcert.component.html',
  styleUrls: ['./createcert.component.css']
})
export class CreatecertComponent implements OnInit {
  certificateType: string;
  subjectDTO = new SubjectDTO();
  user = new User(0, null,null,null,null,false,0,null);
  constructor(private _createCertService: CreateCertService , private _userService: UserService) { }

  ngOnInit(): void {
  }

  onSubmit() {
    var userId = parseInt(localStorage.getItem(USER_ID_KEY));
    console.log(userId + "ULOGOVANI saaam *****");
    console.log(this.subjectDTO);
    this._createCertService.createCert(this.certificateType, userId, this.subjectDTO).subscribe(
      (response) => {
        //alert(response);
    }, error => {
      // alert("Greskaa");
    }
  
      
    )
  }
}
