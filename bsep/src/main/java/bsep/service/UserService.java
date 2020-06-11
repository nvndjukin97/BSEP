package bsep.service;

import bsep.model.User;

public interface UserService {
    User save(User user);
    User delete(User user);
}
