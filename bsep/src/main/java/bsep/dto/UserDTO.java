package bsep.dto;

import bsep.model.Authority;
import bsep.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private Long id;
    private String username;
    private String name;
    private String email;
    private String country;
    private String oranization;
    private String organizationUnit;
    private boolean enabled;
    private List<String> authorities;
    private UserTokenDTO token;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getFirstName();
        this.email = user.getEmail();
        this.country = user.getCountry();
        this.oranization = user.getOrganization();
        this.organizationUnit = user.getOrganizationUnit();
        this.enabled = user.isEnabled();
        this.token = null;


        this.authorities = user.getAuthorities().stream()
                .map(authority ->  authority.getName()).collect(Collectors.toList());
    }

}
