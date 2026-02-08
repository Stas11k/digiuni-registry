package ua.edu.ukma.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.edu.ukma.domain.Faculty;
import ua.edu.ukma.repository.InMemoryFacultyRepository;
import ua.edu.ukma.repository.Repository;

import static org.junit.jupiter.api.Assertions.*;

class FacultyServiceTest {

    private FacultyService service;

    @BeforeEach
    void setUp() {
        Repository<Faculty, Integer> repo = new InMemoryFacultyRepository();
        service = new FacultyService(repo);
    }

    // helper: валідний факультет
    private Faculty validFaculty() {
        return new Faculty(
                "Computer Science",
                "CS"
        );
    }

    @Test
    void addFaculty_shouldAddFaculty() {
        service.add(validFaculty());

        assertEquals(1, service.getAll().size());
    }

    @Test
    void getAll_shouldReturnAllFaculties() {
        service.add(validFaculty());
        service.add(new Faculty("Law", "LAW"));

        assertEquals(2, service.getAll().size());
    }

    @Test
    void deleteFaculty_shouldRemoveFaculty() {
        Faculty f = validFaculty();
        service.add(f);

        service.delete(f.getId());

        assertEquals(0, service.getAll().size());
    }

    @Test
    void addFaculty_emptyName_shouldThrowException() {
        Faculty f = new Faculty("", "CS");

        assertThrows(IllegalArgumentException.class,
                () -> service.add(f));
    }
}