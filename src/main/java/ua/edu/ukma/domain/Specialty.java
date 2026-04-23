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
public class Specialty implements Identifiable<Integer> {
    private static int counter = 1;

    @EqualsAndHashCode.Include
    private final int id;

    @NotBlankField(message = "Specialty name cannot be empty")
    @NoDigitsField(message = "Specialty name cannot contain digits")
    private String name;

    private Department department;

    public Specialty(String name, Department department) {
        if (department == null) throw new ValidationException("Department cannot be null");
        this.id = counter++;
        this.name = name;
        this.department = department;
        AnnotationValidator.validate(this);
    }

    public Specialty(int id, String name, Department department) {
        if (department == null) throw new ValidationException("Department cannot be null");
        this.id = id;
        this.name = name;
        this.department = department;
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

    public void setDepartment(Department department) {
        if (department == null) throw new ValidationException("Department cannot be null");
        this.department = department;
    }

    @Override
    public String toString() {
        return name;
    }
}
