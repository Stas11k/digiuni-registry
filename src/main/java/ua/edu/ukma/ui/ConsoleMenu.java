package ua.edu.ukma.ui;

import ua.edu.ukma.repository.*;
import ua.edu.ukma.service.*;

import java.util.Scanner;

public class ConsoleMenu {

    private final Scanner scanner = new Scanner(System.in);

    private final FacultyService facultyService = new FacultyService(new InMemoryFacultyRepository());
    private final DepartmentService departmentService = new DepartmentService(new InMemoryDepartmentRepository());
    private final SpecialtyService specialtyService = new SpecialtyService(new InMemorySpecialtyRepository());
    private final StudentService studentService = new StudentService(new InMemoryStudentRepository());
    private final TeacherService teacherService = new TeacherService(new InMemoryTeacherRepository());

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("""
                    === University System ===
                    1. Faculties
                    2. Departments
                    3. Specialties
                    4. Students
                    5. Teachers
                    0. Exit
                    """);
            System.out.print("Choose option: ");
            int choice = readInt();
            switch (choice) {
                case 1 : new FacultyMenu(scanner, facultyService, teacherService, departmentService).start(); break;
                case 2 : new DepartmentMenu(scanner, departmentService, facultyService, teacherService).start(); break;
                case 3 : new SpecialtyMenu(scanner, specialtyService, departmentService).start(); break;
                case 4 : new StudentMenu(scanner, studentService, specialtyService).start(); break;
                case 5 : new TeacherMenu(scanner, teacherService, departmentService).start(); break;
                case 0 : running = false; break;
                default : System.out.println("Unknown option\n");
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
