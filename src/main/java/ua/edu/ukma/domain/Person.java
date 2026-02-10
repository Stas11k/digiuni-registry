package ua.edu.ukma.domain;

import ua.edu.ukma.util.ValidationUtils;

import java.util.Objects;

abstract class Person {
    private static int counter = 1;

    private final int id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String birthDate;
    private String email;
    private String phone;
    private String address;

    protected Person(String lastName, String firstName, String middleName) {
        this.id = counter++;
        ValidationUtils.validateNotEmpty(lastName, "Last name");
        ValidationUtils.validateNotEmpty(firstName, "First name");
        ValidationUtils.validateNotEmpty(middleName, "Middle name");
        ValidationUtils.validateNoDigits(lastName, "Last name");
        ValidationUtils.validateNoDigits(firstName, "First name");
        ValidationUtils.validateNoDigits(middleName, "Middle name");
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        ValidationUtils.validateNotEmpty(firstName, "First name");
        ValidationUtils.validateNoDigits(firstName, "First name");
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        ValidationUtils.validateNotEmpty(lastName, "Last name");
        ValidationUtils.validateNoDigits(lastName, "Last name");
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        ValidationUtils.validateNotEmpty(middleName, "Middle name");
        ValidationUtils.validateNoDigits(middleName, "Middle name");
        this.middleName = middleName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
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
