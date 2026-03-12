package ua.edu.ukma.ui;

import ua.edu.ukma.auth.Role;
import ua.edu.ukma.domain.Specialty;
import ua.edu.ukma.domain.Student;
import ua.edu.ukma.domain.StudyForm;
import ua.edu.ukma.domain.StudentStatus;
import ua.edu.ukma.exception.EntityNotFoundException;
import ua.edu.ukma.exception.ValidationException;
import ua.edu.ukma.service.SpecialtyService;
import ua.edu.ukma.service.StudentService;

import java.util.Optional;
import java.util.Scanner;

public class StudentMenu {

    private final Scanner scanner;
    private final StudentService studentService;
    private final SpecialtyService specialtyService;
    private final Role role;

    public StudentMenu(Scanner scanner, StudentService studentService, SpecialtyService specialtyService, Role role) {
        this.scanner = scanner;
        this.studentService = studentService;
        this.specialtyService = specialtyService;
        this.role = role;
    }

    public void start() {
        boolean inMenu = true;
        while (inMenu) {
            if (canWrite()) {
                System.out.println("""
                        --- Students ---
                        1. Show all
                        2. Add
                        3. Edit
                        4. Delete
                        5. Find by full name
                        6. Find by course
                        7. Find by group
                        0. Back
                        """);
            } else {
                System.out.println("""
                        --- Students ---
                        1. Show all
                        5. Find by full name
                        6. Find by course
                        7. Find by group
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
                    case 5 -> findByFullName();
                    case 6 -> findByCourse();
                    case 7 -> findByGroup();
                    case 0 -> inMenu = false;
                    default -> System.out.println("Unknown option\n");
                }
            } else {
                switch (choice) {
                    case 1 -> showAll();
                    case 5 -> findByFullName();
                    case 6 -> findByCourse();
                    case 7 -> findByGroup();
                    case 0 -> inMenu = false;
                    default -> System.out.println("Unknown option\n");
                }
            }
        }
    }

    private void showAll() {
        for (Student s : studentService.getAll()) {
            System.out.println(
                    s.getId() + " | " +
                            s.getFullName() +
                            " | age: " + s.getAge() +
                            " | yearsOfStudy: " + s.getYearsOfStudy() +
                            " | gradeBookNumber: " + s.getGradeBookNumber() +
                            " | specialty: " + s.getSpecialty() +
                            " | course: " + s.getCourse() +
                            " | group: " + s.getGroup() +
                            " | email: " + s.getEmail() +
                            " | phone: " + s.getPhone() +
                            " | address: " + s.getAddress() +
                            " | birthDate: " + s.getBirthDate() +
                            " | admissionYear: " + s.getAdmissionYear() +
                            " | studyForm: " + s.getStudyForm() +
                            " | status: " + s.getStatus()
            );
        }
    }

    private void add() {
        if (specialtyService.getAll().isEmpty()) {
            System.out.println("Create specialty first");
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

                System.out.print("Grade book number: ");
                String sid = scanner.nextLine();

                System.out.print("Course: ");
                int course = readInt();

                System.out.print("Group: ");
                int group = readInt();

                Specialty specialty = chooseSpecialty();

                Student student = new Student(last, first, middle, sid, course, group, specialty);
                studentService.add(student);

                System.out.println("Student added");
                return;

            } catch (ValidationException | EntityNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void edit() {
        try {
            System.out.print("Student ID (or 0 to cancel): ");
            int id = readInt();
            if (id == 0) return;

            studentService.getOrThrow(id);

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
                        8. Grade book number
                        9. Course
                        10. Group
                        11. Specialty
                        12. Admission year
                        13. Study form
                        14. Status
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
                Optional<String> gradeBook = Optional.empty();
                Optional<Integer> course = Optional.empty();
                Optional<Integer> group = Optional.empty();
                Optional<Specialty> specialty = Optional.empty();
                Optional<Integer> admissionYear = Optional.empty();
                Optional<StudyForm> studyForm = Optional.empty();
                Optional<StudentStatus> status = Optional.empty();

                switch (c) {
                    case 1 -> lastName = Optional.of(readRequiredLine("New last name"));
                    case 2 -> firstName = Optional.of(readRequiredLine("New first name"));
                    case 3 -> middleName = Optional.of(readRequiredLine("New middle name"));
                    case 4 -> birthDate = Optional.of(readOptionalLine("New birth date (YYYY-MM-DD, can be empty)"));
                    case 5 -> email = Optional.of(readOptionalLine("New email (can be empty)"));
                    case 6 -> phone = Optional.of(readOptionalLine("New phone (can be empty)"));
                    case 7 -> address = Optional.of(readOptionalLine("New address (can be empty)"));
                    case 8 -> gradeBook = Optional.of(readRequiredLine("New grade book number"));
                    case 9 -> course = Optional.of(readIntValue("New course"));
                    case 10 -> group = Optional.of(readIntValue("New group"));
                    case 11 -> specialty = Optional.of(chooseSpecialty());
                    case 12 -> admissionYear = Optional.of(readIntValue("New admission year"));
                    case 13 -> studyForm = Optional.of(chooseStudyForm());
                    case 14 -> status = Optional.of(chooseStatus());
                    case 90 -> {
                        lastName = Optional.of(readRequiredLine("New last name"));
                        firstName = Optional.of(readRequiredLine("New first name"));
                        middleName = Optional.of(readRequiredLine("New middle name"));
                        birthDate = Optional.of(readOptionalLine("New birth date (YYYY-MM-DD, can be empty)"));
                        email = Optional.of(readOptionalLine("New email (can be empty)"));
                        phone = Optional.of(readOptionalLine("New phone (can be empty)"));
                        address = Optional.of(readOptionalLine("New address (can be empty)"));
                        gradeBook = Optional.of(readRequiredLine("New grade book number"));
                        course = Optional.of(readIntValue("New course"));
                        group = Optional.of(readIntValue("New group"));
                        specialty = Optional.of(chooseSpecialty());
                        admissionYear = Optional.of(readIntValue("New admission year"));
                        studyForm = Optional.of(chooseStudyForm());
                        status = Optional.of(chooseStatus());
                    }
                    case 0 -> {
                        editing = false;
                        continue;
                    }
                    default -> {
                        System.out.println("Unknown option\n");
                        continue;
                    }
                }

                studentService.updatePartial(
                        id,
                        lastName, firstName, middleName,
                        birthDate, email, phone, address,
                        gradeBook, course, group, specialty,
                        admissionYear, studyForm, status
                );

                System.out.println("Updated\n");
            }

        } catch (ValidationException | EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: invalid date format\n");
        }
    }

    private void delete() {
        System.out.print("Student ID: ");
        System.out.println(studentService.delete(readInt()) ? "Deleted" : "Not found");
    }

    private void findByFullName() {
        while (true) {
            try {
                System.out.print("Full name or part of name (or 0 to cancel): ");
                String fullName = scanner.nextLine().trim();
                if (fullName.equals("0")) return;
                for (Student s : studentService.findByFullName(fullName)) {
                    System.out.println(s.getId() + " | " + s.getFullName() + " | course " + s.getCourse() + " | group " + s.getGroup() + " | specialty: " + s.getSpecialty());
                }
                return;
            } catch (ValidationException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void findByCourse() {
        while (true) {
            try {
                System.out.print("Course (or 0 to cancel): ");
                int course = readInt();
                if (course == 0) return;

                for (Student s : studentService.findByCourse(course)) {
                    System.out.println(s.getId() + " | " + s.getFullName() + " | group " + s.getGroup());
                }
                return;

            } catch (ValidationException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void findByGroup() {
        while (true) {
            try {
                System.out.print("Group (or 0 to cancel): ");
                int group = readInt();
                if (group == 0) return;

                for (Student s : studentService.findByGroup(group)) {
                    System.out.println(s.getId() + " | " + s.getFullName() + " | course " + s.getCourse());
                }
                return;

            } catch (ValidationException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private Specialty chooseSpecialty() {
        if (specialtyService.getAll().isEmpty()) {
            throw new ValidationException("No specialties available");
        }

        while (true) {
            System.out.println("Choose specialty:");
            for (Specialty s : specialtyService.getAll()) {
                System.out.println(s.getId() + ": " + s.getName());
            }
            System.out.print("Specialty ID: ");
            int id = readInt();

            try {
                return specialtyService.getOrThrow(id);
            } catch (EntityNotFoundException e) {
                System.out.println("Error: " + e.getMessage() + "\n");
            }
        }
    }

    private String readOptionalLine(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine();
    }

    private int readIntValue(String prompt) {
        while (true) {
            System.out.print(prompt + ": ");
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number\n");
            }
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

    private StudyForm chooseStudyForm() {
        while (true) {
            System.out.println("Choose study form:");
            StudyForm[] forms = StudyForm.values();

            for (int i = 0; i < forms.length; i++) {
                System.out.println((i + 1) + ". " + forms[i]);
            }

            int choice = readInt();
            if (choice >= 1 && choice <= forms.length) return forms[choice - 1];

            System.out.println("Invalid choice\n");
        }
    }

    private StudentStatus chooseStatus() {
        while (true) {
            System.out.println("Choose student status:");
            StudentStatus[] statuses = StudentStatus.values();
            for (int i = 0; i < statuses.length; i++) {
                System.out.println((i + 1) + ". " + statuses[i]);
            }
            int choice = readInt();
            if (choice >= 1 && choice <= statuses.length) return statuses[choice - 1];
            System.out.println("Invalid choice\n");
        }
    }
}