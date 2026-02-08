package ua.edu.ukma.service;

import ua.edu.ukma.domain.Department;
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

    public Department get(int id) {
        return repo.findById(id);
    }

    public List<Department> getAll() {
        return repo.findAll();
    }

    public boolean delete(int id) {
        return repo.deleteById(id);
    }


    public List<Department> findByFaculty(int facultyId) {
        return repo.findAll().stream()
                .filter(d -> d.getFaculty().getId() == facultyId)
                .toList();
    }

    private void validate(Department d) {
        if (d.getName().isBlank())
            throw new IllegalArgumentException("Department name empty");
    }
}
