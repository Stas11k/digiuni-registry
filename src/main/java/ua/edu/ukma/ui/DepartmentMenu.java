package ua.edu.ukma.ui;

import ua.edu.ukma.domain.Department;
import ua.edu.ukma.domain.Faculty;
import ua.edu.ukma.domain.Teacher;
import ua.edu.ukma.service.DepartmentService;
import ua.edu.ukma.service.FacultyService;
import ua.edu.ukma.service.TeacherService;

import java.util.Scanner;

public class DepartmentMenu {

    private final Scanner scanner;
    private final DepartmentService departmentService;
    private final FacultyService facultyService;
    private final TeacherService teacherService;

    public DepartmentMenu(Scanner scanner, DepartmentService departmentService, FacultyService facultyService, TeacherService teacherService) {
        this.scanner = scanner;
        this.departmentService = departmentService;
        this.facultyService = facultyService;
        this.teacherService = teacherService;
    }

    public void start() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("""
                    --- Departments ---
                    1. Show all
                    2. Add
                    3. Edit
                    4. Delete
                    5. Show by faculty
                    0. Back
                    """);
            System.out.print("Choose option: ");
            switch (readInt()) {
                case 1 : showAll() ; break;
                case 2 : add(); break;
                case 3 : edit(); break;
                case 4 : delete(); break;
                case 5 : showByFaculty(); break;
                case 0 : inMenu = false; break;
                default : System.out.println("Unknown option\n");
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

                System.out.println("Choose faculty:");
                for (Faculty f : facultyService.getAll()) {
                    System.out.println(f.getId() + ": " + f.getName());
                }

                System.out.print("Faculty ID: ");
                int facultyId = readInt();

                Faculty faculty = facultyService.get(facultyId);
                if (faculty == null) {
                    System.out.println("Faculty not found\n");
                    continue;
                }

                departmentService.add(new Department(name, faculty));
                System.out.println("Department added");
                return;

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void edit() {
        while (true) {
            try {
                System.out.print("Department ID (or 0 to cancel): ");
                int id = readInt();
                if (id == 0) return;

                System.out.print("New name: ");
                String name = scanner.nextLine();

                System.out.println("Choose faculty:");
                for (Faculty f : facultyService.getAll()) {
                    System.out.println(f.getId() + ": " + f.getName());
                }
                Faculty faculty = facultyService.get(readInt());
                if (faculty == null) {
                    System.out.println("Faculty not found");
                    continue;
                }

                if (teacherService.getAll().isEmpty()) {
                    System.out.println("No teachers available");
                    return;
                }

                System.out.println("Choose head of department:");
                for (Teacher t : teacherService.getAll()) {
                    System.out.println(t.getId() + ": " + t.getFullName());
                }

                Teacher head = teacherService.get(readInt());
                if (head == null) {
                    System.out.println("Teacher not found");
                    continue;
                }

                if (isDean(head)) {
                    System.out.println("This teacher is already a dean. Choose another one.");
                    continue;
                }

                if (isHeadOfAnotherDepartment(head, id)) {
                    System.out.println("This teacher is already head of another department.");
                    continue;
                }

                System.out.print("Location: ");
                String location = scanner.nextLine();

                System.out.println(departmentService.update(id, name, faculty, head, location) ? "Updated" : "Not found");
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
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

        while (true) {
            try {
                System.out.println("Choose faculty (or 0 to cancel):");
                for (Faculty f : facultyService.getAll()) {
                    System.out.println(f.getId() + ": " + f.getName());
                }

                int facultyId = readInt();
                if (facultyId == 0) return;

                Faculty faculty = facultyService.get(facultyId);
                if (faculty == null) throw new IllegalArgumentException("Faculty not found");

                for (Department d : departmentService.findByFaculty(facultyId)) {
                    System.out.println(d.getId() + ": " + d.getName() + " | location: " + d.getLocation());
                }
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
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
