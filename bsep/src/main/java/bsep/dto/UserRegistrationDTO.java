package bsep.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDTO {

    private String username;

    private String email;

    private String password;

    private String repeatPassword;

    private String name;

    private String surname;

    private String organization;

    private String organizationUnit;

    private String country;

    private String role;

}
