package ua.edu.ukma.ui;

import ua.edu.ukma.auth.Role;
import ua.edu.ukma.domain.*;
import ua.edu.ukma.exception.*;
import ua.edu.ukma.service.DepartmentService;
import ua.edu.ukma.service.FacultyService;
import ua.edu.ukma.service.TeacherService;

import java.util.Optional;
import java.util.Scanner;

public class FacultyMenu {

    private final Scanner scanner;
    private final FacultyService service;
    private final DepartmentService departmentService;
    private final TeacherService teacherService;
    private final Role role;

    public FacultyMenu(Scanner scanner, FacultyService service, TeacherService teacherService, DepartmentService departmentService, Role role) {
        this.scanner = scanner;
        this.service = service;
        this.teacherService = teacherService;
        this.departmentService = departmentService;
        this.role = role;
    }

    public void start() {
        boolean inMenu = true;
        while (inMenu) {
            if (canWrite()) {
                System.out.println("""
                    --- Departments ---
                    1. Show all
                    2. Add
                    3. Edit
                    4. Delete
                    0. Back
                    """);
            } else {
                System.out.println("""
                    --- Departments ---
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
            } else {
                switch (choice) {
                    case 1 -> showAll();
                    case 0 -> inMenu = false;
                    default -> System.out.println("Unknown option\n");
                }
            }
        }
    }

    private void showAll() {
        for (Faculty f : service.getAll()) {
            System.out.println(f.getId() + ": " + f.getName() + " (" + f.getShortName() + ")" + " |  dean: " + f.getDean() + " | contacts: " + f.getContacts());
        }
    }

    private void add() {
        while (true) {
            try {
                System.out.print("Name (or 0 to cancel): ");
                String name = scanner.nextLine();
                if (name.equals("0")) return;

                System.out.print("Short name: ");
                String shortName = scanner.nextLine();

                service.add(new Faculty(name, shortName));
                System.out.println("Faculty added");
                return;

            } catch (ValidationException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void edit() {
        try {
            System.out.print("Faculty ID (or 0 to cancel): ");
            int id = readInt();
            if (id == 0) return;

            // Перевіряємо що існує
            service.getOrThrow(id);

            boolean editing = true;
            while (editing) {
                System.out.println("""
                        What do you want to edit
                        1. Name
                        2. Short name
                        3. Contacts
                        4. Dean
                        9. Edit all fields
                        0. Back
                        """);
                System.out.print("Choose option: ");
                int c = readInt();

                Optional<String> name = Optional.empty();
                Optional<String> shortName = Optional.empty();
                Optional<Teacher> dean = Optional.empty();
                Optional<String> contacts = Optional.empty();

                switch (c) {
                    case 1:
                        name = Optional.of(readRequiredLine("New name"));
                        break;
                    case 2:
                        shortName = Optional.of(readRequiredLine("New short name"));
                        break;
                    case 3:
                        contacts = Optional.of(readRequiredLine("New contacts"));
                        break;
                    case 4:
                        dean = Optional.of(chooseDean());
                        break;
                    case 9:
                        name = Optional.of(readRequiredLine("New name"));
                        shortName = Optional.of(readRequiredLine("New short name"));
                        contacts = Optional.of(readRequiredLine("New contacts"));
                        dean = Optional.of(chooseDean());
                        break;
                    case 0:
                        editing = false;
                        continue;
                    default:
                        System.out.println("Unknown option\n");
                        continue;
                }

                service.updatePartial(id, name, shortName, dean, contacts);
                System.out.println("Updated\n");
            }

        } catch (ValidationException | EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void delete() {
        System.out.print("Faculty ID: ");
        System.out.println(service.delete(readInt()) ? "Deleted" : "Not found");
    }

    private Teacher chooseDean() {
        if (teacherService.getAll().isEmpty()) throw new ValidationException("No teachers available");

        while (true) {
            System.out.println("Choose dean:");
            for (Teacher t : teacherService.getAll()) {
                System.out.println(t.getId() + ": " + t.getFullName());
            }
            System.out.print("Teacher ID: ");
            int id = readInt();

            Teacher dean;
            try {
                dean = teacherService.getOrThrow(id);
            } catch (EntityNotFoundException e) {
                System.out.println("Error: " + e.getMessage() + "\n");
                continue;
            }

            if (!canBeDean(dean)) {
                System.out.println("This teacher cannot be dean (already a dean or department head). Choose another one.\n");
                continue;
            }
            return dean;
        }
    }

    private boolean canBeDean(Teacher teacher) {
        for (Faculty f : service.getAll()) {
            if (f.getDean() != null && f.getDean().getId() == teacher.getId()) return false;
        }
        for (Department d : departmentService.getAll()) {
            if (d.getHead() != null && d.getHead().getId() == teacher.getId()) return false;
        }
        return true;
    }

    private String readRequiredLine(String prompt) {
        while (true) {
            System.out.print(prompt + ": ");
            String v = scanner.nextLine();
            if (!v.isBlank()) return v;
            System.out.println("Value cannot be empty\n");
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
