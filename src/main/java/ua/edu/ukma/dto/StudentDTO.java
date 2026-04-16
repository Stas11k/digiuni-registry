package ua.edu.ukma.dto;

public record StudentDTO(
        int id,
        String lastName,
        String firstName,
        String middleName,
        String gradeBookNumber,
        int course,
        int group,
        int specialtyId,
        int admissionYear,
        String studyForm,
        String status,
        String birthDate,
        String email,
        String phone,
        String address
) {}