package ua.edu.ukma.dto;

public record FacultyDTO(
        int id,
        String name,
        String shortName,
        Integer deanId,
        String contacts
) {}
