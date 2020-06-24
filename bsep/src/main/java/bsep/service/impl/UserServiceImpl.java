package bsep.service.impl;

import bsep.common.TimeProvider;
import bsep.config.consts.UserRoles;
import bsep.dto.UserRegistrationDTO;
import bsep.exception.ApiRequestException;
import bsep.exception.ResourceNotFoundException;
import bsep.mappers.UserMapper;
import bsep.model.Authority;
import bsep.model.ConfirmationToken;
import bsep.model.User;
import bsep.model.UserRequest;
import bsep.repository.AuthorityRepository;
import bsep.repository.ConfirmationTokenRepository;
import bsep.repository.UserRepository;
import bsep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityServiceImpl authorityService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ConfirmationTokenRepository tokenRepository;

    @Autowired
    private TimeProvider timeProvider;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public User save(User user){
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        List<User> result = userRepository.findAll();
        return result;
    }
    @Override
    public User save(UserRequest userRequest) {
        User u = new User();
        // pre nego sto postavimo lozinku u atribut hesiramo je
        u.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        u.setFirstName(userRequest.getFirstname());
        u.setLastName(userRequest.getLastname());

        List<Authority> auth = authorityService.findByname("ROLE_USER");
        // u primeru se registruju samo obicni korisnici i u skladu sa tim im se i dodeljuje samo rola USER
        u.setAuthorities(auth);

        u = this.userRepository.save(u);
        return u;
    }

    @Override
    public User addUser(UserRegistrationDTO userInfo) {
        if (userRepository.findByUsername(userInfo.getUsername()) != null) {
            throw new ApiRequestException("Username '" + userInfo.getUsername() + "' already exists.");
        }

        if (!userInfo.getPassword().equals(userInfo.getRepeatPassword())) {
            throw new ApiRequestException("Provided passwords must be the same.");
        }

        if (userRepository.findByEmail(userInfo.getEmail()) != null) {
            throw new ApiRequestException("Email '" + userInfo.getEmail() + "' is taken.");
        }

        User user = createNewUserObject(userInfo);
        userRepository.save(user);

        ConfirmationToken token = new ConfirmationToken(user);
        tokenRepository.save(token);

        //mailSenderService.sendRegistrationMail(token);

        return user;
    }

    private User createNewUserObject(UserRegistrationDTO userInfo) {
        //Korisnik user = UserMapper.toKorisnikEntity(userInfo);
        User user = UserMapper.toUserEntity(userInfo);
        user.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        user.setLastPasswordResetDate(timeProvider.nowTimestamp());
        if(userInfo.getRole().equals("ROLE_CA")){
            user.getUserAuthorities().add(authorityRepository.findByName(UserRoles.ROLE_CA));
        }else if(userInfo.getRole().equals("ROLE_NOT_CA")){
            user.getUserAuthorities().add(authorityRepository.findByName(UserRoles.ROLE_NOT_CA));
        }
        user.getUserAuthorities().add(authorityRepository.findByName(UserRoles.ROLE_KORISNIK));
        user.setFirstName(userInfo.getName());
        user.setLastName(userInfo.getSurname());
        user.setEmail(userInfo.getEmail());
        user.setCountry(userInfo.getCountry());
        user.setOrganization(userInfo.getOrganization());
        user.setOrganizationUnit(userInfo.getOrganizationUnit());


        //aktivacija naloga
        user.setEnabled(true);



        return user;
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " doesn't exist"));

        userRepository.delete(user);
    }

    @Override
    public void activateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " doesn't exist"));

        if(user.isEnabled()) {
            user.setEnabled(false);
        } else {
            user.setEnabled(true);
        }
        userRepository.save(user);
    }

    @Override
    public User findById(Long id) throws AccessDeniedException {
        User u = userRepository.findById(id).orElseGet(null);
        return u;
    }

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        User u = userRepository.findByUsername(username);
        return u;
    }




    @Override
    public User getLoogedIn() throws AccessDeniedException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        System.out.println(username+"userko");

        return  findByUsername(username);
    }


}
