package bsep.controller;

import bsep.dto.CertificateDTO;
import bsep.dto.SubjectDTO;
import bsep.service.impl.CertificateServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.text.ParseException;
import java.util.List;

@Slf4j
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
    public ResponseEntity<String>revokeCertificate(@PathVariable String alias) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        return new ResponseEntity<>(certificateService.revokeCertificate(alias),HttpStatus.OK);
    }

    @GetMapping(value = "/getAllCertificates")
    public  ResponseEntity<List<CertificateDTO>> findAllCert() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, ParseException {
        return new ResponseEntity<List<CertificateDTO>>(certificateService.findAllCert(),HttpStatus.OK);
    }

    @GetMapping(value = "/revokeExpiredCertificate")
    public ResponseEntity<String>revokeExpiredCertificate() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, ParseException, IOException {
        return new ResponseEntity<>(certificateService.revokeExpiredCertificate(),HttpStatus.OK);
    }

    @PostMapping(value = "/downloadCertificate")
    public ResponseEntity downloadCertificate(CertificateDTO certificateDTO) throws FileNotFoundException {
        //https://stackoverflow.com/questions/35680932/download-a-file-from-spring-boot-rest-service
        String ime = certificateDTO.getAlias()+".CER";
        File file = new File(ime);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        log.info("Radim download");
        return ResponseEntity.ok().headers(headers).contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }


}
