package ua.edu.ukma.dto;

public record UserDTO(
        int id,
        String login,
        String password,
        String role,
        int permissions,
        boolean blocked
) {}