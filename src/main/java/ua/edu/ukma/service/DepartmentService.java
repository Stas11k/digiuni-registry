package ua.edu.ukma.service;

import ua.edu.ukma.domain.Department;
import ua.edu.ukma.domain.Faculty;
import ua.edu.ukma.domain.Teacher;
import ua.edu.ukma.repository.InMemoryDepartmentRepository;

import java.util.*;

public class DepartmentService {

    private final InMemoryDepartmentRepository repo;

    public DepartmentService(InMemoryDepartmentRepository repo) {
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
        List<Department> result = new ArrayList<>();

        for (Department d : repo.findAll()) {
            if (d.getFaculty().getId() == facultyId) {
                result.add(d);
            }
        }
        return result;
    }

    private void validate(Department d) {
        if (d.getName().isBlank())
            throw new IllegalArgumentException("Department name empty");
    }

    public boolean update(int id, String name, Faculty faculty, Teacher head, String location) {
        Department d = repo.findById(id);
        if (d == null) return false;
        d.setName(name);
        d.setFaculty(faculty);
        d.setHead(head);
        d.setLocation(location);
        return true;
    }
}
