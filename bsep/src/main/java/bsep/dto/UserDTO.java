package bsep.dto;

import bsep.model.Authority;
import bsep.model.User;
import com.github.rkpunjal.sqlsafe.SQLInjectionSafe;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private Long id;
    @NotBlank(message = "Username can not be empty")
    @Size(min = 1, max = 100, message = "The maximum length of username is 100 characters")
    private @SQLInjectionSafe String username;

    @Pattern(regexp = "[a-zA-Z0-9\\s\\-]{1,50}")
    private @SQLInjectionSafe String name;

    @Pattern(regexp = "[a-zA-Z0-9\\s\\-]{1,50}")
    private @SQLInjectionSafe String email;

    @Pattern(regexp = "[a-zA-Z0-9\\s\\-]{1,50}")
    private @SQLInjectionSafe String country;

    @Pattern(regexp = "[a-zA-Z0-9\\s\\-]{1,50}")
    private @SQLInjectionSafe String oranization;


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
