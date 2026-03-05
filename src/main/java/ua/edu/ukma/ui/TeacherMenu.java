package ua.edu.ukma.ui;

import ua.edu.ukma.auth.Role;
import ua.edu.ukma.domain.Department;
import ua.edu.ukma.domain.Teacher;
import ua.edu.ukma.exception.*;
import ua.edu.ukma.service.DepartmentService;
import ua.edu.ukma.service.TeacherService;
import java.time.LocalDate;

import java.util.Optional;
import java.util.Scanner;

public class TeacherMenu {

    private final Scanner scanner;
    private final TeacherService teacherService;
    private final DepartmentService departmentService;
    private final Role role;

    public TeacherMenu(Scanner scanner, TeacherService teacherService, DepartmentService departmentService, Role role) {
        this.scanner = scanner;
        this.teacherService = teacherService;
        this.departmentService = departmentService;
        this.role = role;
    }

    public void start() {
        boolean inMenu = true;
        while (inMenu) {
            if (canWrite()) {
                System.out.println("""
                        --- Teachers ---
                        1. Show all
                        2. Add
                        3. Edit
                        4. Delete
                        0. Back
                        """);
            }else{
                System.out.println("""
                        --- Teachers ---
                        1. Show all
                        0. Back
                        """);
            }
            System.out.print("Choose option: ");
            int choice = readInt();
            if (canWrite()) {
                switch (choice) {
                    case 1 -> showAll();
                    case 2 -> add();
                    case 3 -> edit();
                    case 4 -> delete();
                    case 0 -> inMenu = false;
                    default -> System.out.println("Unknown option\n");
                }
            }else{
                switch (choice) {
                    case 1 -> showAll();
                    case 0 -> inMenu = false;
                    default -> System.out.println("Unknown option\n");
                }
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

                Department department = chooseDepartment();

                teacherService.add(new Teacher(last, first, middle, pos, department));
                System.out.println("Teacher added");
                return;

            } catch (ValidationException | EntityNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void edit() {
        try {
            System.out.print("Teacher ID (or 0 to cancel): ");
            int id = readInt();
            if (id == 0) return;

            teacherService.getOrThrow(id);

            boolean editing = true;
            while (editing) {
                System.out.println("""
                        What do you want to edit
                        1. Last name
                        2. First name
                        3. Middle name
                        4. Birth date
                        5. Email
                        6. Phone
                        7. Address
                        8. Position
                        9. Department
                        10. Degree
                        11. Title
                        12. Hire date
                        13. Workload
                        90. Edit all fields
                        0. Back
                        """);
                System.out.print("Choose option: ");
                int c = readInt();

                Optional<String> lastName = Optional.empty();
                Optional<String> firstName = Optional.empty();
                Optional<String> middleName = Optional.empty();
                Optional<String> birthDate = Optional.empty();
                Optional<String> email = Optional.empty();
                Optional<String> phone = Optional.empty();
                Optional<String> address = Optional.empty();
                Optional<String> position = Optional.empty();
                Optional<Department> department = Optional.empty();
                Optional<String> degree = Optional.empty();
                Optional<String> title = Optional.empty();
                Optional<LocalDate> hireDate = Optional.empty();
                Optional<Double> workload = Optional.empty();

                switch (c) {
                    case 1:
                        lastName = Optional.of(readRequiredLine("New last name"));
                        break;
                    case 2:
                        firstName = Optional.of(readRequiredLine("New first name"));
                        break;
                    case 3:
                        middleName = Optional.of(readRequiredLine("New middle name"));
                        break;
                    case 4:
                        birthDate = Optional.of(readOptionalLine("New birth date (can be empty)"));
                        break;
                    case 5:
                        email = Optional.of(readOptionalLine("New email (can be empty)"));
                        break;
                    case 6:
                        phone = Optional.of(readOptionalLine("New phone (can be empty)"));
                        break;
                    case 7:
                        address = Optional.of(readOptionalLine("New address (can be empty)"));
                        break;
                    case 8:
                        position = Optional.of(readRequiredLine("New position"));
                        break;
                    case 9:
                        department = Optional.of(chooseDepartment());
                        break;
                    case 10:
                        degree = Optional.of(readOptionalLine("New degree (can be empty)"));
                        break;
                    case 11:
                        title = Optional.of(readOptionalLine("New title (can be empty)"));
                        break;
                    case 12:
                        hireDate = Optional.of(readDate("New hire date (YYYY-MM-DD)"));
                        break;
                    case 13:
                        workload = Optional.of(readDoubleValue("New workload"));
                        break;
                    case 90:
                        lastName = Optional.of(readRequiredLine("New last name"));
                        firstName = Optional.of(readRequiredLine("New first name"));
                        middleName = Optional.of(readRequiredLine("New middle name"));
                        birthDate = Optional.of(readOptionalLine("New birth date (can be empty)"));
                        email = Optional.of(readOptionalLine("New email (can be empty)"));
                        phone = Optional.of(readOptionalLine("New phone (can be empty)"));
                        address = Optional.of(readOptionalLine("New address (can be empty)"));
                        position = Optional.of(readRequiredLine("New position"));
                        department = Optional.of(chooseDepartment());
                        degree = Optional.of(readOptionalLine("New degree (can be empty)"));
                        title = Optional.of(readOptionalLine("New title (can be empty)"));
                        hireDate = Optional.of(readDate("New hire date (YYYY-MM-DD)"));
                        workload = Optional.of(readDoubleValue("New workload"));
                        break;
                    case 0:
                        editing = false;
                        continue;
                    default:
                        System.out.println("Unknown option\n");
                        continue;
                }

                teacherService.updatePartial(
                        id,
                        lastName, firstName, middleName,
                        birthDate, email, phone, address,
                        position, department, degree, title,
                        hireDate, workload
                );

                System.out.println("Updated\n");
            }

        } catch (ValidationException | EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private Department chooseDepartment() {
        if (departmentService.getAll().isEmpty()) throw new ValidationException("No departments available");

        while (true) {
            System.out.println("Choose department:");
            for (Department d : departmentService.getAll()) {
                System.out.println(d.getId() + ": " + d.getName());
            }
            System.out.print("Department ID: ");
            int id = readInt();

            try {
                return departmentService.getOrThrow(id);
            } catch (EntityNotFoundException e) {
                System.out.println("Error: " + e.getMessage() + "\n");
            }
        }
    }

    private void delete() {
        System.out.print("Teacher ID: ");
        System.out.println(teacherService.delete(readInt()) ? "Deleted" : "Not found");
    }

    private String readOptionalLine(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine();
    }

    private String readRequiredLine(String prompt) {
        while (true) {
            System.out.print(prompt + ": ");
            String v = scanner.nextLine();
            if (!v.isBlank()) return v;
            System.out.println("Value cannot be empty\n");
        }
    }

    private LocalDate readDate(String prompt) {
        while (true) {
            System.out.print(prompt + ": ");
            String s = scanner.nextLine().trim();
            try {
                return LocalDate.parse(s);
            } catch (Exception e) {
                System.out.println("Invalid date format. Example: 2026-03-03\n");
            }
        }
    }

    private double readDoubleValue(String prompt) {
        while (true) {
            System.out.print(prompt + ": ");
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number\n");
            }
        }
    }

    private boolean canWrite() {
        return role == Role.MANAGER || role == Role.ADMIN;
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
