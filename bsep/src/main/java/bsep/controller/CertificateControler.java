package bsep.controller;

import bsep.dto.CertificateDTO;
import bsep.dto.SubjectDTO;
import bsep.service.impl.CertificateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.util.locale.LocaleMatcher;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.text.ParseException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/certificate")
public class CertificateControler {

    @Autowired
    private final CertificateServiceImpl certificateService;

    public CertificateControler(CertificateServiceImpl certificateService) {
        this.certificateService = certificateService;
    }

    /*@PostMapping( value = "/create")
    public ResponseEntity<String>create(@RequestBody SubjectDTO subjectDTO) throws ParseException {
        return new ResponseEntity<>(certificateService.create(subjectDTO), HttpStatus.OK);
    }*/


    @PostMapping(value = "/createCert/{certificateType}/{id}")
    public ResponseEntity<String>createCert(@RequestBody SubjectDTO subjectDTO, @PathVariable String certificateType, @PathVariable Long id) throws CertificateException, ParseException, NoSuchAlgorithmException, KeyStoreException, NoSuchProviderException, IOException {
        return new ResponseEntity<>(certificateService.creatCert(subjectDTO,certificateType,id),HttpStatus.OK);
    }

    @PostMapping(value = "/revokeCertificate/{alias}")
    public ResponseEntity<String>revokeCertificate(@PathVariable String alias){
        return new ResponseEntity<>(certificateService.revokeCertificate(alias),HttpStatus.OK);
    }

    @GetMapping(value = "/getAllCertificates")
    public  ResponseEntity<List<CertificateDTO>> findAllCert() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, ParseException {
        return new ResponseEntity<List<CertificateDTO>>(certificateService.findAllCert(),HttpStatus.OK);
    }


}
