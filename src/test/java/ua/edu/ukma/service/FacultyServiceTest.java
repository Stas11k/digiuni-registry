package ua.edu.ukma.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ua.edu.ukma.domain.*;
import ua.edu.ukma.io.AsyncSaveService;
import ua.edu.ukma.io.DataContext;
import ua.edu.ukma.repository.InMemoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;



class FacultyServiceTest {
    InMemoryRepository<Faculty, Integer> facultyRepo = new InMemoryRepository<>();
    InMemoryRepository<Department, Integer> departmentRepo = new InMemoryRepository<>();
    InMemoryRepository<Specialty, Integer> specialtyRepo = new InMemoryRepository<>();
    InMemoryRepository<Teacher, Integer> teacherRepo = new InMemoryRepository<>();
    InMemoryRepository<Student, Integer> studentRepo = new InMemoryRepository<>();

    University university = new University("NaUKMA", "NaUKMA", "Kyiv", "Address");

    DataContext dataContext = new DataContext(
            facultyRepo,
            departmentRepo,
            specialtyRepo,
            teacherRepo,
            studentRepo,
            university
    );
    AsyncSaveService saveService = new AsyncSaveService() {
        @Override
        public CompletableFuture<Void> saveAsync(DataContext dataContext) {

            return CompletableFuture.completedFuture(null);
        }
    };
    @Test
    void addAndGetFaculty() {

        InMemoryRepository<Faculty, Integer> repo = new InMemoryRepository<>();


        FacultyService service = new FacultyService(facultyRepo,saveService,dataContext);
        Faculty faculty = new Faculty("Computer Science", "CS");
        service.add(faculty);
        assertEquals("Computer Science", service.find(faculty.getId()).get().getName());
    }
    @Test
    void sortedByNameTest() {
        InMemoryRepository<Faculty, Integer> repo = new InMemoryRepository<>();
        FacultyService service = new FacultyService(facultyRepo,saveService,dataContext);

        facultyRepo.save(new Faculty("Mathematics", "M"));
        facultyRepo.save(new Faculty("Computer Science", "CS"));

        List<Faculty> result = service.sortedByName();

        assertEquals("Computer Science", result.get(0).getName());
    }
    @ParameterizedTest
    @CsvSource({
            "Mathematics, Computer Science, Computer Science",
            "Physics, Mathematics, Mathematics"
    })
    void sortedByNameParameterized(String name1, String name2, String expectedFirst) {
        FacultyService service = new FacultyService(facultyRepo,saveService,dataContext);

        facultyRepo.save(new Faculty(name1, "A"));
        facultyRepo.save(new Faculty(name2, "B"));

        List<Faculty> result = service.sortedByName();

        assertEquals(expectedFirst, result.get(0).getName());
    }


    @Test
    void findFacultyById() {
        FacultyService service = new FacultyService(facultyRepo,saveService,dataContext);
        Faculty f = new Faculty("Computer Science", "CS");
        facultyRepo.save(f);
        assertTrue(service.find(f.getId()).isPresent());

    }
    @Test
    void getOrThrowFacultyTest() {
        FacultyService service = new FacultyService(facultyRepo,saveService,dataContext);
        assertThrows(RuntimeException.class, () -> service.getOrThrow(100));
    }
    @Test
    void deleteFacultyById() {
        InMemoryRepository<Faculty, Integer> repo = new InMemoryRepository<>();
        FacultyService service = new FacultyService(facultyRepo,saveService,dataContext);
        Faculty faculty = new Faculty("Computer Science", "CS");
        repo.save(faculty);
        service.delete(faculty.getId());
        assertFalse(service.find(faculty.getId()).isPresent());
    }
    @Test
    void updatePartialFaculty() {
        FacultyService service = new FacultyService(facultyRepo,saveService,dataContext);
        Faculty faculty = new Faculty("Computer Science", "CS");

        facultyRepo.save(faculty);

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