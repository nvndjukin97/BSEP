package bsep.service.impl;

import bsep.model.User;
import bsep.repository.UserRepository;
import bsep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User save(User user){
        return userRepository.save(user);
    }

    @Override
    public void delete(User user){
        userRepository.delete(user);
    }
}
