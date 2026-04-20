package ua.edu.ukma.converter;

import ua.edu.ukma.domain.Specialty;
import ua.edu.ukma.domain.Student;
import ua.edu.ukma.domain.StudentStatus;
import ua.edu.ukma.domain.StudyForm;
import ua.edu.ukma.dto.StudentDTO;

import java.time.LocalDate;

public class StudentMapper {

    public static StudentDTO toDTO(Student s) {
        return new StudentDTO(
                s.getId(),
                s.getLastName(),
                s.getFirstName(),
                s.getMiddleName(),
                s.getGradeBookNumber(),
                s.getCourse(),
                s.getGroup(),
                s.getSpecialty().getId(),
                s.getAdmissionYear(),
                s.getStudyForm() != null ? s.getStudyForm().name() : null,
                s.getStatus() != null ? s.getStatus().name() : null,
                s.getBirthDate() != null ? s.getBirthDate().toString() : null,
                s.getEmail(),
                s.getPhone(),
                s.getAddress()
        );
    }

    public static Student fromDTO(StudentDTO dto, Specialty specialty) {
        Student student = new Student(
                dto.id(),
                dto.lastName(),
                dto.firstName(),
                dto.middleName(),
                dto.gradeBookNumber(),
                dto.course(),
                dto.group(),
                specialty
        );

        student.setAdmissionYear(dto.admissionYear());

        if (dto.studyForm() != null && !dto.studyForm().isBlank()) {
            student.setStudyForm(StudyForm.valueOf(dto.studyForm()));
        }
        if (dto.status() != null && !dto.status().isBlank()) {
            student.setStatus(StudentStatus.valueOf(dto.status()));
        }
        if (dto.birthDate() != null && !dto.birthDate().isBlank()) {
            student.setBirthDate(LocalDate.parse(dto.birthDate()));
        }

        student.setEmail(dto.email());
        student.setPhone(dto.phone());
        student.setAddress(dto.address());

        return student;
    }
}