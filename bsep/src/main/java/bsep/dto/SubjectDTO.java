package bsep.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SubjectDTO {
    private String cn;
    private String surname;
    private String givenName;
    private String organization;
    private String organizationUnit;
    private String country;
    private String email;

//     builder.addRDN(BCStyle.CN, "Nikola Luburic");
//	    builder.addRDN(BCStyle.SURNAME, "Luburic");
//	    builder.addRDN(BCStyle.GIVENNAME, "Nikola");
//	    builder.addRDN(BCStyle.O, "UNS-FTN");
//	    builder.addRDN(BCStyle.OU, "Katedra za informatiku");
//	    builder.addRDN(BCStyle.C, "RS");
//	    builder.addRDN(BCStyle.E, "nikola.luburic@uns.ac.rs");

}
