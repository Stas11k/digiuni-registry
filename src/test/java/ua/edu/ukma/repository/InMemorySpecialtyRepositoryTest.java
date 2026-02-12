package ua.edu.ukma.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.edu.ukma.domain.Department;
import ua.edu.ukma.domain.Faculty;
import ua.edu.ukma.domain.Specialty;

import static org.junit.jupiter.api.Assertions.*;

class InMemorySpecialtyRepositoryTest {

    private InMemorySpecialtyRepository repository;
    private Department department;

    @BeforeEach
    void setUp() {
        repository = new InMemorySpecialtyRepository();
        Faculty faculty = new Faculty("Faculty of Informatics", "FI");
        department = new Department("Software Engineering", faculty);
    }

    @Test
    void save_shouldStoreSpecialty() {
        Specialty specialty = new Specialty("Computer Science", department);

        repository.save(specialty);

        assertEquals(1, repository.findAll().size());
    }

    @Test
    void findById_shouldReturnCorrectSpecialty() {
        Specialty specialty = new Specialty("Computer Science", department);
        repository.save(specialty);

        Specialty found = repository.findById(specialty.getId());

        assertNotNull(found);
        assertEquals("Computer Science", found.getName());
    }
}
