package bsep.service.impl;

import bsep.dto.SubjectDTO;
import bsep.model.IssuerData;
import bsep.model.User;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;

@Service
public class IssuerDataServiceImpl {

    @Autowired
    SubjectDataServiceImpl subjectDataService;

    public IssuerData generateIssuerData(PrivateKey issuerKey, User subjectDTO) {
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, subjectDTO.getFirstName()+subjectDTO.getLastName());
        builder.addRDN(BCStyle.SURNAME, subjectDTO.getLastName());
        builder.addRDN(BCStyle.GIVENNAME, subjectDTO.getFirstName());
        builder.addRDN(BCStyle.O, subjectDTO.getOrganization());
        builder.addRDN(BCStyle.OU, subjectDTO.getOrganizationUnit());
        builder.addRDN(BCStyle.C, subjectDTO.getCountry());
        builder.addRDN(BCStyle.E, subjectDTO.getEmail());
        //UID (USER ID) je ID korisnika
        builder.addRDN(BCStyle.UID, subjectDataService.generateUID());

        //Kreiraju se podaci za issuer-a, sto u ovom slucaju ukljucuje:
        // - privatni kljuc koji ce se koristiti da potpise sertifikat koji se izdaje
        // - podatke o vlasniku sertifikata koji izdaje nov sertifikat
        return new IssuerData(issuerKey, builder.build());
    }
}
