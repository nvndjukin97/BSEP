package bsep.service;

import bsep.dto.UserRegistrationDTO;
import bsep.model.User;
import bsep.model.UserRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);
    //Optional<User> findById(Long id);
    List<User> findAll();
    User save(UserRequest userRequest);
    User addUser(UserRegistrationDTO userInfo);
    void delete(Long id);
    void activateUser(Long id);
    User findById(Long id);
    User findByUsername(String username);
    public User getLoogedIn();
    User ulogovani();
}

