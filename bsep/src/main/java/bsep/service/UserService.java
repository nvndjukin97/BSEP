package bsep.service;

import bsep.dto.UserRegistrationDTO;
import bsep.model.User;
import bsep.model.UserRequest;

import java.util.List;

public interface UserService {
    User save(User user);
    List<User> findAll();
    User save(UserRequest userRequest);
    User addUser(UserRegistrationDTO userInfo);
    void delete(Long id);
    void activateUser(Long id);
}

