package ua.edu.ukma.auth;

public class User {
    private String login;
    private String password;
    private Role role;
    public User(String login, String password, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }
    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }
    public Role getRole() {
        return role;
    }

}
