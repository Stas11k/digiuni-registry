package ua.edu.ukma.ui;

import ua.edu.ukma.domain.*;
import ua.edu.ukma.service.DepartmentService;
import ua.edu.ukma.service.FacultyService;
import ua.edu.ukma.service.TeacherService;

import java.util.Scanner;

public class FacultyMenu {

    private final Scanner scanner;
    private final FacultyService service;
    private final DepartmentService departmentService;
    private final TeacherService teacherService;

    public FacultyMenu(Scanner scanner, FacultyService service, TeacherService teacherService, DepartmentService departmentService) {
        this.scanner = scanner;
        this.service = service;
        this.teacherService = teacherService;
        this.departmentService = departmentService;
    }

    public void start() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("""
                    --- Faculties ---
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

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void edit() {
        while (true) {
            try {
                System.out.print("Faculty ID (or 0 to cancel): ");
                int id = readInt();
                if (id == 0) return;

                System.out.print("New name: ");
                String name = scanner.nextLine();

                System.out.print("New short name: ");
                String shortName = scanner.nextLine();

                System.out.print("Contacts: ");
                String contacts = scanner.nextLine();

                if (teacherService.getAll().isEmpty()) {
                    System.out.println("No teachers available");
                    return;
                }

                System.out.println("Choose dean:");
                for (Teacher t : teacherService.getAll()) {
                    System.out.println(t.getId() + ": " + t.getFullName());
                }

                Teacher dean = teacherService.get(readInt());
                if (dean == null) {
                    System.out.println("Teacher not found");
                    continue;
                }

                if (!canBeDean(dean)) {
                    System.out.println("This teacher cannot be dean (already a dean or department head). Choose another one.");
                    continue;
                }

                System.out.println(service.update(id, name, shortName, dean, contacts) ? "Updated" : "Not found");
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void delete() {
        System.out.print("Faculty ID: ");
        System.out.println(service.delete(readInt()) ? "Deleted" : "Not found");
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
