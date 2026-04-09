package ua.edu.ukma.ui;

import ua.edu.ukma.auth.AuthService;
import ua.edu.ukma.auth.Role;
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

    private final University university =
            new University("Kyiv-Mohyla Academy", "NaUKMA", "Kyiv", "2 Hryhorii Skovoroda St.");

    private final AuthService authService = new AuthService();
    private final FacultyService facultyService;
    private final DepartmentService departmentService;
    private final SpecialtyService specialtyService;
    private final StudentService studentService;
    private final TeacherService teacherService;

    public ConsoleMenu(FacultyService facultyService,
                       DepartmentService departmentService,
                       SpecialtyService specialtyService,
                       StudentService studentService,
                       TeacherService teacherService) {
        this.facultyService = facultyService;
        this.departmentService = departmentService;
        this.specialtyService = specialtyService;
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    public void start() {
        boolean applicationRunning = true;

        while (applicationRunning) {
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

        if (user.getRole() == Role.USER) {
            System.out.println("(User mode: read only)");
        } else if (user.getRole() == Role.MANAGER) {
            System.out.println("(Manager mode: CRUD access)");
        } else {
            System.out.println("(Admin mode: CRUD + user management)");
        }
    }

    private void runSession(User user) {
        Role role = user.getRole();
        boolean sessionRunning = true;

        while (sessionRunning) {
            printMainMenu(role);

            System.out.print("Choose option: ");
            int choice = readInt();

            if (role == Role.ADMIN) {
                switch (choice) {
                    case 1 -> new FacultyMenu(scanner, facultyService, teacherService, departmentService, role).start();
                    case 2 -> new DepartmentMenu(scanner, departmentService, facultyService, teacherService, role).start();
                    case 3 -> new SpecialtyMenu(scanner, specialtyService, departmentService, role).start();
                    case 4 -> new StudentMenu(scanner, studentService, specialtyService, role).start();
                    case 5 -> new TeacherMenu(scanner, teacherService, departmentService, role).start();
                    case 6 -> new UniversityMenu(scanner, university).start();
                    case 7 -> new ReportMenu(scanner, facultyService, departmentService, studentService, teacherService).start();
                    case 8 -> new UserManagementMenu(scanner, authService).start();
                    case 0 -> sessionRunning = false;
                    default -> System.out.println("Unknown option\n");
                }
            } else {
                switch (choice) {
                    case 1 -> new FacultyMenu(scanner, facultyService, teacherService, departmentService, role).start();
                    case 2 -> new DepartmentMenu(scanner, departmentService, facultyService, teacherService, role).start();
                    case 3 -> new SpecialtyMenu(scanner, specialtyService, departmentService, role).start();
                    case 4 -> new StudentMenu(scanner, studentService, specialtyService, role).start();
                    case 5 -> new TeacherMenu(scanner, teacherService, departmentService, role).start();
                    case 6 -> new ReportMenu(scanner, facultyService, departmentService, studentService, teacherService).start();
                    case 0 -> sessionRunning = false;
                    default -> System.out.println("Unknown option\n");
                }
            }
        }
    }

    private void printMainMenu(Role role) {
        if (role == Role.ADMIN) {
            System.out.println("""
                    === University System ===
                    1. Faculties
                    2. Departments
                    3. Specialties
                    4. Students
                    5. Teachers
                    6. University settings
                    7. Reports
                    8. User management
                    0. Exit
                    """);
        } else {
            System.out.println("""
                    === University System ===
                    1. Faculties
                    2. Departments
                    3. Specialties
                    4. Students
                    5. Teachers
                    6. Reports
                    0. Exit
                    """);
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