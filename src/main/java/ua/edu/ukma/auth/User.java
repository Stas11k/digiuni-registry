package ua.edu.ukma.auth;

import ua.edu.ukma.exception.ValidationException;

public class User {
    private static int counter = 1;

    private final int id;
    private String login;
    private String password;
    private Role role;
    private boolean blocked;

    public User(String login, String password, Role role) {
        validateLogin(login);
        validatePassword(password);
        validateRole(role);
        this.id = counter++;
        this.login = login;
        this.password = password;
        this.role = role;
        this.blocked = false;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        validateLogin(login);
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        validatePassword(password);
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        validateRole(role);
        this.role = role;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    private void validateLogin(String login) {
        if (login == null || login.isBlank()) throw new ValidationException("Login cannot be empty");
    }

    private void validatePassword(String password) {
        if (password == null || password.isBlank()) throw new ValidationException("Password cannot be empty");
    }

    private void validateRole(Role role) {
        if (role == null) throw new ValidationException("Role cannot be null");
    }

    @Override
    public String toString() {
        return id + " | " + login + " | role: " + role + " | blocked: " + blocked;
    }
}