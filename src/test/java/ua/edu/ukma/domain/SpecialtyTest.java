package ua.edu.ukma.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpecialtyTest {
    private Department createDepartment() {
        Faculty faculty = new Faculty("Computer Science", "CS");
        return new Department("Programming", faculty);
    }
    @Test
    void constructor_validData_shouldCreateSpecialty() {
        Department department = createDepartment();

        Specialty specialty = new Specialty("Software Engineering", department);

        assertNotNull(specialty);
        assertEquals("Software Engineering", specialty.getName());
        assertEquals(department, specialty.getDepartment());
    }

    @Test
    void constructor_emptyName_shouldThrowException() {
        Department department = createDepartment();

        assertThrows(IllegalArgumentException.class,
                () -> new Specialty("", department));
    }
    @Test
    void setDepartment_shouldUpdateDepartment() {
        Faculty faculty = new Faculty("Computer Science", "CS");
        Department oldDepartment = new Department("Programming", faculty);
        Department newDepartment = new Department("Applied Math", faculty);

        Specialty specialty = new Specialty("Software Engineering", oldDepartment);

        specialty.setDepartment(newDepartment);

        assertEquals(newDepartment, specialty.getDepartment());
    }


}
