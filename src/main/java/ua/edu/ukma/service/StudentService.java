package ua.edu.ukma.service;
import ua.edu.ukma.domain.Student;
import ua.edu.ukma.repository.Repository;

import java.util.*;
import java.util.stream.Collectors;



public class StudentService {
    private final Repository<Student, UUID> repo;
    public StudentService(Repository<Student, UUID> repo) {
        this.repo = repo;
    }
    public void add(Student student) {
        validate(student);
        repo.save(student);
    }
    public Student get(UUID id) {
        return repo.findById(id);
    }
    public List<Student> getAll() {
        return repo.findAll();
    }
    public boolean delete(UUID id) {
        return repo.deleteById(id);
    }
    public List<Student> findByCourse(int course) {
        return repo.findAll().stream()
                .filter(s -> s.getCourse() == course)
                .toList();
    }
    public List<Student> findByGroup(String group) {
        return repo.findAll().stream()
                .filter(s -> s.getGroup().s.getGroup() == group)
                .toList();
    }
    public List<Student> sortedByCourse() {
        return repo.findAll().stream()
                .sorted(Comparator.comparing(Student::getCourse))
                .toList();
    }
    private void validate(Student s) {

        if (s.getFirstName().isBlank() || s.getLastName().isBlank())
            throw new IllegalArgumentException("Name cannot be empty");

        if (s.getCourse() < 1 || s.getCourse() > 6)
            throw new IllegalArgumentException("Invalid course");

        if (s.getGroup() <= 0)   // ← ЗАМІНА isBlank()
            throw new IllegalArgumentException("Invalid group number");
    }
}
