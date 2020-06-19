package bsep.service.impl;


import bsep.certGenerator.CertificateGenerator;
import bsep.dto.SubjectDTO;
import bsep.keyStore.KeyStoreReader;
import bsep.keyStore.KeyStoreWriter;
import bsep.model.IssuerData;
import bsep.model.SubjectData;
import bsep.model.User;
import bsep.repository.CertificateRepository;
import bsep.repository.UserRepository;
import bsep.service.CertificateService;
import bsep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.util.Optional;
import java.util.UUID;

@Service
public class CertificateServiceImpl implements CertificateService {

    @Autowired
    private SubjectDataServiceImpl subjectDataService;

    @Autowired
    private IssuerDataServiceImpl issuerDataService;

    @Autowired
    private CertificateGenerator certificateGenerator;

    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private KeyStoreWriter keyStoreWriter;

    @Autowired
    private KeyStoreReader keyStoreReader;

    public void printCetr(X509Certificate certificate) {
        System.out.println("\n===== Podaci o izdavacu sertifikata =====");
        System.out.println(certificate.getIssuerX500Principal().getName());
        System.out.println("\n===== Podaci o vlasniku sertifikata =====");
        System.out.println(certificate.getSubjectX500Principal().getName());
        System.out.println("\n===== Sertifikat =====");
        System.out.println("-------------------------------------------------------");
        System.out.println(certificate);
        System.out.println("-------------------------------------------------------");
    }

    public String create(SubjectDTO subjectDTO) throws ParseException {

        KeyPair keyPairIssuer = subjectDataService.generateKeyPair();
        //IssuerData issuerData = issuerDataService.generateIssuerData(keyPairIssuer.getPrivate(), subjectDTO);
        //Moracu menjati da ne bude hardkodovan subjectData!
        SubjectData subjectData = subjectDataService.generateSubjectData(subjectDTO);


        //Generise se sertifikat za subjekta, potpisan od strane issuer-a
        //CertificateGenerator cg = new CertificateGenerator();

        //X509Certificate cert = certificateGenerator.generateCertificate(subjectData, issuerData);

        //printCetr(cert);

        return "Successfully created certificate";

    }

    public String creatCert(SubjectDTO subjectDTO, String certificateType) throws ParseException, NoSuchAlgorithmException, CertificateException, NoSuchProviderException, KeyStoreException, IOException {
        KeyPair keyPairIssuer = subjectDataService.generateKeyPair();
        //IssuerData issuerData = issuerDataService.generateIssuerData(keyPairIssuer.getPrivate(), subjectDTO);
        //Moracu menjati da ne bude hardkodovan subjectData!
        SubjectData subjectData = subjectDataService.generateSubjectData(subjectDTO);
        if (certificateType.equals("self-signed")) {
            String password = "self-signed";


            String alias = UUID.randomUUID().toString();
            User issuer = userRepository.findById(Long.valueOf(1)).get();
            IssuerData issuerData = issuerDataService.generateIssuerData(keyPairIssuer.getPrivate(), issuer);
            X509Certificate cert = certificateGenerator.generateCertificate(subjectData, issuerData);
            System.out.println(issuer.getFirstName());
            printCetr(cert);

            KeyStore ks = KeyStore.getInstance("JKS");
            keyStoreWriter.loadKeyStore(null, password.toCharArray());
            keyStoreWriter.write(alias, subjectData.getPrivateKey(), password.toCharArray(), cert, "self-signedCertificate.jks");

            bsep.model.Certificate certSelfSigned = new bsep.model.Certificate(alias, false);
            certificateRepository.save(certSelfSigned);

            return "Successfully created self-signed certificate";
        } else if (certificateType.equals("intermediat")) {

            KeyStore keyStore = KeyStore.getInstance("JKS");
            String password = "intermediat";
            String alias = UUID.randomUUID().toString();
            User issuer = userRepository.findById(Long.valueOf(1)).get();
            IssuerData issuerData = issuerDataService.generateIssuerData(keyPairIssuer.getPrivate(), issuer);
            X509Certificate cert = certificateGenerator.generateCertificate(subjectData, issuerData);
            System.out.println(issuer.getFirstName());
            printCetr(cert);

            KeyStore ks = KeyStore.getInstance("JKS");
            keyStoreWriter.loadKeyStore(null, password.toCharArray());
            keyStoreWriter.write(alias, subjectData.getPrivateKey(), password.toCharArray(), cert, "intemediatCertificate.jks");



            keyStoreWriter.loadKeyStore("intemediatCertificate.jks", password.toCharArray());
            keyStoreWriter.write(alias,subjectData.getPrivateKey(),password.toCharArray(),cert);

            bsep.model.Certificate certSelfSigned = new bsep.model.Certificate(alias, false);
            certificateRepository.save(certSelfSigned);

            return "Successfully created intermediat certificate";
        } else if (certificateType.equals("end-entity")) {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            String password = "end-entity";
            String alias = UUID.randomUUID().toString();
            User issuer = userRepository.findById(Long.valueOf(2)).get();
            IssuerData issuerData = issuerDataService.generateIssuerData(keyPairIssuer.getPrivate(), issuer);
            X509Certificate cert = certificateGenerator.generateCertificate(subjectData, issuerData);
            System.out.println(issuer.getFirstName());
            printCetr(cert);

            KeyStore ks = KeyStore.getInstance("JKS");
            keyStoreWriter.loadKeyStore(null, password.toCharArray());
            keyStoreWriter.write(alias, subjectData.getPrivateKey(), password.toCharArray(), cert, "end-entityCertificate.jks");


            keyStoreWriter.loadKeyStore("end-entityCertificate.jks", password.toCharArray());
            keyStoreWriter.write(alias,subjectData.getPrivateKey(),password.toCharArray(),cert);

            bsep.model.Certificate certSelfSigned = new bsep.model.Certificate(alias, false);
            certificateRepository.save(certSelfSigned);

            return "Successfully created end-entity certificate";
        }
        return "Successfully created certificate";
    }


}