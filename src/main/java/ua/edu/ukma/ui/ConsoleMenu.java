package ua.edu.ukma.ui;

import ua.edu.ukma.domain.*;
import ua.edu.ukma.repository.*;
import ua.edu.ukma.service.*;

import java.util.Scanner;
import ua.edu.ukma.auth.AuthService;
import ua.edu.ukma.auth.Role;
import ua.edu.ukma.auth.User;

public class ConsoleMenu {

    private final Scanner scanner = new Scanner(System.in);

    private final University university =
            new University("Kyiv-Mohyla Academy", "NaUKMA", "Kyiv", "2 Hryhorii Skovoroda St.");

    private final AuthService authService = new AuthService();
    private final Repository<Faculty, Integer> facultyRepo = new InMemoryRepository<>();
    private final Repository<Department, Integer> departmentRepo = new InMemoryRepository<>();
    private final Repository<Specialty, Integer> specialtyRepo = new InMemoryRepository<>();
    private final Repository<Student, Integer> studentRepo = new InMemoryRepository<>();
    private final Repository<Teacher, Integer> teacherRepo = new InMemoryRepository<>();

    private final FacultyService facultyService = new FacultyService(facultyRepo);
    private final DepartmentService departmentService = new DepartmentService(departmentRepo);
    private final SpecialtyService specialtyService = new SpecialtyService(specialtyRepo);
    private final StudentService studentService = new StudentService(studentRepo);
    private final TeacherService teacherService = new TeacherService(teacherRepo);

    public void start() {
        System.out.print("Login: ");
        String login = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = authService.login(login, password);

        if (user == null) {
            System.out.println("Invalid credentials");
            return;
        }

        Role role = user.getRole();

        System.out.println("Welcome " + user.getLogin());

        if (user.getRole() == Role.USER) {
            System.out.println("(User mode: read only)");
        }
        boolean running = true;
        while (running) {
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
                    case 0 -> running = false;
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
                    case 0 -> running = false;
                    default -> System.out.println("Unknown option\n");
                }
            }
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
