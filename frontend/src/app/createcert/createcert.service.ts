import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {SubjectDTO}  from '../model/subject-dto.model';
import { Observable } from 'rxjs';
import { User } from '../services/user';


@Injectable({
    providedIn: 'root'
})
export class CreateCertService{
    _url = 'http://localhost:8099/api/certificate/createCert';
    
   

    constructor(private _http: HttpClient) { }

    createCert(certificateType: string, id: number, subjectDTO: SubjectDTO):Observable<any>{
        return this._http.post<String>(`${this._url}/${certificateType}/${id}`, subjectDTO);
    }
    
}