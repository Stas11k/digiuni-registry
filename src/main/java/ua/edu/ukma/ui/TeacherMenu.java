package ua.edu.ukma.ui;

import ua.edu.ukma.domain.Department;
import ua.edu.ukma.domain.Teacher;
import ua.edu.ukma.service.DepartmentService;
import ua.edu.ukma.service.TeacherService;
import java.time.LocalDate;

import java.util.Scanner;

public class TeacherMenu {

    private final Scanner scanner;
    private final TeacherService teacherService;
    private final DepartmentService departmentService;

    public TeacherMenu(Scanner scanner, TeacherService teacherService, DepartmentService departmentService) {
        this.scanner = scanner;
        this.teacherService = teacherService;
        this.departmentService = departmentService;
    }

    public void start() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("""
                    --- Teachers ---
                    1. Show all
                    2. Add
                    3. Edit
                    4. Delete
                    0. Back
                    """);
            System.out.print("Choose option: ");
            switch (readInt()) {
                case 1 : showAll(); break;
                case 2 : add(); break;
                case 3 : edit(); break;
                case 4 : delete(); break;
                case 0 : inMenu = false; break;
                default : System.out.println("Unknown option\n");
            }
        }
    }

    private void showAll() {
        for (Teacher t : teacherService.getAll()) {
            System.out.println(t.getId() + " | " + t.getFullName() + " | department: " + t.getDepartment() + " | position: " + t.getPosition() + " | degree: " + t.getDegree() + " | title: " + t.getTitle() + " | email: " + t.getEmail() + " | phone: " + t.getPhone() + " | address: " + t.getAddress() + " | birthDate: " + t.getBirthDate() + " | department: " + t.getDepartment() + " | hireDate: " + t.getHireDate() + " | workLoad: " + t.getWorkload());
        }
    }

    private void add() {
        if (departmentService.getAll().isEmpty()) {
            System.out.println("Create department first");
            return;
        }

        while (true) {
            try {
                System.out.print("Last name: ");
                String last = scanner.nextLine();
                System.out.print("First name: ");
                String first = scanner.nextLine();
                System.out.print("Middle name: ");
                String middle = scanner.nextLine();
                System.out.print("Position: ");
                String pos = scanner.nextLine();

                System.out.println("Choose department:");
                for (Department d : departmentService.getAll()) {
                    System.out.println(d.getId() + ": " + d.getName());
                }

                Department d = departmentService.get(readInt());
                if (d == null) {
                    System.out.println("Department not found\n");
                    continue;
                }

                teacherService.add(new Teacher(last, first, middle, pos, d));
                System.out.println("Teacher added");
                return;

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void edit() {
        while (true) {
            try {
                System.out.print("Teacher ID (or 0 to cancel): ");
                int id = readInt();
                if (id == 0) return;

                System.out.print("Last name: ");
                String last = scanner.nextLine();

                System.out.print("First name: ");
                String first = scanner.nextLine();

                System.out.print("Middle name: ");
                String middle = scanner.nextLine();

                System.out.print("Birth date: ");
                String birthDate = scanner.nextLine();

                System.out.print("Email: ");
                String email = scanner.nextLine();

                System.out.print("Phone: ");
                String phone = scanner.nextLine();

                System.out.print("Address: ");
                String address = scanner.nextLine();

                System.out.print("Position: ");
                String position = scanner.nextLine();

                System.out.println("Choose department:");
                for (Department d : departmentService.getAll()) {
                    System.out.println(d.getId() + ": " + d.getName());
                }
                Department department = departmentService.get(readInt());
                if (department == null) {
                    System.out.println("Department not found");
                    continue;
                }

                System.out.print("Degree: ");
                String degree = scanner.nextLine();

                System.out.print("Title: ");
                String title = scanner.nextLine();

                LocalDate hireDate = LocalDate.now();

                System.out.print("Workload: ");
                double workload = Double.parseDouble(scanner.nextLine());

                System.out.println(teacherService.update( id, last, first, middle, birthDate, email, phone, address, position, department, degree, title, hireDate, workload) ? "Updated" : "Not found");
                return;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void delete() {
        System.out.print("Teacher ID: ");
        System.out.println(teacherService.delete(readInt()) ? "Deleted" : "Not found");
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
