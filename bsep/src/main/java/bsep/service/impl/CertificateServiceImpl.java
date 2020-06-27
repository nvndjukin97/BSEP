package bsep.service.impl;


import bsep.certGenerator.CertificateGenerator;
import bsep.dto.CertificateDTO;
import bsep.dto.SubjectDTO;
import bsep.keyStore.KeyStoreReader;
import bsep.keyStore.KeyStoreWriter;
import bsep.model.Certificate;
import bsep.model.IssuerData;
import bsep.model.SubjectData;
import bsep.model.User;
import bsep.repository.CertificateRepository;
import bsep.repository.UserRepository;
import bsep.service.CertificateService;
import bsep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.time.LocalDate.now;
import static org.aspectj.runtime.internal.Conversions.longValue;

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

    public String creatCert(SubjectDTO subjectDTO, String certificateType, Long id) throws ParseException, NoSuchAlgorithmException, CertificateException, NoSuchProviderException, KeyStoreException, IOException {
        KeyPair keyPairIssuer = subjectDataService.generateKeyPair();
        //IssuerData issuerData = issuerDataService.generateIssuerData(keyPairIssuer.getPrivate(), subjectDTO);
        //Moracu menjati da ne bude hardkodovan subjectData!
        SubjectData subjectData = subjectDataService.generateSubjectData(subjectDTO);
        User ulogovan = userRepository.findById(id).get();
        Long ulogovanId = ulogovan.getId();
        System.out.println(ulogovanId+"******ULOGOVANI ID");
        if (certificateType.equals("self-signed") && ulogovan.getIsCa() ) {
            String password = "self-signed";


            String alias = UUID.randomUUID().toString();
            User issuer = userRepository.findById(id).get();
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

        } else if (certificateType.equals("intermediat") && !ulogovan.getIsCa() || ulogovanId.equals(longValue(1)) && certificateType.equals("intermediat")) {

            KeyStore keyStore = KeyStore.getInstance("JKS");
            String password = "intermediat";
            String alias = UUID.randomUUID().toString();
            User issuer = userRepository.findById(id).get();
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
        } else if (certificateType.equals("end-entity") && !ulogovan.getIsCa() || ulogovanId.equals(longValue(1)) && certificateType.equals("end-entity")) {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            String password = "end-entity";
            String alias = UUID.randomUUID().toString();
            User issuer = userRepository.findById(id).get();
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
        return "Unsuccessfully created certificate";
    }

    public String revokeCertificate(String alias) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        Certificate cert  = certificateRepository.findByAlias(alias);
        cert.setRevoked(true);
        certificateRepository.save(cert);
        //TO-DO povlace se i svi sertifikati koje je potpisao povuceni
        KeyStore ks = KeyStore.getInstance("JKS");
        KeyStore ks1 = KeyStore.getInstance("JKS");
        KeyStore ks2 = KeyStore.getInstance("JKS");

        BufferedInputStream in = new BufferedInputStream(new FileInputStream("self-signedCertificate.jks"));
        String password = "self-signed";
        ks.load(in, password.toCharArray());

        BufferedInputStream in1 = new BufferedInputStream(new FileInputStream("intemediatCertificate.jks"));
        String password1 = "intermediat";
        ks1.load(in1, password1.toCharArray());

        BufferedInputStream in2 = new BufferedInputStream(new FileInputStream("end-entityCertificate.jks"));
        String password2 = "end-entity";
        ks2.load(in2, password2.toCharArray());

        if(ks.containsAlias(alias)){
            X509Certificate cert1 = (X509Certificate) ks.getCertificate(alias);
            povlaciDecu(alias, cert1);
        }else if (ks1.containsAlias(alias)){
            X509Certificate cert2 = (X509Certificate) ks1.getCertificate(alias);
            povlaciDecu(alias, cert2);
        }else if (ks2.containsAlias(alias)){
            X509Certificate cert3 = (X509Certificate) ks2.getCertificate(alias);
            povlaciDecu(alias, cert3);
        }


        return "Certificate revoked";
    }

    public List<CertificateDTO> findAllCert() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        KeyStore ks = KeyStore.getInstance("JKS");
        BufferedInputStream in = new BufferedInputStream(new FileInputStream("self-signedCertificate.jks"));
        String password = "self-signed";
        ks.load(in, password.toCharArray());

        List<CertificateDTO> certificateDTOS = new ArrayList<>();
        //trazim iz ks sertifikate po aliasu
        List<String> aliases = Collections.list(ks.aliases());

        try {
            for (String alias : aliases) {
                X509Certificate cert = (X509Certificate) ks.getCertificate(alias);
                String issuedBy = (cert.getIssuerDN().getName()).split(",")[7].split("=")[1]; //Dobila sam cn
                System.out.println(issuedBy);
                //NevenaDjukin
                //String issuedBy = cert.getIssuerDN().getName()
                //UID=196c6cc8-cac9-469d-8d65-7d7275e8b5ae, EMAILADDRESS=nvn@gmail.com, C=Srbija, OU=Elektro, O=FTN, GIVENNAME=Nevena, SURNAME=Djukin, CN=NevenaDjukin
                String issuedTo = cert.getSubjectDN().getName().split(",")[7].split("=")[1];
                System.out.println(issuedTo);
                //String issuedTo = cert.getSubjectDN().getName();
                //UID=9e62437b-bdc6-4d66-b7e6-953b2b73335d, EMAILADDRESS=nvn@gmail.com, C=RS, OU=Elektro, O=FTN, GIVENNAME=Nevena, SURNAME=Djukin, CN=DjukinNevena
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                String pocetak = sd.format(cert.getNotBefore());
                System.out.println(pocetak);
                String kraj = sd.format(cert.getNotAfter());
                System.out.println(kraj);
                CertificateDTO certificateDTO = new CertificateDTO(alias, issuedBy, issuedTo, pocetak, kraj, "self-signed");
                System.out.println(certificateRepository.findByAlias(certificateDTO.getAlias()));
                System.out.println(certificateDTO.getAlias());

                if (!certificateRepository.findByAlias(certificateDTO.getAlias()).getRevoked()) {
                    certificateDTOS.add(certificateDTO);
                }
            }

            BufferedInputStream in1 = new BufferedInputStream(new FileInputStream("intemediatCertificate.jks"));
            String password1 = "intermediat";
            ks.load(in1, password1.toCharArray());
            List<String> aliases1 = Collections.list(ks.aliases());

            for (String alias : aliases1) {
                X509Certificate cert = (X509Certificate) ks.getCertificate(alias);
                String issuedBy = (cert.getIssuerDN().getName()).split(",")[7].split("=")[1]; //Dobila sam cn
                System.out.println(issuedBy);
                //NevenaDjukin
                //String issuedBy = cert.getIssuerDN().getName()
                //UID=196c6cc8-cac9-469d-8d65-7d7275e8b5ae, EMAILADDRESS=nvn@gmail.com, C=Srbija, OU=Elektro, O=FTN, GIVENNAME=Nevena, SURNAME=Djukin, CN=NevenaDjukin
                String issuedTo = cert.getSubjectDN().getName().split(",")[7].split("=")[1];
                System.out.println(issuedTo);
                //String issuedTo = cert.getSubjectDN().getName();
                //UID=9e62437b-bdc6-4d66-b7e6-953b2b73335d, EMAILADDRESS=nvn@gmail.com, C=RS, OU=Elektro, O=FTN, GIVENNAME=Nevena, SURNAME=Djukin, CN=DjukinNevena
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                String pocetak = sd.format(cert.getNotBefore());
                System.out.println(pocetak);
                String kraj = sd.format(cert.getNotAfter());
                System.out.println(kraj);
                CertificateDTO certificateDTO = new CertificateDTO(alias, issuedBy, issuedTo, pocetak, kraj, "intermediat");
                if (!certificateRepository.findByAlias(certificateDTO.getAlias()).getRevoked()) {
                    certificateDTOS.add(certificateDTO);
                }
            }
            BufferedInputStream in2 = new BufferedInputStream(new FileInputStream("end-entityCertificate.jks"));
            String password2 = "end-entity";
            ks.load(in2, password2.toCharArray());
            List<String> aliases2 = Collections.list(ks.aliases());

            for (String alias : aliases2) {
                X509Certificate cert = (X509Certificate) ks.getCertificate(alias);
                String issuedBy = (cert.getIssuerDN().getName()).split(",")[7].split("=")[1]; //Dobila sam cn
                System.out.println(issuedBy);
                //NevenaDjukin
                //String issuedBy = cert.getIssuerDN().getName()
                //UID=196c6cc8-cac9-469d-8d65-7d7275e8b5ae, EMAILADDRESS=nvn@gmail.com, C=Srbija, OU=Elektro, O=FTN, GIVENNAME=Nevena, SURNAME=Djukin, CN=NevenaDjukin
                String issuedTo = cert.getSubjectDN().getName().split(",")[7].split("=")[1];
                System.out.println(issuedTo);
                //String issuedTo = cert.getSubjectDN().getName();
                //UID=9e62437b-bdc6-4d66-b7e6-953b2b73335d, EMAILADDRESS=nvn@gmail.com, C=RS, OU=Elektro, O=FTN, GIVENNAME=Nevena, SURNAME=Djukin, CN=DjukinNevena
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                String pocetak = sd.format(cert.getNotBefore());
                System.out.println(pocetak);
                String kraj = sd.format(cert.getNotAfter());
                System.out.println(kraj);
                CertificateDTO certificateDTO = new CertificateDTO(alias, issuedBy, issuedTo, pocetak, kraj, "end-entity");
                if (!certificateRepository.findByAlias(certificateDTO.getAlias()).getRevoked()) {
                    certificateDTOS.add(certificateDTO);
                }
            }
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (EOFException exception) {
            exception.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }

        return certificateDTOS;
    }


    public String povlaciDecu(String aliass,X509Certificate cert1) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {

        KeyStore ks = KeyStore.getInstance("JKS");

        String imePovucenog = (cert1.getIssuerDN().getName()).split(",")[7].split("=")[1];//ovo je cn, nije ime

        BufferedInputStream in = new BufferedInputStream(new FileInputStream("self-signedCertificate.jks"));
        String password = "self-signed";
        ks.load(in, password.toCharArray());

        //trazim iz ks sertifikate po aliasu
        List<String> aliases = Collections.list(ks.aliases());

        for (String alias : aliases){
            X509Certificate cert = (X509Certificate) ks.getCertificate(alias);
            String issuedBy = (cert.getIssuerDN().getName()).split(",")[7].split("=")[1]; //Dobila sam cn
            if(imePovucenog.equals(issuedBy)){
                Certificate certificate = certificateRepository.findByAlias(alias);
                certificate.setRevoked(true);
                certificateRepository.save(certificate);
            }



        }

        BufferedInputStream in1 = new BufferedInputStream(new FileInputStream("intemediatCertificate.jks"));
        String password1 = "intermediat";
        ks.load(in1, password1.toCharArray());
        List<String> aliases1 = Collections.list(ks.aliases());

        for (String alias : aliases1){
            X509Certificate cert = (X509Certificate) ks.getCertificate(alias);
            String issuedBy = (cert.getIssuerDN().getName()).split(",")[7].split("=")[1]; //Dobila sam cn
            if(imePovucenog.equals(issuedBy)){
                Certificate certificate = certificateRepository.findByAlias(alias);
                certificate.setRevoked(true);
                certificateRepository.save(certificate);
            }
        }
        BufferedInputStream in2 = new BufferedInputStream(new FileInputStream("end-entityCertificate.jks"));
        String password2 = "end-entity";
        ks.load(in2, password2.toCharArray());
        List<String> aliases2 = Collections.list(ks.aliases());

        for (String alias : aliases2){
            X509Certificate cert = (X509Certificate) ks.getCertificate(alias);
            String issuedBy = (cert.getIssuerDN().getName()).split(",")[7].split("=")[1]; //Dobila sam cn
            if(imePovucenog.equals(issuedBy)){
                Certificate certificate = certificateRepository.findByAlias(alias);
                certificate.setRevoked(true);
                certificateRepository.save(certificate);
            }
        }
        return "Revoked children of revoked certificate";
    }

    public String revokeExpiredCertificate() throws ParseException, KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
        Date sadasnjeVreme = iso8601Formater.parse(now().toString());

        KeyStore ks = KeyStore.getInstance("JKS");
        KeyStore ks1 = KeyStore.getInstance("JKS");
        KeyStore ks2 = KeyStore.getInstance("JKS");

        BufferedInputStream in = new BufferedInputStream(new FileInputStream("self-signedCertificate.jks"));
        String password = "self-signed";
        ks.load(in, password.toCharArray());
        List<String> aliases = Collections.list(ks.aliases());

        BufferedInputStream in1 = new BufferedInputStream(new FileInputStream("intemediatCertificate.jks"));
        String password1 = "intermediat";
        ks1.load(in1, password1.toCharArray());
        List<String> aliases1 = Collections.list(ks1.aliases());

        BufferedInputStream in2 = new BufferedInputStream(new FileInputStream("end-entityCertificate.jks"));
        String password2 = "end-entity";
        ks2.load(in2, password2.toCharArray());
        List<String> aliases2 = Collections.list(ks2.aliases());

        for (String alias : aliases){
            X509Certificate cert = (X509Certificate) ks.getCertificate(alias);
            if(sadasnjeVreme.after(cert.getNotAfter())){
                Certificate certificate = certificateRepository.findByAlias(alias);
                certificate.setRevoked(true);
            }
        }
        for (String alias : aliases1){
            X509Certificate cert = (X509Certificate) ks1.getCertificate(alias);
            if(sadasnjeVreme.after(cert.getNotAfter())){
                Certificate certificate = certificateRepository.findByAlias(alias);
                certificate.setRevoked(true);
            }
        }
        for (String alias : aliases2){
            X509Certificate cert = (X509Certificate) ks2.getCertificate(alias);
            if(sadasnjeVreme.after(cert.getNotAfter())){
                Certificate certificate = certificateRepository.findByAlias(alias);
                certificate.setRevoked(true);
            }
        }
        return "Revoked expired certificates";
    }


}