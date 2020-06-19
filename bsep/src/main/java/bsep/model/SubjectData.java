package bsep.model;

import lombok.*;
import org.bouncycastle.asn1.x500.X500Name;


import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectData {

    private PrivateKey privateKey;
    private PublicKey publicKey;
    private X500Name x500name;
    private String serialNumber;
    private Date startDate;
    private Date endDate;
}
