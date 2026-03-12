package ua.edu.ukma.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ua.edu.ukma.domain.Department;
import ua.edu.ukma.domain.Faculty;
import ua.edu.ukma.domain.Teacher;
import ua.edu.ukma.repository.InMemoryRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;



class FacultyServiceTest {

    @Test
    void addAndGetFaculty() {
        InMemoryRepository<Faculty, Integer> repo = new InMemoryRepository<>();
        FacultyService service = new FacultyService(repo);
        Faculty faculty = new Faculty("Computer Science", "CS");
        service.add(faculty);
        assertEquals("Computer Science", service.find(faculty.getId()).get().getName());
    }
    @Test
    void sortedByNameTest() {
        InMemoryRepository<Faculty, Integer> repo = new InMemoryRepository<>();
        FacultyService service = new FacultyService(repo);

        repo.save(new Faculty("Mathematics", "M"));
        repo.save(new Faculty("Computer Science", "CS"));

        List<Faculty> result = service.sortedByName();

        assertEquals("Computer Science", result.get(0).getName());
    }
    @ParameterizedTest
    @CsvSource({
            "Mathematics, Computer Science, Computer Science",
            "Physics, Mathematics, Mathematics"
    })
    void sortedByNameParameterized(String name1, String name2, String expectedFirst) {

        InMemoryRepository<Faculty, Integer> repo = new InMemoryRepository<>();
        FacultyService service = new FacultyService(repo);

        repo.save(new Faculty(name1, "A"));
        repo.save(new Faculty(name2, "B"));

        List<Faculty> result = service.sortedByName();

        assertEquals(expectedFirst, result.get(0).getName());
    }


    @Test
    void findFacultyById() {
        InMemoryRepository<Faculty, Integer> repo = new InMemoryRepository<>();
        FacultyService service = new FacultyService(repo);
        Faculty f = new Faculty("Computer Science", "CS");
        repo.save(f);
        assertTrue(service.find(f.getId()).isPresent());

    }
    @Test
    void getOrThrowFacultyTest() {
        InMemoryRepository<Faculty, Integer> repo = new InMemoryRepository<>();
        FacultyService service = new FacultyService(repo);
        assertThrows(RuntimeException.class, () -> service.getOrThrow(100));
    }
    @Test
    void deleteFacultyById() {
        InMemoryRepository<Faculty, Integer> repo = new InMemoryRepository<>();
        FacultyService service = new FacultyService(repo);
        Faculty faculty = new Faculty("Computer Science", "CS");
        repo.save(faculty);
        service.delete(faculty.getId());
        assertFalse(service.find(faculty.getId()).isPresent());
    }
    @Test
    void updatePartialFaculty() {
        InMemoryRepository<Faculty, Integer> repo = new InMemoryRepository<>();
        FacultyService service = new FacultyService(repo);
        Faculty faculty = new Faculty("Computer Science", "CS");

        repo.save(faculty);

        service.updatePartial(
                faculty.getId(), Optional.of("NewName"),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        );
        Faculty updated = service.getOrThrow(faculty.getId());
        assertEquals("NewName", updated.getName());


    }
}