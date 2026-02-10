package ua.edu.ukma.ui;

import ua.edu.ukma.domain.Specialty;
import ua.edu.ukma.domain.Student;
import ua.edu.ukma.domain.StudyForm;
import ua.edu.ukma.domain.StudentStatus;
import ua.edu.ukma.service.SpecialtyService;
import ua.edu.ukma.service.StudentService;

import java.util.Scanner;

public class StudentMenu {

    private final Scanner scanner;
    private final StudentService studentService;
    private final SpecialtyService specialtyService;

    public StudentMenu(Scanner scanner, StudentService studentService, SpecialtyService specialtyService) {
        this.scanner = scanner;
        this.studentService = studentService;
        this.specialtyService = specialtyService;
    }

    public void start() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("""
                    --- Students ---
                    1. Show all
                    2. Add
                    3. Edit
                    4. Delete
                    5. Find by course
                    6. Find by group
                    0. Back
                    """);
            System.out.print("Choose option: ");
            switch (readInt()) {
                case 1 : showAll(); break;
                case 2 : add(); break;
                case 3 : edit(); break;
                case 4 : delete(); break;
                case 5 : findByCourse(); break;
                case 6 : findByGroup(); break;
                case 0 : inMenu = false; break;
                default : System.out.println("Unknown option\n");
            }
        }
    }

    private void showAll() {
        for (Student s : studentService.getAll()) {
            System.out.println(s.getId() + " | " + s.getFullName() + " | gradeBookNumber: " + s.getGradeBookNumber() + " | specialty: " + s.getSpecialty() +" | course: " + s.getCourse() + " | group: " + s.getGroup() + " | email: " + s.getEmail() + " | phone: " + s.getPhone() + " | address: " + s.getAddress() + " | birthDate: " + s.getBirthDate() + " | admissionYear: " + s.getAdmissionYear() + " | studyForm: " + s.getStudyForm() + " | status: " + s.getStatus());
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

                System.out.println("Choose specialty:");
                for (Specialty s : specialtyService.getAll()) {
                    System.out.println(s.getId() + ": " + s.getName());
                }

                Specialty sp = specialtyService.get(readInt());
                if (sp == null) {
                    System.out.println("Specialty not found\n");
                    continue;
                }

                studentService.add(new Student(last, first, middle, sid, course, group, sp));
                System.out.println("Student added");
                return;

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void edit() {
        while (true) {
            try {
                System.out.print("Student ID (or 0 to cancel): ");
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

                System.out.print("Grade book: ");
                String gradeBook = scanner.nextLine();

                System.out.print("Course: ");
                int course = readInt();

                System.out.print("Group: ");
                int group = readInt();

                System.out.println("Choose specialty:");
                for (Specialty s : specialtyService.getAll()) {
                    System.out.println(s.getId() + ": " + s.getName());
                }
                Specialty specialty = specialtyService.get(readInt());
                if (specialty == null) {
                    System.out.println("Specialty not found");
                    continue;
                }

                System.out.print("Admission year: ");
                int year = readInt();

                StudyForm studyForm = chooseStudyForm();
                StudentStatus status = chooseStatus();

                System.out.println(studentService.update(id, last, first, middle, birthDate, email, phone, address, gradeBook, course, group, specialty, year, studyForm, status) ? "Updated" : "Not found");
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void delete() {
        System.out.print("Student ID: ");
        System.out.println(studentService.delete(readInt()) ? "Deleted" : "Not found");
    }

    private void findByCourse() {
        while (true) {
            try {
                System.out.print("Course (or 0 to cancel): ");
                int course = readInt();
                if (course == 0) return;

                if (course < 1 || course > 6)
                    throw new IllegalArgumentException("Invalid course");

                for (Student s : studentService.findByCourse(course)) {
                    System.out.println(s.getId() + " | " + s.getFullName() + " | group " + s.getGroup());
                }
                return;
            } catch (IllegalArgumentException e) {
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

                if (group <= 0)
                    throw new IllegalArgumentException("Invalid group");

                for (Student s : studentService.findByGroup(group)) {
                    System.out.println(s.getId() + " | " + s.getFullName() + " | course " + s.getCourse());
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
