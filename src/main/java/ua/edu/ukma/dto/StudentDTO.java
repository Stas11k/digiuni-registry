package ua.edu.ukma.dto;

public record StudentDTO(
        int id,
        String lastName,
        String firstName,
        String middleName,
        int course,
        int group,
        String specialtyName,
        String departmentName,
        String facultyName
) {}
