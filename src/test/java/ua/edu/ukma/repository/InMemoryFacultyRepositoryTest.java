package ua.edu.ukma.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.edu.ukma.domain.Faculty;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryFacultyRepositoryTest {

    private InMemoryFacultyRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryFacultyRepository();
    }

    @Test
    void save_shouldStoreFaculty() {
        Faculty faculty = new Faculty("Faculty of Law", "LAW");

        repository.save(faculty);

        assertEquals(1, repository.findAll().size());
    }

    @Test
    void findById_shouldReturnFaculty() {
        Faculty faculty = new Faculty("Faculty of Law", "LAW");
        repository.save(faculty);

        Faculty found = repository.findById(faculty.getId());

        assertNotNull(found);
        assertEquals("LAW", found.getShortName());
    }

    @Test
    void deleteById_shouldReturnFalse_whenFacultyNotFound() {
        assertFalse(repository.deleteById(123));
    }
}
