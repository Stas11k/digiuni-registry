package ua.edu.ukma.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) { super(message); }
}