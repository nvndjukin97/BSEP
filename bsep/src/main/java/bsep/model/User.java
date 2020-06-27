package bsep.model;

import com.mysql.cj.conf.PropertyDefinitions;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="user_type", discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorValue("0")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "organization")
    private String organization;

    @Column(name = "organizationUnit")
    private String organizationUnit;

    @Column(name = "country")
    private String country;

    @Column(name = "email")
    private String email;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "last_password_reset_date")
    private Date lastPasswordResetDate;

    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private List<Authority> authorities;

    @Column
    private Boolean isCa;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public List<Authority> getUserAuthorities() {
        if (this.authorities == null) {
            this.authorities = new ArrayList<>();
        }

        return this.authorities;
    }
}
