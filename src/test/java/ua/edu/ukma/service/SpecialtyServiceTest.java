package ua.edu.ukma.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.edu.ukma.domain.Department;
import ua.edu.ukma.domain.Faculty;
import ua.edu.ukma.domain.Specialty;
import ua.edu.ukma.repository.InMemorySpecialtyRepository;

import static org.junit.jupiter.api.Assertions.*;

class SpecialtyServiceTest {

    private SpecialtyService service;

    @BeforeEach
    void setUp() {
        InMemorySpecialtyRepository repo = new InMemorySpecialtyRepository();
        service = new SpecialtyService(repo);
    }

    // helper: валідна спеціальність
    private Specialty validSpecialty() {
        Faculty f = new Faculty(
                "Computer Science",
                "CS"
        );

        Department d = new Department(
                "Programming",
                f
        );

        return new Specialty(
                "Software Engineering",
                d
        );
    }

    @Test
    void addSpecialty_shouldAddSpecialty() {
        service.add(validSpecialty());

        assertEquals(1, service.getAll().size());
    }

    @Test
    void getAll_shouldReturnAllSpecialties() {
        service.add(validSpecialty());
        service.add(validSpecialty());

        assertEquals(2, service.getAll().size());
    }

    @Test
    void deleteSpecialty_shouldRemoveSpecialty() {
        Specialty s = validSpecialty();
        service.add(s);

        service.delete(s.getId());

        assertEquals(0, service.getAll().size());
    }

    @Test
    void findByDepartment_shouldReturnCorrectSpecialties() {
        Specialty s = validSpecialty();
        service.add(s);

        int departmentId = s.getDepartment().getId();

        assertEquals(1, service.findByDepartment(departmentId).size());
    }

    @Test
    void addSpecialty_emptyName_shouldThrowException() {
        Faculty f = new Faculty("CS", "CS");
        Department d = new Department("Programming", f);

        Specialty s = new Specialty("", d);

        assertThrows(IllegalArgumentException.class,
                () -> service.add(s));
    }

    @Test
    void addSpecialty_nullDepartment_shouldThrowException() {
        Specialty s = new Specialty(
                "Software Engineering",
                null
        );

        assertThrows(IllegalArgumentException.class,
                () -> service.add(s));
    }
}
