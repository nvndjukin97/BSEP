package bsep.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String alias;

    @Column
    private Boolean revoked;

    public Certificate (String alias, Boolean revoked){
        this.alias=alias;
        this.revoked=revoked;
    }
}
