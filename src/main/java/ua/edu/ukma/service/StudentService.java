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
        List<Student> result = new ArrayList<>();

        for (Student s : repo.findAll()) {
            if (s.getCourse() == course) {
                result.add(s);
            }
        }
        return result;
    }
    public List<Student> findByGroup(int group) {
        List<Student> result = new ArrayList<>();

        for (Student s : repo.findAll()) {
            if (s.getGroup() == group) {
                result.add(s);
            }
        }
        return result;
    }
    public List<Student> sortedByCourse() {
        List<Student> result = repo.findAll();

        for (int i = 0; i < result.size(); i++) {
            for (int j = i + 1; j < result.size(); j++) {
                if (result.get(i).getCourse() > result.get(j).getCourse()) {
                    Student tmp = result.get(i);
                    result.set(i, result.get(j));
                    result.set(j, tmp);
                }
            }
        }
        return result;
    }
    private void validate(Student s) {

        if (s.getFirstName().isBlank() || s.getLastName().isBlank())
            throw new IllegalArgumentException("Name cannot be empty");

        if (s.getCourse() < 1 || s.getCourse() > 6)
            throw new IllegalArgumentException("Invalid course");

        if (s.getGroup() <= 0)
            throw new IllegalArgumentException("Invalid group number");
    }
}
