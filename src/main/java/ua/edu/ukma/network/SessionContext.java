package ua.edu.ukma.network;

import ua.edu.ukma.auth.User;

public class SessionContext {
    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public boolean isAuthenticated() {
        return currentUser != null;
    }

    public void logout() {
        this.currentUser = null;
    }
}