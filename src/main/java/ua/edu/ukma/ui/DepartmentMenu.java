package ua.edu.ukma.ui;

import ua.edu.ukma.auth.Role;
import ua.edu.ukma.domain.Department;
import ua.edu.ukma.domain.Faculty;
import ua.edu.ukma.domain.Teacher;
import ua.edu.ukma.exception.*;
import ua.edu.ukma.service.DepartmentService;
import ua.edu.ukma.service.FacultyService;
import ua.edu.ukma.service.TeacherService;

import java.util.Optional;
import java.util.Scanner;

public class DepartmentMenu {

    private final Scanner scanner;
    private final DepartmentService departmentService;
    private final FacultyService facultyService;
    private final TeacherService teacherService;
    private final Role role;

    public DepartmentMenu(Scanner scanner, DepartmentService departmentService, FacultyService facultyService, TeacherService teacherService, Role role) {
        this.scanner = scanner;
        this.departmentService = departmentService;
        this.facultyService = facultyService;
        this.teacherService = teacherService;
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
                    5. Show by faculty
                    0. Back
                    """);
            } else {
                System.out.println("""
                    --- Departments ---
                    1. Show all
                    5. Show by faculty
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
                    case 5 -> showByFaculty();
                    case 0 -> inMenu = false;
                    default -> System.out.println("Unknown option\n");
                }
            } else {
                switch (choice) {
                    case 1 -> showAll();
                    case 5 -> showByFaculty();
                    case 0 -> inMenu = false;
                    default -> System.out.println("Unknown option\n");
                }
            }
        }
    }

    private void showAll() {
        for (Department d : departmentService.getAll()) {
            System.out.println(d.getId() + ": " + d.getName() + " | faculty: " + d.getFaculty().getName() + " | head: " + d.getHead() + " | location: " + d.getLocation());
        }
    }

    private void add() {
        if (facultyService.getAll().isEmpty()) {
            System.out.println("Create faculty first");
            return;
        }

        while (true) {
            try {
                System.out.print("Department name (or 0 to cancel): ");
                String name = scanner.nextLine();
                if (name.equals("0")) return;

                Faculty faculty = chooseFaculty();

                departmentService.add(new Department(name, faculty));
                System.out.println("Department added");
                return;

            } catch (ValidationException | EntityNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void edit() {
        try {
            System.out.print("Department ID (or 0 to cancel): ");
            int id = readInt();
            if (id == 0) return;

            departmentService.getOrThrow(id);

            boolean editing = true;
            while (editing) {
                System.out.println("""
                        What do you want to edit
                        1. Name
                        2. Faculty
                        3. Head
                        4. Location
                        9. Edit all fields
                        0. Back
                        """);
                System.out.print("Choose option: ");
                int c = readInt();

                Optional<String> name = Optional.empty();
                Optional<Faculty> facultyOpt = Optional.empty();
                Optional<Teacher> headOpt = Optional.empty();
                Optional<String> location = Optional.empty();

                switch (c) {
                    case 1:
                        name = Optional.of(readRequiredLine("New name"));
                        break;
                    case 2:
                        facultyOpt = Optional.of(chooseFaculty());
                        break;
                    case 3:
                        headOpt = Optional.of(chooseHead(id));
                        break;
                    case 4:
                        location = Optional.of(readRequiredLine("New location"));
                        break;
                    case 9:
                        name = Optional.of(readRequiredLine("New name"));
                        facultyOpt = Optional.of(chooseFaculty());
                        headOpt = Optional.of(chooseHead(id));
                        location = Optional.of(readRequiredLine("New location"));
                        break;
                    case 0:
                        editing = false;
                        continue;
                    default:
                        System.out.println("Unknown option\n");
                        continue;
                }

                departmentService.updatePartial(id, name, facultyOpt, headOpt, location);
                System.out.println("Updated\n");
            }

        } catch (ValidationException | EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private Faculty chooseFaculty() {
        if (facultyService.getAll().isEmpty()) throw new ValidationException("No faculties available");

        while (true) {
            System.out.println("Choose faculty:");
            for (Faculty f : facultyService.getAll()) {
                System.out.println(f.getId() + ": " + f.getName());
            }

            System.out.print("Faculty ID: ");
            int id = readInt();

            try {
                return facultyService.getOrThrow(id);
            } catch (EntityNotFoundException e) {
                System.out.println("Error: " + e.getMessage() + "\n");
            }
        }
    }

    private Teacher chooseHead(int currentDepartmentId) {
        if (teacherService.getAll().isEmpty()) throw new ValidationException("No teachers available");

        while (true) {
            System.out.println("Choose head of department:");
            for (Teacher t : teacherService.getAll()) {
                System.out.println(t.getId() + ": " + t.getFullName());
            }
            System.out.print("Teacher ID: ");
            int id = readInt();

            Teacher head;
            try {
                head = teacherService.getOrThrow(id);
            } catch (EntityNotFoundException e) {
                System.out.println("Error: " + e.getMessage() + "\n");
                continue;
            }

            if (isDean(head)) {
                System.out.println("This teacher is already a dean. Choose another one.\n");
                continue;
            }
            if (isHeadOfAnotherDepartment(head, currentDepartmentId)) {
                System.out.println("This teacher is already head of another department.\n");
                continue;
            }
            return head;
        }
    }

    private void delete() {
        System.out.print("Department ID: ");
        System.out.println(departmentService.delete(readInt()) ? "Deleted" : "Not found");
    }

    private void showByFaculty() {
        if (facultyService.getAll().isEmpty()) {
            System.out.println("No faculties available");
            return;
        }

        Faculty faculty = chooseFaculty();
        int id = faculty.getId();

        for (Department d : departmentService.findByFaculty(id)) {
            System.out.println(d.getId() + ": " + d.getName() + " | location: " + d.getLocation());
        }
    }

    private boolean isDean(Teacher teacher) {
        for (Faculty f : facultyService.getAll()) {
            if (f.getDean() != null && f.getDean().getId() == teacher.getId()) {
                return true;
            }
        }
        return false;
    }

    private boolean isHeadOfAnotherDepartment(Teacher teacher, int currentDepartmentId) {
        for (Department d : departmentService.getAll()) {
            if (d.getHead() != null && d.getHead().getId() == teacher.getId() && d.getId() != currentDepartmentId) {
                return true;
            }
        }
        return false;
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
