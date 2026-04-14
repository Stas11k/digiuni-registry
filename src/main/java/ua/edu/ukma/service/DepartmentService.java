package ua.edu.ukma.service;

import ua.edu.ukma.domain.Department;
import ua.edu.ukma.domain.Faculty;
import ua.edu.ukma.domain.Teacher;
import ua.edu.ukma.exception.*;
import ua.edu.ukma.repository.Repository;

import java.util.*;

public class DepartmentService {

    private final Repository<Department, Integer> repo;

    public DepartmentService(Repository<Department, Integer> repo) {
        this.repo = repo;
    }

    public void add(Department d) {
        validate(d);
        repo.save(d);
    }

    public Optional<Department> find(int id) {
        return repo.findById(id);
    }

    public Department getOrThrow(int id) {
        Optional<Department> opt = repo.findById(id);
        if (opt.isEmpty()) {
            throw new EntityNotFoundException("Department with id " + id + " not found");
        }
        return opt.get();
    }

    public List<Department> getAll() {
        return repo.findAll();
    }

    public boolean delete(int id) {
        return repo.deleteById(id);
    }

    public List<Department> findByFaculty(int facultyId) {
        return repo.findAll().stream()
                .filter(d -> d.getFaculty() != null && d.getFaculty().getId() == facultyId)
                .toList();
    }

    private void validate(Department d) {
        if (d == null) throw new ValidationException("Department cannot be null");
        if (d.getName() == null || d.getName().isBlank()) throw new ValidationException("Department name cannot be empty");
        if (d.getFaculty() == null) throw new ValidationException("Faculty cannot be null");
    }

    public void updatePartial(int id, Optional<String> name, Optional<Faculty> faculty, Optional<Teacher> head, Optional<String> location) {
        Department d = getOrThrow(id);
        if (name.isPresent()) d.setName(name.get());
        if (faculty.isPresent()) d.setFaculty(faculty.get());
        if (head.isPresent()) d.setHead(head.get());
        if (location.isPresent()) d.setLocation(location.get());
        repo.save(d);
    }
}