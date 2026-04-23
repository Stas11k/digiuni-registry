package ua.edu.ukma.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import ua.edu.ukma.exception.ValidationException;
import ua.edu.ukma.repository.Identifiable;
import ua.edu.ukma.validation.AnnotationValidator;
import ua.edu.ukma.validation.NoDigitsField;
import ua.edu.ukma.validation.NotBlankField;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Department implements Identifiable<Integer> {
    private static int counter = 1;

    @EqualsAndHashCode.Include
    private final int id;

    @NotBlankField(message = "Department name cannot be empty")
    @NoDigitsField(message = "Department name cannot contain digits")
    private String name;

    private Faculty faculty;
    private Teacher head;
    private String location;

    public Department(String name, Faculty faculty) {
        if (faculty == null) throw new ValidationException("Faculty cannot be null");
        this.id = counter++;
        this.name = name;
        this.faculty = faculty;
        AnnotationValidator.validate(this);
    }

    public Department(int id, String name, Faculty faculty) {
        if (faculty == null) throw new ValidationException("Faculty cannot be null");
        this.id = id;
        this.name = name;
        this.faculty = faculty;
        if (id >= counter) counter = id + 1;
        AnnotationValidator.validate(this);
    }

    public static void resetCounter() {
        counter = 1;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
        AnnotationValidator.validate(this);
    }

    public void setFaculty(Faculty faculty) {
        if (faculty == null) throw new ValidationException("Faculty cannot be null");
        this.faculty = faculty;
    }

    public void setHead(Teacher head) {
        this.head = head;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return name;
    }
}
