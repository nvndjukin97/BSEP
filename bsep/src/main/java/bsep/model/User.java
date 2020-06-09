package bsep.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String givenName;

    @Column
    private String commonName;

    @Column
    private String organization;

    @Column
    private String organizationUnit;

    @Column
    private String country;

    //@Column
    //private Boolean isCa;
}
