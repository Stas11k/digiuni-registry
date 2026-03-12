package ua.edu.ukma.ui;

import ua.edu.ukma.domain.Department;
import ua.edu.ukma.domain.Faculty;
import ua.edu.ukma.domain.Student;
import ua.edu.ukma.domain.Teacher;
import ua.edu.ukma.exception.EntityNotFoundException;
import ua.edu.ukma.exception.ValidationException;
import ua.edu.ukma.service.DepartmentService;
import ua.edu.ukma.service.FacultyService;
import ua.edu.ukma.service.StudentService;
import ua.edu.ukma.service.TeacherService;

import java.util.List;
import java.util.Scanner;

public class ReportMenu {

    private final Scanner scanner;
    private final FacultyService facultyService;
    private final DepartmentService departmentService;
    private final StudentService studentService;
    private final TeacherService teacherService;

    public ReportMenu(Scanner scanner,
                       FacultyService facultyService,
                       DepartmentService departmentService,
                       StudentService studentService,
                       TeacherService teacherService) {
        this.scanner = scanner;
        this.facultyService = facultyService;
        this.departmentService = departmentService;
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    public void start() {
        boolean inMenu = true;

        while (inMenu) {
            System.out.println("""
                    --- Reports ---
                    1. All students sorted by course
                    2. Faculty students sorted alphabetically
                    3. Faculty teachers sorted alphabetically
                    4. Department students sorted by course
                    5. Department students sorted alphabetically
                    6. Department teachers sorted alphabetically
                    7. Department students of a selected course
                    8. Department students of a selected course sorted alphabetically
                    0. Back
                    """);

            System.out.print("Choose option: ");
            int choice = readInt();

            switch (choice) {
                case 1 -> showAllStudentsSortedByCourse();
                case 2 -> showFacultyStudentsSortedByName();
                case 3 -> showFacultyTeachersSortedByName();
                case 4 -> showDepartmentStudentsSortedByCourse();
                case 5 -> showDepartmentStudentsSortedByName();
                case 6 -> showDepartmentTeachersSortedByName();
                case 7 -> showDepartmentStudentsByCourse();
                case 8 -> showDepartmentStudentsByCourseSortedByName();
                case 0 -> inMenu = false;
                default -> System.out.println("Unknown option\n");
            }
        }
    }

    private void showAllStudentsSortedByCourse() {
        List<Student> students = studentService.sortedByCourse();

        if (students.isEmpty()) {
            System.out.println("No students found\n");
            return;
        }

        for (Student s : students) {
            printStudentShort(s);
        }
        System.out.println();
    }

    private void showFacultyStudentsSortedByName() {
        if (facultyService.getAll().isEmpty()) {
            System.out.println("No faculties available\n");
            return;
        }

        try {
            Faculty faculty = chooseFaculty();
            List<Student> students = studentService.findByFacultySortedByName(faculty.getId());

            System.out.println("Students of faculty: " + faculty.getName());
            if (students.isEmpty()) {
                System.out.println("No students found\n");
                return;
            }

            for (Student s : students) {
                printStudentShort(s);
            }
            System.out.println();

        } catch (ValidationException | EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage() + "\n");
        }
    }

    private void showFacultyTeachersSortedByName() {
        if (facultyService.getAll().isEmpty()) {
            System.out.println("No faculties available\n");
            return;
        }

        try {
            Faculty faculty = chooseFaculty();
            List<Teacher> teachers = teacherService.findByFacultySortedByName(faculty.getId());

            System.out.println("Teachers of faculty: " + faculty.getName());
            if (teachers.isEmpty()) {
                System.out.println("No teachers found\n");
                return;
            }

            for (Teacher t : teachers) {
                printTeacherShort(t);
            }
            System.out.println();

        } catch (ValidationException | EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage() + "\n");
        }
    }

    private void showDepartmentStudentsSortedByCourse() {
        if (departmentService.getAll().isEmpty()) {
            System.out.println("No departments available\n");
            return;
        }

        try {
            Department department = chooseDepartment();
            List<Student> students = studentService.findByDepartmentSortedByCourse(department.getId());

            System.out.println("Students of department: " + department.getName());
            if (students.isEmpty()) {
                System.out.println("No students found\n");
                return;
            }

            for (Student s : students) {
                printStudentShort(s);
            }
            System.out.println();

        } catch (ValidationException | EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage() + "\n");
        }
    }

    private void showDepartmentStudentsSortedByName() {
        if (departmentService.getAll().isEmpty()) {
            System.out.println("No departments available\n");
            return;
        }

        try {
            Department department = chooseDepartment();
            List<Student> students = studentService.findByDepartmentSortedByName(department.getId());

            System.out.println("Students of department: " + department.getName());
            if (students.isEmpty()) {
                System.out.println("No students found\n");
                return;
            }

            for (Student s : students) {
                printStudentShort(s);
            }
            System.out.println();

        } catch (ValidationException | EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage() + "\n");
        }
    }

    private void showDepartmentTeachersSortedByName() {
        if (departmentService.getAll().isEmpty()) {
            System.out.println("No departments available\n");
            return;
        }

        try {
            Department department = chooseDepartment();
            List<Teacher> teachers = teacherService.findByDepartmentSortedByName(department.getId());

            System.out.println("Teachers of department: " + department.getName());
            if (teachers.isEmpty()) {
                System.out.println("No teachers found\n");
                return;
            }

            for (Teacher t : teachers) {
                printTeacherShort(t);
            }
            System.out.println();

        } catch (ValidationException | EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage() + "\n");
        }
    }

    private void showDepartmentStudentsByCourse() {
        if (departmentService.getAll().isEmpty()) {
            System.out.println("No departments available\n");
            return;
        }

        try {
            Department department = chooseDepartment();
            int course = readCourse();
            List<Student> students = studentService.findByDepartmentAndCourse(department.getId(), course);

            System.out.println("Students of department " + department.getName() + ", course " + course);
            if (students.isEmpty()) {
                System.out.println("No students found\n");
                return;
            }

            for (Student s : students) {
                printStudentShort(s);
            }
            System.out.println();

        } catch (ValidationException | EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage() + "\n");
        }
    }

    private void showDepartmentStudentsByCourseSortedByName() {
        if (departmentService.getAll().isEmpty()) {
            System.out.println("No departments available\n");
            return;
        }

        try {
            Department department = chooseDepartment();
            int course = readCourse();
            List<Student> students = studentService.findByDepartmentAndCourseSortedByName(department.getId(), course);

            System.out.println("Students of department " + department.getName() + ", course " + course + " sorted alphabetically");
            if (students.isEmpty()) {
                System.out.println("No students found\n");
                return;
            }

            for (Student s : students) {
                printStudentShort(s);
            }
            System.out.println();

        } catch (ValidationException | EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage() + "\n");
        }
    }

    private Faculty chooseFaculty() {
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

    private Department chooseDepartment() {
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

    private int readCourse() {
        while (true) {
            System.out.print("Course: ");
            int course = readInt();
            if (course >= 1 && course <= 6) {
                return course;
            }
            System.out.println("Course must be between 1 and 6\n");
        }
    }

    private void printStudentShort(Student s) {
        System.out.println(
                s.getId() + " | " +
                        s.getFullName() +
                        " | course: " + s.getCourse() +
                        " | group: " + s.getGroup() +
                        " | specialty: " + s.getSpecialty()
        );
    }

    private void printTeacherShort(Teacher t) {
        System.out.println(
                t.getId() + " | " +
                        t.getFullName() +
                        " | position: " + t.getPosition() +
                        " | department: " + t.getDepartment()
        );
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