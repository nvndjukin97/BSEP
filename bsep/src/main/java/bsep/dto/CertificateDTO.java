package bsep.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CertificateDTO {
    private String alias;

    private String issuedBy;

    private String issuedTo;

    private String startDate;

    private String endDate;

    private String certificateType;
}
