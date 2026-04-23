package ua.edu.ukma.auth;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ua.edu.ukma.exception.ValidationException;

@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    private static int counter = 1;

    @EqualsAndHashCode.Include
    private final int id;

    private String login;
    private String password;
    private Role role;
    private int permissions;
    private boolean blocked;

    public User(String login, String password, Role role) {
        validateLogin(login);
        validatePassword(password);
        validateRole(role);
        this.id = counter++;
        this.login = login;
        this.password = password;
        this.role = role;
        this.permissions = role.getDefaultPermissions();
        this.blocked = false;
    }

    public void setLogin(String login) {
        validateLogin(login);
        this.login = login;
    }

    public void setPassword(String password) {
        validatePassword(password);
        this.password = password;
    }

    public void setRole(Role role) {
        validateRole(role);
        this.role = role;
        this.permissions = role.getDefaultPermissions();
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

    public void setPermissions(int permissions) {
        this.permissions = permissions;
    }

    public boolean hasPermission(int permission) {
        return Permission.has(this.permissions, permission);
    }

    public void addPermission(int permission) {
        this.permissions = Permission.add(this.permissions, permission);
    }

    public void removePermission(int permission) {
        this.permissions = Permission.remove(this.permissions, permission);
    }

    @Override
    public String toString() {
        return id + " | " + login
                + " | role: " + role
                + " | permissions: " + permissions
                + " | blocked: " + blocked;
    }
}