package ua.edu.ukma.ui;

import ua.edu.ukma.domain.Department;
import ua.edu.ukma.domain.Specialty;
import ua.edu.ukma.service.DepartmentService;
import ua.edu.ukma.service.SpecialtyService;

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

                System.out.println("Choose department:");
                for (Department d : departmentService.getAll()) {
                    System.out.println(d.getId() + ": " + d.getName());
                }

                System.out.print("Department ID: ");
                Department d = departmentService.get(readInt());

                if (d == null) {
                    System.out.println("Department not found\n");
                    continue;
                }

                specialtyService.add(new Specialty(name, d));
                System.out.println("Specialty added");
                return;

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void edit() {
        while (true) {
            try {
                System.out.print("Specialty ID (or 0 to cancel): ");
                int id = readInt();
                if (id == 0) return;

                System.out.print("New name: ");
                String name = scanner.nextLine();

                System.out.println("Choose department:");
                for (Department d : departmentService.getAll()) {
                    System.out.println(d.getId() + ": " + d.getName());
                }
                Department department = departmentService.get(readInt());
                if (department == null) {
                    System.out.println("Department not found");
                    continue;
                }

                System.out.println(specialtyService.update(id, name, department) ? "Updated" : "Not found");
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void delete() {
        System.out.print("Specialty ID: ");
        System.out.println(specialtyService.delete(readInt()) ? "Deleted" : "Not found");
    }

    private void showByDepartment() {
        if (departmentService.getAll().isEmpty()) {
            System.out.println("No departments available");
            return;
        }

        while (true) {
            try {
                System.out.println("Choose department (or 0 to cancel):");
                for (Department d : departmentService.getAll()) {
                    System.out.println(d.getId() + ": " + d.getName());
                }

                int departmentId = readInt();
                if (departmentId == 0) return;

                Department d = departmentService.get(departmentId);
                if (d == null)
                    throw new IllegalArgumentException("Department not found");

                for (Specialty s : specialtyService.findByDepartment(departmentId)) {
                    System.out.println(s.getId() + ": " + s.getName());
                }
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
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
