package bsep.service.impl;


import bsep.certGenerator.CertificateGenerator;
import bsep.dto.SubjectDTO;
import bsep.model.IssuerData;
import bsep.model.SubjectData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.cert.X509Certificate;

@Service
public class CertificateServiceImpl {

    @Autowired
    private SubjectDataServiceImpl subjectDataService;

    @Autowired
    private IssuerDataServiceImpl issuerDataService;

    @Autowired
    private CertificateGenerator certificateGenerator;

    public void printCetr(X509Certificate certificate){
        System.out.println("Dosao i stao");
        System.out.println("\n===== Podaci o izdavacu sertifikata =====");
        System.out.println(certificate.getIssuerX500Principal().getName());
        System.out.println("\n===== Podaci o vlasniku sertifikata =====");
        System.out.println(certificate.getSubjectX500Principal().getName());
        System.out.println("\n===== Sertifikat =====");
        System.out.println("-------------------------------------------------------");
        System.out.println(certificate);
        System.out.println("-------------------------------------------------------");
    }

    public String create(){

        KeyPair keyPairIssuer = subjectDataService.generateKeyPair();
        IssuerData issuerData = issuerDataService.generateIssuerData(keyPairIssuer.getPrivate());
        //Moracu menjati da ne bude hardkodovan subjectData!
        SubjectData subjectData = subjectDataService.generateSubjectData();


        //Generise se sertifikat za subjekta, potpisan od strane issuer-a
        //CertificateGenerator cg = new CertificateGenerator();

        X509Certificate cert = certificateGenerator.generateCertificate(subjectData, issuerData);

        printCetr(cert);

        return "Successfully created certificate";

    }


}
