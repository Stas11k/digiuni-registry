package ua.edu.ukma.converter;

import ua.edu.ukma.domain.Department;
import ua.edu.ukma.domain.Faculty;
import ua.edu.ukma.domain.Specialty;
import ua.edu.ukma.domain.Student;
import ua.edu.ukma.dto.StudentDTO;

public class StudentMapper {
    public static StudentDTO toDTO(Student s) {
        return new StudentDTO(
                s.getLastName(),
                s.getFirstName(),
                s.getMiddleName(),
                s.getCourse(),
                s.getGroup()
        );
    }

    public static Student fromDTO(StudentDTO dto) {
        Faculty faculty = new Faculty("Default Faculty", "DF");
        Department dept = new Department("Default Department", faculty);
        Specialty specialty = new Specialty("Default Specialty", dept);
        return new Student(
                dto.lastName(),
                dto.firstName(),
                dto.middleName(),
                "TEMP",
                dto.course(),
                dto.group(),
                specialty
        );
    }
}

