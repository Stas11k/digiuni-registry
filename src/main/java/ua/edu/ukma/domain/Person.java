package ua.edu.ukma.domain;

import java.util.Objects;
import java.util.UUID;

abstract class Person {
    private final UUID id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String birthDate;
    private String email;
    private String phone;
    private String address;

    protected Person(String lastName, String firstName, String middleName) {
        this.id = UUID.randomUUID();
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
    }
    public UUID getId() {
        return id;
    }
    public String getFullName() {
        return lastName + " " + firstName + " " + middleName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return id.equals(person.id);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
