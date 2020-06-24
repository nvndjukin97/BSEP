package bsep.controller;


import bsep.dto.UserRegistrationDTO;
import bsep.model.User;
import bsep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

// Primer kontrolera cijim metodama mogu pristupiti samo autorizovani korisnici
@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

	@Autowired
	private UserService userService;



    @PostMapping("/public/register")
    public ResponseEntity add( @RequestBody UserRegistrationDTO user) {
        userService.addUser(user);
        return ResponseEntity.ok().build();
    }

	// Za pristup ovoj metodi neophodno je da ulogovani korisnik ima ADMIN ulogu
	// Ukoliko nema, server ce vratiti gresku 403 Forbidden
	// Korisnik jeste autentifikovan, ali nije autorizovan da pristupi resursu
	@GetMapping("/user/{userId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public User loadById(@PathVariable Long userId) {
		return this.userService.findById(userId);
	}

	@GetMapping("/getLoggedIn")
	public User getLoggedIn() {
		return this.userService.getLoogedIn();
	}

	@GetMapping("/user/all")
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<User> loadAll() {
		return this.userService.findAll();
	}


	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity delete(@PathVariable Long id) {
		userService.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("activate/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity activate(@PathVariable Long id) {
		userService.activateUser(id);
		return ResponseEntity.ok().build();
	}


}
