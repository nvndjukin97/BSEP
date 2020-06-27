package bsep.dto;

import com.github.rkpunjal.sqlsafe.SQLInjectionSafe;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SubjectDTO {
    //private String cn;

    private @SQLInjectionSafe String surname;
    private @SQLInjectionSafe String givenName;
    private @SQLInjectionSafe String organization;
    private @SQLInjectionSafe String organizationUnit;
    private @SQLInjectionSafe String country;
    private @SQLInjectionSafe String email;

//     builder.addRDN(BCStyle.CN, "Nikola Luburic");
//	    builder.addRDN(BCStyle.SURNAME, "Luburic");
//	    builder.addRDN(BCStyle.GIVENNAME, "Nikola");
//	    builder.addRDN(BCStyle.O, "UNS-FTN");
//	    builder.addRDN(BCStyle.OU, "Katedra za informatiku");
//	    builder.addRDN(BCStyle.C, "RS");
//	    builder.addRDN(BCStyle.E, "nikola.luburic@uns.ac.rs");

}
