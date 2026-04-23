package ua.edu.ukma.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import ua.edu.ukma.repository.Identifiable;
import ua.edu.ukma.validation.AnnotationValidator;
import ua.edu.ukma.validation.NoDigitsField;
import ua.edu.ukma.validation.NotBlankField;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Faculty implements Identifiable<Integer> {
    private static int counter = 1;

    @EqualsAndHashCode.Include
    private final int id;

    @NotBlankField(message = "Faculty name cannot be empty")
    @NoDigitsField(message = "Faculty name cannot contain digits")
    private String name;

    @NotBlankField(message = "Faculty short name cannot be empty")
    @NoDigitsField(message = "Faculty short name cannot contain digits")
    private String shortName;

    private Teacher dean;
    private String contacts;

    public Faculty(String name, String shortName) {
        this.id = counter++;
        this.name = name;
        this.shortName = shortName;
        AnnotationValidator.validate(this);
    }

    public Faculty(int id, String name, String shortName) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
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

    public void setShortName(String shortName) {
        this.shortName = shortName;
        AnnotationValidator.validate(this);
    }

    public void setDean(Teacher dean) {
        this.dean = dean;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return name;
    }
}
