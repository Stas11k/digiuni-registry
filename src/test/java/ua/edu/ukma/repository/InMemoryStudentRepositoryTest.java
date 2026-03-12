package ua.edu.ukma.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.edu.ukma.domain.Department;
import ua.edu.ukma.domain.Faculty;
import ua.edu.ukma.domain.Specialty;
import ua.edu.ukma.domain.Student;

import static org.junit.jupiter.api.Assertions.*;


class InMemoryStudentRepositoryTest {

    private InMemoryStudentRepository repository;
    private Student student;

    @BeforeEach
    void setUp() {
        repository = new InMemoryStudentRepository();

        Faculty faculty = new Faculty("Faculty of Informatics", "FI");
        Department department = new Department("Computer Science", faculty);
        Specialty specialty = new Specialty("Software Engineering", department);

        student = new Student(
                "Shevchenko",
                "Taras",
                "Hryhorovych",
                "ST12345",
                2,
                1,
                specialty
        );
    }

    @Test
    void save_shouldStoreStudent() {
        repository.save(student);

        assertEquals(1, repository.findAll().size());
    }

    @Test
    void findById_shouldReturnStudent() {
        repository.save(student);

        Student found = repository.findById(student.getId());

        assertNotNull(found);
        assertEquals("ST12345", found.getGradeBookNumber());
    }

    @Test
    void deleteById_shouldRemoveStudent() {
        repository.save(student);
        int id = student.getId();

        boolean deleted = repository.deleteById(id);

        assertTrue(deleted);
        assertTrue(repository.findAll().isEmpty());
    }
}
