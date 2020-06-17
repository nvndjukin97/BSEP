package bsep.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
// DTO koji preuzima podatke iz HTML forme za registraciju
public class UserRequest {

	private Long id;

	private String username;

	private String password;

	private String firstname;

	private String lastname;


}
