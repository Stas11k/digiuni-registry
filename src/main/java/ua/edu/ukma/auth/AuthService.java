package ua.edu.ukma.auth;
import ua.edu.ukma.domain.Teacher;

import java.util.ArrayList;
import java.util.List;

public class AuthService {
    private List<User> users =  new ArrayList<>();
    public AuthService() {
        users.add(new User("user", "1234", Role.USER));
        users.add(new User("manager", "admin", Role.MANAGER));
    }
    public User login(String login, String password) {
        for (User u: users) {
            if (u.getLogin().equals(login) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }
}
