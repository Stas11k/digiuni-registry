package ua.edu.ukma.converter;

import ua.edu.ukma.domain.Specialty;
import ua.edu.ukma.domain.Student;
import ua.edu.ukma.dto.StudentDTO;

public class StudentMapper {

    public static StudentDTO toDTO(Student s) {
        return new StudentDTO(
                s.getId(),
                s.getLastName(),
                s.getFirstName(),
                s.getMiddleName(),
                s.getCourse(),
                s.getGroup(),
                s.getSpecialty() != null ? s.getSpecialty().getName() : null,
                s.getSpecialty() != null && s.getSpecialty().getDepartment() != null
                        ? s.getSpecialty().getDepartment().getName() : null,
                s.getSpecialty() != null && s.getSpecialty().getDepartment() != null
                        && s.getSpecialty().getDepartment().getFaculty() != null
                        ? s.getSpecialty().getDepartment().getFaculty().getName() : null
        );
    }

    public static Student fromDTO(StudentDTO dto, Specialty specialty) {
        return new Student(
                dto.id(),
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