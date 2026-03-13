package ua.edu.ukma.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ua.edu.ukma.domain.Department;
import ua.edu.ukma.domain.Faculty;
import ua.edu.ukma.domain.Specialty;
import ua.edu.ukma.repository.InMemoryRepository;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SpecialtyServiceTest {


    @Test
    void addAndGetSpecialty() {
        InMemoryRepository<Specialty, Integer> repo = new InMemoryRepository<>();
        SpecialtyService service = new SpecialtyService(repo);

        Department department = new Department("Programming", new Faculty("FCS", "CS"));
        Specialty specialty = new Specialty("Software Engineering", department);

        service.add(specialty);
        assertEquals(1, service.getAll().size());
    }


    @Test
    void getOrThrow_shouldReturnSpecialty() {
        InMemoryRepository<Specialty, Integer> repo = new InMemoryRepository<>();
        SpecialtyService service = new SpecialtyService(repo);
        Department department = new Department("ComputerScience", new Faculty("FCS", "CS"));
        Specialty specialty = new Specialty("SoftwareEngineering", department);
        repo.save(specialty);
        Specialty result = service.getOrThrow(specialty.getId());
        assertEquals("SoftwareEngineering", result.getName());
    }

    @Test
    void getOrThrow_shouldThrowException() {
        InMemoryRepository<Specialty, Integer> repo = new InMemoryRepository<>();
        SpecialtyService service = new SpecialtyService(repo);
        Department department = new Department("ComputerScience", new Faculty("FCS", "CS"));
        Specialty specialty = new Specialty("SoftwareEngineering", department);
        repo.save(specialty);
        assertThrows(RuntimeException.class, () -> service.getOrThrow(100));

    }


    @Test
    void deleteSpecialty() {
        InMemoryRepository<Specialty, Integer> repo = new InMemoryRepository<>();
        SpecialtyService service = new SpecialtyService(repo);
        Department department = new Department("ComputerScience", new Faculty("FCS", "CS"));
        Specialty specialty = new Specialty("SoftwareEngineering", department);
        repo.save(specialty);
        service.delete(specialty.getId());
        assertTrue(service.getAll().isEmpty());

    }

    @Test
    void findByDepartmentTest() {
        InMemoryRepository<Specialty, Integer> repo = new InMemoryRepository<>();
        SpecialtyService service = new SpecialtyService(repo);

        Department d1 = new Department("ComputerScience", new Faculty("FCS","CS"));
        Department d2 = new Department("Math", new Faculty("FM","M"));

        Specialty s1 = new Specialty("SoftwareEngineering", d1);
        Specialty s2 = new Specialty("CyberSecurity", d1);
        Specialty s3 = new Specialty("Algebra", d2);

        repo.save(s1);
        repo.save(s2);
        repo.save(s3);

        List<Specialty> result = service.findByDepartment(d2.getId());

        assertEquals(1, result.size());

    }
    @ParameterizedTest
    @CsvSource({
            "1, 2",
            "2, 1"
    })
    void findByDepartmentParameterized(int departmentIndex, int expectedSize) {

        InMemoryRepository<Specialty, Integer> repo = new InMemoryRepository<>();
        SpecialtyService service = new SpecialtyService(repo);

        Faculty faculty = new Faculty("CS", "CS");

        Department d1 = new Department("Software Engineering", faculty);
        Department d2 = new Department("Mathematics", faculty);

        Specialty s1 = new Specialty("AI", d1);
        Specialty s2 = new Specialty("Cybersecurity", d1);
        Specialty s3 = new Specialty("Algebra", d2);

        repo.save(s1);
        repo.save(s2);
        repo.save(s3);

        int departmentId = (departmentIndex == 1) ? d1.getId() : d2.getId();

        List<Specialty> result = service.findByDepartment(departmentId);

        assertEquals(expectedSize, result.size());
    }

    @Test
    void updatePartialSpecialty() {
        InMemoryRepository<Specialty, Integer> repo = new InMemoryRepository<>();
        SpecialtyService service = new SpecialtyService(repo);

        Department department = new Department("ComputerScience", new Faculty("FCS","CS"));
        Specialty specialty = new Specialty("OldName", department);

        repo.save(specialty);

        service.updatePartial(
                specialty.getId(),
                Optional.of("NewName"),
                Optional.empty()
        );

        Specialty updated = service.getOrThrow(specialty.getId());

        assertEquals("NewName", updated.getName());

    }
}
