package ua.edu.ukma.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.edu.ukma.domain.Department;
import ua.edu.ukma.domain.Faculty;
import ua.edu.ukma.domain.Teacher;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTeacherRepositoryTest {

    private InMemoryTeacherRepository repository;
    private Teacher teacher;

    @BeforeEach
    void setUp() {
        repository = new InMemoryTeacherRepository();

        Faculty faculty = new Faculty("Faculty of Informatics", "FI");
        Department department = new Department("Computer Science", faculty);

        teacher = new Teacher(
                "Ivanenko",
                "Ivan",
                "Ivanovych",
                "Professor",
                department
        );
    }

    @Test
    void save_shouldStoreTeacher() {
        repository.save(teacher);

        assertEquals(1, repository.findAll().size());
    }

    @Test
    void findById_shouldReturnTeacher() {
        repository.save(teacher);

        Teacher found = repository.findById(teacher.getId());

        assertNotNull(found);
        assertEquals("Professor", found.getPosition());
    }
}
