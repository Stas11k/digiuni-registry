package ua.edu.ukma.converter;

import ua.edu.ukma.domain.Department;
import ua.edu.ukma.domain.Teacher;
import ua.edu.ukma.dto.TeacherDTO;

import java.time.LocalDate;

public class TeacherMapper {

    public static TeacherDTO toDTO(Teacher t) {
        return new TeacherDTO(
                t.getId(),
                t.getLastName(),
                t.getFirstName(),
                t.getMiddleName(),
                t.getPosition(),
                t.getDepartment().getId(),
                t.getDegree(),
                t.getTitle(),
                t.getHireDate() != null ? t.getHireDate().toString() : null,
                t.getWorkload(),
                t.getBirthDate() != null ? t.getBirthDate().toString() : null,
                t.getEmail(),
                t.getPhone(),
                t.getAddress()
        );
    }

    public static Teacher fromDTO(TeacherDTO dto, Department department) {
        Teacher teacher = new Teacher(
                dto.id(),
                dto.lastName(),
                dto.firstName(),
                dto.middleName(),
                dto.position(),
                department
        );

        if (dto.degree() != null && !dto.degree().isBlank()) {
            teacher.setDegree(dto.degree());
        }
        if (dto.title() != null && !dto.title().isBlank()) {
            teacher.setTitle(dto.title());
        }
        if (dto.hireDate() != null && !dto.hireDate().isBlank()) {
            teacher.setHireDate(LocalDate.parse(dto.hireDate()));
        }
        teacher.setWorkload(dto.workload());

        if (dto.birthDate() != null && !dto.birthDate().isBlank()) {
            teacher.setBirthDate(LocalDate.parse(dto.birthDate()));
        }

        teacher.setEmail(dto.email());
        teacher.setPhone(dto.phone());
        teacher.setAddress(dto.address());

        return teacher;
    }
}