package ua.edu.ukma.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.edu.ukma.domain.Department;
import ua.edu.ukma.domain.Faculty;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryDepartmentRepositoryTest {

    private InMemoryDepartmentRepository repository;
    private Faculty faculty;

    @BeforeEach
    void setUp() {
        repository = new InMemoryDepartmentRepository();
        faculty = new Faculty("Faculty of Informatics", "FI");
    }

    @Test
    void save_shouldStoreDepartment() {
        Department department = new Department("Computer Science", faculty);

        repository.save(department);

        assertEquals(1, repository.findAll().size());
    }

    @Test
    void findById_shouldReturnDepartment_whenExists() {
        Department department = new Department("Computer Science", faculty);
        repository.save(department);

        Department found = repository.findById(department.getId());

        assertNotNull(found);
        assertEquals("Computer Science", found.getName());
    }

    @Test
    void findById_shouldReturnNull_whenNotExists() {
        assertNull(repository.findById(999));
    }

    @Test
    void deleteById_shouldRemoveDepartment() {
        Department department = new Department("Computer Science", faculty);
        repository.save(department);

        boolean deleted = repository.deleteById(department.getId());

        assertTrue(deleted);
        assertTrue(repository.findAll().isEmpty());
    }
}
