package ua.edu.ukma.repository;

import org.junit.jupiter.api.Test;
import ua.edu.ukma.domain.Department;
import ua.edu.ukma.domain.Faculty;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryRepositoryTest {
    @Test
    void saveEntityTest() {

        InMemoryRepository<Department, Integer> repo = new InMemoryRepository<>();

        Faculty faculty = new Faculty("FCS","CS");
        Department department = new Department("SE", faculty);

        repo.save(department);

        assertEquals(1, repo.findAll().size());
    }
    @Test
    void findByIdTest() {
        InMemoryRepository<Department, Integer> repo = new InMemoryRepository<>();

        Faculty faculty = new Faculty("FCS","CS");
        Department department = new Department("SE", faculty);

        repo.save(department);

        Optional<Department> result = repo.findById(department.getId());

        assertTrue(result.isPresent());
    }
    @Test
    void findAllTest() {
        InMemoryRepository<Department, Integer> repo = new InMemoryRepository<>();
        Faculty faculty = new Faculty("FCS","CS");
        Department department = new Department("SE", faculty);

        repo.save(department);
        List<Department> result = repo.findAll();
        assertEquals(1, result.size());
    }
    @Test
    void deleteByIdTest() {
        InMemoryRepository<Department, Integer> repo = new InMemoryRepository<>();
        Faculty faculty = new Faculty("FCS","CS");
        Department department = new Department("SE", faculty);
        repo.save(department);
        repo.deleteById(department.getId());
        assertTrue(repo.findById(department.getId()).isEmpty());
    }
}