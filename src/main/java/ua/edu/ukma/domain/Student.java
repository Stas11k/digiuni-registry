package ua.edu.ukma.domain;

import ua.edu.ukma.exception.ValidationException;
import ua.edu.ukma.validation.AnnotationValidator;
import ua.edu.ukma.validation.NotBlankField;
import ua.edu.ukma.validation.PositiveNumber;
import ua.edu.ukma.validation.RangeInt;

import java.time.LocalDate;

public final class Student extends Person {
    private static int counter = 1;

    @NotBlankField(message = "Grade book number cannot be empty")
    private String gradeBookNumber;

    @RangeInt(min = 1, max = 6, message = "Course must be between 1 and 6")
    private int course;

    private Specialty specialty;

    @PositiveNumber(message = "Group must be a positive number")
    private int group;

    private int admissionYear;
    private StudyForm studyForm;
    private StudentStatus status;

    public Student(String lastName, String firstName, String middleName, String gradeBookNumber, int course, int group, Specialty specialty) {
        super(counter++, lastName, firstName, middleName);
        if (specialty == null) throw new ValidationException("Specialty cannot be null");
        this.gradeBookNumber = gradeBookNumber;
        this.course = course;
        this.group = group;
        this.specialty = specialty;
        this.status = StudentStatus.STUDYING;

        AnnotationValidator.validate(this);
    }

    public Student(int id, String lastName, String firstName, String middleName, String gradeBookNumber, int course, int group, Specialty specialty) {
        super(id, lastName, firstName, middleName);
        if (specialty == null) throw new ValidationException("Specialty cannot be null");
        this.gradeBookNumber = gradeBookNumber;
        this.course = course;
        this.group = group;
        this.specialty = specialty;
        this.status = StudentStatus.STUDYING;

        if (id >= counter) counter = id + 1;
        AnnotationValidator.validate(this);
    }

    public static void resetCounter() {
        counter = 1;
    }

    public String getGradeBookNumber() {
        return gradeBookNumber;
    }

    public void setGradeBookNumber(String gradeBookNumber) {
        this.gradeBookNumber = gradeBookNumber;
        AnnotationValidator.validate(this);
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
        AnnotationValidator.validate(this);
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        if (specialty == null) throw new ValidationException("Specialty cannot be null");
        this.specialty = specialty;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
        AnnotationValidator.validate(this);
    }

    public int getAdmissionYear() {
        return admissionYear;
    }

    public void setAdmissionYear(int admissionYear) {
        if (admissionYear < 1900) throw new ValidationException("Invalid admission year");
        this.admissionYear = admissionYear;
    }

    public int getYearsOfStudy() {
        if (admissionYear <= 0) return 0;
        int years = LocalDate.now().getYear() - admissionYear;
        return Math.max(years, 0);
    }

    public StudyForm getStudyForm() {
        return studyForm;
    }

    public void setStudyForm(StudyForm studyForm) {
        if (studyForm == null) throw new ValidationException("Study form cannot be null");
        this.studyForm = studyForm;
    }

    public StudentStatus getStatus() {
        return status;
    }

    public void setStatus(StudentStatus status) {
        if (status == null) throw new ValidationException("Student status cannot be null");
        this.status = status;
    }
}
