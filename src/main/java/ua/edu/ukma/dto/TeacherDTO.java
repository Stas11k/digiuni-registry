package ua.edu.ukma.dto;

public record TeacherDTO(
        int id,
        String lastName,
        String firstName,
        String middleName,
        String position,
        int departmentId,
        String degree,
        String title,
        String hireDate,
        double workload,
        String birthDate,
        String email,
        String phone,
        String address
) {}