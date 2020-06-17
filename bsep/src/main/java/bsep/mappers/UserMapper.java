package bsep.mappers;


import bsep.dto.UserDTO;
import bsep.dto.UserRegistrationDTO;
import bsep.model.User;

public class UserMapper {

    public static User toUserEntity(UserRegistrationDTO userInfo) {
        User user = new User();
        user.setUsername(userInfo.getUsername());
        user.setEmail(userInfo.getEmail());
        user.setEmail(userInfo.getName());

        return user;
    }



    public static UserDTO toDto(User user) {
        return new UserDTO(user);
    }

    private UserMapper() {
    }
}
