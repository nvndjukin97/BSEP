import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import {SubjectDTO} from '../model/subject-dto.model';
import {CreateCertService} from '../createcert/createcert.service';
import {UserService} from '../services/user.service';
import { User } from '../services/user';

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
    console.log(this.subjectDTO);
    this._userService.ulogovani().subscribe(
      data=>{ 
       
        console.log("ID moj" + data);
        this.user = data;
        console.log(this.user+"jajajjajajajjjaj")
      },
      error=> console.error('Error!', error)
  )
  //   this._klasaServis.dodajKlasu(this.klasa)
  //  .subscribe(
  //      data=>{
  //       console.log('Success!', JSON.stringify(data))
  //       alert('Nova klasa automobila dodata!');
  //       this.router.navigate(['admin']);
  //      } ,
  //       error=> console.error('Error!',error)
  //   )
  }
}
