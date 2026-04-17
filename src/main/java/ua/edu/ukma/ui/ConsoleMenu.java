package ua.edu.ukma.ui;

import ua.edu.ukma.auth.AuthService;
import ua.edu.ukma.auth.Permission;
import ua.edu.ukma.auth.User;
import ua.edu.ukma.domain.University;
import ua.edu.ukma.exception.ValidationException;
import ua.edu.ukma.service.DepartmentService;
import ua.edu.ukma.service.FacultyService;
import ua.edu.ukma.service.SpecialtyService;
import ua.edu.ukma.service.StudentService;
import ua.edu.ukma.service.TeacherService;

import java.util.Scanner;

public class ConsoleMenu {

    private final Scanner scanner = new Scanner(System.in);

    private final University university;

    private final AuthService authService = new AuthService();
    private final FacultyService facultyService;
    private final DepartmentService departmentService;
    private final SpecialtyService specialtyService;
    private final StudentService studentService;
    private final TeacherService teacherService;

    public ConsoleMenu(FacultyService facultyService, DepartmentService departmentService, SpecialtyService specialtyService, StudentService studentService, TeacherService teacherService, University university) {
        this.facultyService = facultyService;
        this.departmentService = departmentService;
        this.specialtyService = specialtyService;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.university = university;
    }

    public void start() {
        while (true) {
            User user = authorize();
            if (user == null) {
                return;
            }

            showWelcome(user);
            runSession(user);
        }
    }

    private User authorize() {
        while (true) {
            System.out.println("""
                    --- Authorization ---
                    1. Log in
                    0. Exit
                    """);
            System.out.print("Choose option: ");
            int choice = readInt();

            switch (choice) {
                case 1 -> {
                    User user = login();
                    if (user != null) {
                        return user;
                    }
                }
                case 0 -> {
                    return null;
                }
                default -> System.out.println("Unknown option\n");
            }
        }
    }

    private User login() {
        System.out.print("Login: ");
        String login = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        try {
            User user = authService.login(login, password);
            if (user == null) {
                System.out.println("Invalid credentials\n");
                return null;
            }
            return user;
        } catch (ValidationException e) {
            System.out.println("Error: " + e.getMessage() + "\n");
            return null;
        }
    }

    private void showWelcome(User user) {
        System.out.println("Welcome " + user.getLogin());
        System.out.println("Role: " + user.getRole());
        System.out.println("Permissions mask: " + user.getPermissions());

        if (user.hasPermission(Permission.MANAGE_USERS)) {
            System.out.println("(Admin capabilities available)");
        } else if (user.hasPermission(Permission.EDIT_STUDENTS)
                || user.hasPermission(Permission.EDIT_TEACHERS)
                || user.hasPermission(Permission.EDIT_FACULTIES)
                || user.hasPermission(Permission.EDIT_DEPARTMENTS)
                || user.hasPermission(Permission.EDIT_SPECIALTIES)) {
            System.out.println("(Editing capabilities available)");
        } else {
            System.out.println("(Read only mode)");
        }
    }

    private void runSession(User user) {
        boolean sessionRunning = true;

        while (sessionRunning) {
            printMainMenu(user);

            System.out.print("Choose option: ");
            int choice = readInt();

            switch (choice) {
                case 1 -> {
                    if (user.hasPermission(Permission.VIEW_FACULTIES)) {
                        new FacultyMenu(scanner, facultyService, teacherService, departmentService, user).start();
                    } else {
                        System.out.println("Access denied\n");
                    }
                }
                case 2 -> {
                    if (user.hasPermission(Permission.VIEW_DEPARTMENTS)) {
                        new DepartmentMenu(scanner, departmentService, facultyService, teacherService, user).start();
                    } else {
                        System.out.println("Access denied\n");
                    }
                }
                case 3 -> {
                    if (user.hasPermission(Permission.VIEW_SPECIALTIES)) {
                        new SpecialtyMenu(scanner, specialtyService, departmentService, user).start();
                    } else {
                        System.out.println("Access denied\n");
                    }
                }
                case 4 -> {
                    if (user.hasPermission(Permission.VIEW_STUDENTS)) {
                        new StudentMenu(scanner, studentService, specialtyService, user).start();
                    } else {
                        System.out.println("Access denied\n");
                    }
                }
                case 5 -> {
                    if (user.hasPermission(Permission.VIEW_TEACHERS)) {
                        new TeacherMenu(scanner, teacherService, departmentService, user).start();
                    } else {
                        System.out.println("Access denied\n");
                    }
                }
                case 6 -> {
                    if (user.hasPermission(Permission.EDIT_UNIVERSITY)) {
                        new UniversityMenu(scanner, university).start();
                    } else if (user.hasPermission(Permission.VIEW_REPORTS)) {
                        new ReportMenu(scanner, facultyService, departmentService, studentService, teacherService).start();
                    } else {
                        System.out.println("Access denied\n");
                    }
                }
                case 7 -> {
                    if (user.hasPermission(Permission.EDIT_UNIVERSITY)) {
                        new ReportMenu(scanner, facultyService, departmentService, studentService, teacherService).start();
                    } else if (user.hasPermission(Permission.MANAGE_USERS)) {
                        new UserManagementMenu(scanner, authService).start();
                    } else {
                        System.out.println("Unknown option\n");
                    }
                }
                case 8 -> {
                    if (user.hasPermission(Permission.MANAGE_USERS)) {
                        new UserManagementMenu(scanner, authService).start();
                    } else {
                        System.out.println("Unknown option\n");
                    }
                }
                case 0 -> sessionRunning = false;
                default -> System.out.println("Unknown option\n");
            }
        }
    }

    private void printMainMenu(User user) {
        System.out.println("=== University System ===");
        System.out.println("1. Faculties");
        System.out.println("2. Departments");
        System.out.println("3. Specialties");
        System.out.println("4. Students");
        System.out.println("5. Teachers");

        if (user.hasPermission(Permission.EDIT_UNIVERSITY)) {
            System.out.println("6. University settings");
            System.out.println("7. Reports");
            if (user.hasPermission(Permission.MANAGE_USERS)) {
                System.out.println("8. User management");
            }
        } else {
            if (user.hasPermission(Permission.VIEW_REPORTS)) {
                System.out.println("6. Reports");
            }
            if (user.hasPermission(Permission.MANAGE_USERS)) {
                System.out.println("7. User management");
            }
        }

        System.out.println("0. Logout");
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