package ua.edu.ukma.ui;

import ua.edu.ukma.domain.Department;
import ua.edu.ukma.domain.Specialty;
import ua.edu.ukma.exception.*;
import ua.edu.ukma.service.DepartmentService;
import ua.edu.ukma.service.SpecialtyService;

import java.util.Optional;
import java.util.Scanner;

public class SpecialtyMenu {

    private final Scanner scanner;
    private final SpecialtyService specialtyService;
    private final DepartmentService departmentService;

    public SpecialtyMenu(Scanner scanner, SpecialtyService specialtyService, DepartmentService departmentService) {
        this.scanner = scanner;
        this.specialtyService = specialtyService;
        this.departmentService = departmentService;
    }

    public void start() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("""
                    --- Specialties ---
                    1. Show all
                    2. Add
                    3. Edit
                    4. Delete
                    5. Show by department
                    0. Back
                    """);
            System.out.print("Choose option: ");
            switch (readInt()) {
                case 1 : showAll(); break;
                case 2 : add(); break;
                case 3 : edit(); break;
                case 4 : delete(); break;
                case 5 : showByDepartment(); break;
                case 0 : inMenu = false; break;
                default : System.out.println("Unknown option\n");
            }
        }
    }

    private void showAll() {
        for (Specialty s : specialtyService.getAll()) {
            System.out.println(s.getId() + ": " + s.getName() + " | department: " + s.getDepartment().getName());
        }
    }

    private void add() {
        if (departmentService.getAll().isEmpty()) {
            System.out.println("Create department first");
            return;
        }

        while (true) {
            try {
                System.out.print("Specialty name (or 0 to cancel): ");
                String name = scanner.nextLine();
                if (name.equals("0")) return;

                Department d = chooseDepartment();

                specialtyService.add(new Specialty(name, d));
                System.out.println("Specialty added");
                return;

            } catch (ValidationException | EntityNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void edit() {
        try {
            System.out.print("Specialty ID (or 0 to cancel): ");
            int id = readInt();
            if (id == 0) return;

            specialtyService.getOrThrow(id);

            boolean editing = true;
            while (editing) {
                System.out.println("""
                        What do you want to edit
                        1. Name
                        2. Department
                        9. Edit all fields
                        0. Back
                        """);
                System.out.print("Choose option: ");
                int c = readInt();

                Optional<String> name = Optional.empty();
                Optional<Department> dept = Optional.empty();

                switch (c) {
                    case 1:
                        name = Optional.of(readRequiredLine("New name"));
                        break;
                    case 2:
                        dept = Optional.of(chooseDepartment());
                        break;
                    case 9:
                        name = Optional.of(readRequiredLine("New name"));
                        dept = Optional.of(chooseDepartment());
                        break;
                    case 0:
                        editing = false;
                        continue;
                    default:
                        System.out.println("Unknown option\n");
                        continue;
                }

                specialtyService.updatePartial(id, name, dept);
                System.out.println("Updated\n");
            }

        } catch (ValidationException | EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void delete() {
        System.out.print("Specialty ID: ");
        System.out.println(specialtyService.delete(readInt()) ? "Deleted" : "Not found");
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

    private void showByDepartment() {
        if (departmentService.getAll().isEmpty()) {
            System.out.println("No departments available");
            return;
        }

        try {
            System.out.println("Choose department:");
            for (Department d : departmentService.getAll()) {
                System.out.println(d.getId() + ": " + d.getName());
            }
            System.out.print("Department ID (or 0 to cancel): ");
            int id = readInt();
            if (id == 0) return;

            departmentService.getOrThrow(id);

            for (Specialty s : specialtyService.findByDepartment(id)) {
                System.out.println(s.getId() + ": " + s.getName());
            }

        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private String readRequiredLine(String prompt) {
        while (true) {
            System.out.print(prompt + ": ");
            String v = scanner.nextLine();
            if (!v.isBlank()) return v;
            System.out.println("Value cannot be empty\n");
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
