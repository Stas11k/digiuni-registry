package ua.edu.ukma.domain;

import ua.edu.ukma.exception.ValidationException;
import ua.edu.ukma.validation.AnnotationValidator;
import ua.edu.ukma.validation.NoDigitsField;
import ua.edu.ukma.validation.NotBlankField;

import java.time.LocalDate;
import java.time.Period;

public final class Teacher extends Person {
    private static int counter = 1;

    private Department department;

    @NotBlankField(message = "Position cannot be empty")
    @NoDigitsField(message = "Position cannot contain digits")
    private String position;

    @NoDigitsField(message = "Degree cannot contain digits")
    private String degree;

    private String title;
    private LocalDate hireDate;
    private double workload;

    public Teacher(String lastName, String firstName, String middleName, String position, Department department) {
        super(counter++, lastName, firstName, middleName);
        if (department == null) throw new ValidationException("Department cannot be null");
        this.position = position;
        this.department = department;
        this.hireDate = LocalDate.now();

        AnnotationValidator.validate(this);
    }

    public Teacher(int id, String lastName, String firstName, String middleName, String position, Department department) {
        super(id, lastName, firstName, middleName);
        if (department == null) throw new ValidationException("Department cannot be null");
        this.position = position;
        this.department = department;
        this.hireDate = LocalDate.now();

        if (id >= counter) counter = id + 1;
        AnnotationValidator.validate(this);
    }

    public static void resetCounter() {
        counter = 1;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        if (department == null) throw new ValidationException("Department cannot be null");
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
        AnnotationValidator.validate(this);
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
        AnnotationValidator.validate(this);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        if (hireDate == null || hireDate.isAfter(LocalDate.now())) throw new ValidationException("Invalid hire date");
        this.hireDate = hireDate;
    }

    public double getWorkload() {
        return workload;
    }

    public void setWorkload(double workload) {
        if (workload < 0) throw new ValidationException("Workload cannot be negative");
        this.workload = workload;
    }

    public int getExperienceYears() {
        if (hireDate == null) return 0;
        return Period.between(hireDate, LocalDate.now()).getYears();
    }
}
