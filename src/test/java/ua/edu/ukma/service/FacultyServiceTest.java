package ua.edu.ukma.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.edu.ukma.domain.Department;
import ua.edu.ukma.domain.Faculty;
import ua.edu.ukma.domain.Teacher;
import ua.edu.ukma.repository.InMemoryRepository;

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
