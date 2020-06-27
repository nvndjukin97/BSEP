import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {SubjectDTO}  from '../model/subject-dto.model';
import { Observable } from 'rxjs';
import { User } from '../services/user';
import { CertificateDTO } from '../model/certificate-dto.model';


@Injectable({
    providedIn: 'root'
})
export class ShowCertificates{
    _url = 'http://localhost:8080/api/certificate/getAllCertificates';
    _url2 = 'http://localhost:8080/api/certificate/revokeCertificate';
    _url3 = 'http://localhost:8080/api/certificate/downloadCertificate';
    
   

    constructor(private _http: HttpClient) { }

    findAllCert():Observable<any>{
        return this._http.get<CertificateDTO[]>(`${this._url}`);
    }

    revokeCertificate(alias: string): Observable<any>{
        return this._http.post<string>(`${this._url2}/${alias}`,null);
    }

    downloadCertificate(certificateDTO:CertificateDTO): Observable<any>{
        return this._http.post(`${this._url3}`, certificateDTO);
    }

    
}