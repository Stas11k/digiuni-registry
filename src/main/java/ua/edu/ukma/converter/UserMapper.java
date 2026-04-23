package ua.edu.ukma.converter;

import ua.edu.ukma.auth.Role;
import ua.edu.ukma.auth.User;
import ua.edu.ukma.dto.UserDTO;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getLogin(),
                user.getPassword(),
                user.getRole().name(),
                user.getPermissions(),
                user.isBlocked()
        );
    }

    public static User fromDTO(UserDTO dto) {
        return new User(
                dto.id(),
                dto.login(),
                dto.password(),
                Role.valueOf(dto.role()),
                dto.permissions(),
                dto.blocked()
        );
    }
}