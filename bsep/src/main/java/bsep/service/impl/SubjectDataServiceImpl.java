package bsep.service.impl;

import bsep.dto.SubjectDTO;
import bsep.model.SubjectData;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.springframework.stereotype.Service;

import java.security.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import static java.time.LocalDate.*;

@Service
public class SubjectDataServiceImpl {

    public KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(2048, random);
            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String generateUID(){
        //randomUUID vraca niz bajtova
        String generatedUUid = UUID.randomUUID().toString();
        return generatedUUid;

    }


    public SubjectData generateSubjectData(SubjectDTO subjectDTO) throws ParseException {
        //try {
            KeyPair keyPairSubject = generateKeyPair();

            //Datumi od kad do kad vazi sertifikat
            //Sertifikat je validan 5 godina
            SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = iso8601Formater.parse(now().toString());
            Date endDate = iso8601Formater.parse(now().plus(5, ChronoUnit.YEARS).toString());

            //Serijski broj sertifikata
            String sn="1";
            //klasa X500NameBuilder pravi X500Name objekat koji predstavlja podatke o vlasniku
            X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
            builder.addRDN(BCStyle.CN, subjectDTO.getCn());
            builder.addRDN(BCStyle.SURNAME, subjectDTO.getSurname());
            builder.addRDN(BCStyle.GIVENNAME, subjectDTO.getGivenName());
            builder.addRDN(BCStyle.O, subjectDTO.getOrganization());
            builder.addRDN(BCStyle.OU, subjectDTO.getOrganizationUnit());
            builder.addRDN(BCStyle.C, subjectDTO.getCountry());
            builder.addRDN(BCStyle.E, subjectDTO.getEmail());
            //UID (USER ID) je ID korisnika
            builder.addRDN(BCStyle.UID, generateUID());

            //Kreiraju se podaci za sertifikat, sto ukljucuje:
            // - javni kljuc koji se vezuje za sertifikat
            // - podatke o vlasniku
            // - serijski broj sertifikata
            // - od kada do kada vazi sertifikat
            return new SubjectData(keyPairSubject.getPublic(), builder.build(), sn, startDate, endDate);
        //} catch (ParseException e) {
          //  e.printStackTrace();
        //}
        //return null;
    }
}
