package ua.edu.ukma.dto;

public record DepartmentDTO(
        int id,
        String name,
        int facultyId,
        Integer headId,
        String location
) {}
