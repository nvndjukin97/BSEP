import { Component, OnInit } from '@angular/core';
import {CertificateDTO} from '../model/certificate-dto.model';
import {ShowCertificates} from '../showcert/showcert.service';


@Component({
  selector: 'app-showcert',
  templateUrl: './showcert.component.html',
  styleUrls: ['./showcert.component.css']
})
export class ShowcertComponent implements OnInit {

  public certificates : CertificateDTO[];
 
  constructor(private _showCertificateService: ShowCertificates) { }

  ngOnInit(): void {
    this._showCertificateService.findAllCert().subscribe(
      data=>{ 
       
        console.log('ziv samm')
          this.certificates = data;
      },
      error=> console.error('Error!', error)
    )
  }

  onClickRevoke(alias): void{
    this._showCertificateService.revokeCertificate(alias).subscribe();
  }

  

}
