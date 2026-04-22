package ua.edu.ukma.domain;

import ua.edu.ukma.validation.AnnotationValidator;
import ua.edu.ukma.validation.NotBlankField;

public class University {

    @NotBlankField(message = "Full name cannot be empty")
    private String fullName;

    private String shortName;
    private String city;
    private String address;

    public University(String fullName, String shortName, String city, String address) {
        this.fullName = fullName;
        this.shortName = shortName;
        this.city = city;
        this.address = address;
        AnnotationValidator.validate(this);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
        AnnotationValidator.validate(this);
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
