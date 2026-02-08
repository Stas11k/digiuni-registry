package ua.edu.ukma.ui;

import ua.edu.ukma.domain.*;
import ua.edu.ukma.repository.*;
import ua.edu.ukma.service.*;

import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {

    private final Scanner scanner = new Scanner(System.in);
    private boolean running = true;

    private final FacultyService facultyService;
    private final DepartmentService departmentService;
    private final SpecialtyService specialtyService;
    private final StudentService studentService;
    private final TeacherService teacherService;

    public ConsoleMenu() {
        facultyService = new FacultyService(new InMemoryFacultyRepository());
        departmentService = new DepartmentService(new InMemoryDepartmentRepository());
        specialtyService = new SpecialtyService(new InMemorySpecialtyRepository());
        studentService = new StudentService(new InMemoryStudentRepository());
        teacherService = new TeacherService(new InMemoryTeacherRepository());
    }

    public void start() {
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

            int choice = readInt();
            switch (choice) {
                case 1 -> facultiesMenu();
                case 2 -> departmentsMenu();
                case 3 -> specialtiesMenu();
                case 4 -> studentsMenu();
                case 5 -> teachersMenu();
                case 0 -> exit();
                default -> System.out.println("Unknown option");
            }
        }
    }

    private void facultiesMenu() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("""
                    --- Faculties ---
                    1. Show all
                    0. Back
                    """);

            int choice = readInt();
            switch (choice) {
                case 1 -> {
                    List<Faculty> faculties = facultyService.getAll();
                    for (int i = 0; i < faculties.size(); i++) {
                        Faculty f = faculties.get(i);
                        System.out.println(
                                f.getId() + ": " +
                                        f.getName() + " (" + f.getShortName() + ")"
                        );
                    }
                }
                case 0 -> inMenu = false;
                default -> System.out.println("Unknown option");
            }
        }
    }

    private void departmentsMenu() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("""
                    --- Departments ---
                    1. Show all
                    0. Back
                    """);

            int choice = readInt();
            switch (choice) {
                case 1 -> {
                    List<Department> departments = departmentService.getAll();
                    for (int i = 0; i < departments.size(); i++) {
                        Department d = departments.get(i);
                        System.out.println(
                                d.getId() + ": " +
                                        d.getName() +
                                        " | faculty: " + d.getFaculty().getName()
                        );
                    }
                }
                case 0 -> inMenu = false;
                default -> System.out.println("Unknown option");
            }
        }
    }

    private void specialtiesMenu() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("""
                    --- Specialties ---
                    1. Show all
                    0. Back
                    """);

            int choice = readInt();
            switch (choice) {
                case 1 -> {
                    List<Specialty> specialties = specialtyService.getAll();
                    for (int i = 0; i < specialties.size(); i++) {
                        Specialty s = specialties.get(i);
                        System.out.println(
                                s.getId() + ": " +
                                        s.getName() +
                                        " | department: " + s.getDepartment().getName()
                        );
                    }
                }
                case 0 -> inMenu = false;
                default -> System.out.println("Unknown option");
            }
        }
    }

    private void studentsMenu() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("""
                    --- Students ---
                    1. Show all
                    0. Back
                    """);

            int choice = readInt();
            switch (choice) {
                case 1 -> {
                    List<Student> students = studentService.getAll();
                    for (int i = 0; i < students.size(); i++) {
                        Student s = students.get(i);
                        System.out.println(
                                s.getStudentId() + " | " +
                                        s.getFullName() +
                                        " | course: " + s.getCourse() +
                                        " | group: " + s.getGroup()
                        );
                    }
                }
                case 0 -> inMenu = false;
                default -> System.out.println("Unknown option");
            }
        }
    }

    private void teachersMenu() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("""
                    --- Teachers ---
                    1. Show all
                    0. Back
                    """);

            int choice = readInt();
            switch (choice) {
                case 1 -> {
                    List<Teacher> teachers = teacherService.getAll();
                    for (int i = 0; i < teachers.size(); i++) {
                        Teacher t = teachers.get(i);
                        System.out.println(
                                t.getFullName() +
                                        " | position: " + t.getPosition()
                        );
                    }
                }
                case 0 -> inMenu = false;
                default -> System.out.println("Unknown option");
            }
        }
    }

    private int readInt() {
        while (!scanner.hasNextInt()) {
            scanner.next();
        }
        return scanner.nextInt();
    }

    private void exit() {
        running = false;
        System.out.println("Goodbye!");
    }
}
