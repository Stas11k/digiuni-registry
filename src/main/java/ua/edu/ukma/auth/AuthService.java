package ua.edu.ukma.auth;

import ua.edu.ukma.exception.EntityNotFoundException;
import ua.edu.ukma.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthService {
    private final List<User> users = new ArrayList<>();

    public AuthService() {
        users.add(new User("user", "user", Role.USER));
        users.add(new User("manager", "manager", Role.MANAGER));
        users.add(new User("admin", "admin", Role.ADMIN));
    }

    public User login(String login, String password) {
        User user = users.stream()
                .filter(u -> u.getLogin().equals(login) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
        if (user != null && user.isBlocked()) throw new ValidationException("User is blocked");
        return user;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public Optional<User> findById(int id) {
        return users.stream()
                .filter(u -> u.getId() == id)
                .findFirst();
    }

    public Optional<User> findByLogin(String login) {
        return users.stream()
                .filter(u -> u.getLogin().equalsIgnoreCase(login))
                .findFirst();
    }

    public void addUser(String login, String password, Role role) {
        if (findByLogin(login).isPresent()) throw new ValidationException("User with this login already exists");
        users.add(new User(login, password, role));
    }

    public void updateUser(int id, Optional<String> login, Optional<String> password, Optional<Role> role) {
        User user = getUserOrThrow(id);
        if (login.isPresent()) {
            String newLogin = login.get();
            Optional<User> existing = findByLogin(newLogin);
            if (existing.isPresent() && existing.get().getId() != id) throw new ValidationException("User with this login already exists");
            user.setLogin(newLogin);
        }
        if (password.isPresent()) user.setPassword(password.get());
        if (role.isPresent()) user.setRole(role.get());
    }

    public void setBlocked(int id, boolean blocked) {
        User user = getUserOrThrow(id);
        user.setBlocked(blocked);
    }

    public boolean deleteUser(int id) {
        User user = getUserOrThrow(id);
        return users.remove(user);
    }

    private User getUserOrThrow(int id) {
        return findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
    }
}