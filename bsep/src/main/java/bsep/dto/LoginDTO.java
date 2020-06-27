package bsep.dto;


import com.github.rkpunjal.sqlsafe.SQLInjectionSafe;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class LoginDTO {

    @NotBlank(message = "Username can not be empty")
    @Size(min = 1, max = 100, message = "The maximum length of username is 100 characters")
    private @SQLInjectionSafe String username;

    @NotBlank(message = "Password can not be empty")
    @Size(min = 10, max = 100, message = "The maximum length of password is 100 characters")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{10,}$", message = "Minimum ten characters, at least one letter and one number")
    private String password;

    public LoginDTO() {
        super();
    }

    public LoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
