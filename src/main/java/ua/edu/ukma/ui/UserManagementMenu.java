package ua.edu.ukma.ui;

import ua.edu.ukma.auth.AuthService;
import ua.edu.ukma.auth.Role;
import ua.edu.ukma.auth.User;
import ua.edu.ukma.exception.EntityNotFoundException;
import ua.edu.ukma.exception.ValidationException;

import java.util.Optional;
import java.util.Scanner;

public class UserManagementMenu {

    private final Scanner scanner;
    private final AuthService authService;

    public UserManagementMenu(Scanner scanner, AuthService authService) {
        this.scanner = scanner;
        this.authService = authService;
    }

    public void start() {
        boolean inMenu = true;

        while (inMenu) {
            System.out.println("""
                    --- User management ---
                    1. Show all users
                    2. Add user
                    3. Edit user
                    4. Block user
                    5. Unblock user
                    6. Delete user
                    0. Back
                    """);

            System.out.print("Choose option: ");
            int choice = readInt();

            switch (choice) {
                case 1 -> showAll();
                case 2 -> add();
                case 3 -> edit();
                case 4 -> changeBlocked(true);
                case 5 -> changeBlocked(false);
                case 6 -> delete();
                case 0 -> inMenu = false;
                default -> System.out.println("Unknown option\n");
            }
        }
    }

    private void showAll() {
        for (User user : authService.getAllUsers()) {
            System.out.println(user);
        }
        System.out.println();
    }

    private void add() {
        try {
            System.out.print("Login: ");
            String login = readRequiredLine();

            System.out.print("Password: ");
            String password = readRequiredLine();

            Role role = chooseRole();

            authService.addUser(login, password, role);
            System.out.println("User created\n");

        } catch (ValidationException e) {
            System.out.println("Error: " + e.getMessage() + "\n");
        }
    }

    private void edit() {
        try {
            System.out.print("User ID: ");
            int id = readInt();

            Optional<String> login = Optional.empty();
            Optional<String> password = Optional.empty();
            Optional<Role> role = Optional.empty();

            boolean editing = true;
            while (editing) {
                System.out.println("""
                        What do you want to edit
                        1. Login
                        2. Password
                        3. Role
                        9. Edit all
                        0. Back
                        """);
                System.out.print("Choose option: ");
                int c = readInt();

                switch (c) {
                    case 1 -> login = Optional.of(readLineWithPrompt("New login"));
                    case 2 -> password = Optional.of(readLineWithPrompt("New password"));
                    case 3 -> role = Optional.of(chooseRole());
                    case 9 -> {
                        login = Optional.of(readLineWithPrompt("New login"));
                        password = Optional.of(readLineWithPrompt("New password"));
                        role = Optional.of(chooseRole());
                    }
                    case 0 -> {
                        return;
                    }
                    default -> {
                        System.out.println("Unknown option\n");
                        continue;
                    }
                }

                authService.updateUser(id, login, password, role);
                System.out.println("User updated\n");
                editing = false;
            }

        } catch (ValidationException | EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage() + "\n");
        }
    }

    private void changeBlocked(boolean blocked) {
        try {
            System.out.print("User ID: ");
            int id = readInt();
            authService.setBlocked(id, blocked);
            System.out.println(blocked ? "User blocked\n" : "User unblocked\n");

        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage() + "\n");
        }
    }

    private void delete() {
        try {
            System.out.print("User ID: ");
            int id = readInt();
            authService.deleteUser(id);
            System.out.println("Deleted\n");

        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage() + "\n");
        }
    }

    private Role chooseRole() {
        while (true) {
            System.out.println("""
                    Choose role:
                    1. USER
                    2. MANAGER
                    3. ADMIN
                    """);
            System.out.print("Choose option: ");
            int choice = readInt();

            switch (choice) {
                case 1:
                    return Role.USER;
                case 2:
                    return Role.MANAGER;
                case 3:
                    return Role.ADMIN;
                default:
                    System.out.println("Unknown option\n");
            }
        }
    }

    private String readLineWithPrompt(String prompt) {
        while (true) {
            System.out.print(prompt + ": ");
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Value cannot be empty\n");
        }
    }

    private String readRequiredLine() {
        while (true) {
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.print("Value cannot be empty. Try again: ");
        }
    }

    private int readInt() {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a number: ");
            }
        }
    }
}