package bsep.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "confirmation_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "datetime_created", nullable = false)
    private Date datetimeCreated;

    @Column(name = "used", nullable = false)
    private boolean used;

    @OneToOne
    private User user;

    public ConfirmationToken(User user) {
        this.token = UUID.randomUUID().toString();
        this.datetimeCreated = new Date();
        this.used = false;
        this.user = user;
    }
}
