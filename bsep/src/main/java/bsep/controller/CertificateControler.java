package bsep.controller;

import bsep.service.impl.CertificateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/certificate")
public class CertificateControler {

    @Autowired
    private final CertificateServiceImpl certificateService;

    public CertificateControler(CertificateServiceImpl certificateService) {
        this.certificateService = certificateService;
    }

    @PostMapping( value = "/create")
    public ResponseEntity<String>create(){
        return new ResponseEntity<>(certificateService.create(), HttpStatus.OK);
    }
}
