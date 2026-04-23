package ua.edu.ukma.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import ua.edu.ukma.exception.ValidationException;
import ua.edu.ukma.repository.Identifiable;
import ua.edu.ukma.validation.AnnotationValidator;
import ua.edu.ukma.validation.NoDigitsField;
import ua.edu.ukma.validation.NotBlankField;

import java.time.LocalDate;
import java.time.Period;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract sealed class Person implements Identifiable<Integer> permits Student, Teacher {

    @EqualsAndHashCode.Include
    private final int id;

    @NotBlankField(message = "Last name cannot be empty")
    @NoDigitsField(message = "Last name cannot contain digits")
    private String lastName;

    @NotBlankField(message = "First name cannot be empty")
    @NoDigitsField(message = "First name cannot contain digits")
    private String firstName;

    @NotBlankField(message = "Middle name cannot be empty")
    @NoDigitsField(message = "Middle name cannot contain digits")
    private String middleName;

    transient private LocalDate birthDate;
    private String email;
    private String phone;
    private String address;

    protected Person(int id, String lastName, String firstName, String middleName) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        AnnotationValidator.validate(this);
    }

    public int getAge() {
        if (birthDate == null) return 0;
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        AnnotationValidator.validate(this);
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
        AnnotationValidator.validate(this);
    }

    public void setBirthDate(LocalDate birthDate) {
        if (birthDate != null && birthDate.isAfter(LocalDate.now())) {
            throw new ValidationException("Birth date cannot be in the future");
        }
        this.birthDate = birthDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullName() {
        return lastName + " " + firstName + " " + middleName;
    }

    @Override
    public String toString() {
        return getFullName();
    }
}
