package ua.edu.ukma.service;

import ua.edu.ukma.domain.Department;
import ua.edu.ukma.domain.Student;
import ua.edu.ukma.domain.Teacher;
import ua.edu.ukma.repository.Repository;

import java.time.LocalDate;
import java.util.*;

public class TeacherService {

    private final Repository<Teacher, Integer> repo;

    public TeacherService(Repository<Teacher, Integer> repo) {
        this.repo = repo;
    }

    public void add(Teacher t) {
        validate(t);
        repo.save(t);
    }

    public Teacher get(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public List<Teacher> getAll() {
        return repo.findAll();
    }

    public boolean delete(Integer id) {
        return repo.deleteById(id);
    }

    public List<Teacher> findByPosition(String position) {
        List<Teacher> result = new ArrayList<>();

        for (Teacher t : repo.findAll()) {
            if (t.getPosition().equalsIgnoreCase(position)) {
                result.add(t);
            }
        }
        return result;
    }

    private void validate(Teacher t) {
        if (t.getFirstName().isBlank() || t.getLastName().isBlank())
            throw new IllegalArgumentException("Name cannot be empty");

        if (t.getPosition().isBlank())
            throw new IllegalArgumentException("Position required");
    }

    public boolean update(int id, String lastName, String firstName, String middleName, String birthDate, String email, String phone, String address, String position, Department department, String degree, String title, LocalDate hireDate, double workload) {
        Teacher t = repo.findById(id).orElse(null);
        if (t == null) return false;
        t.setLastName(lastName);
        t.setFirstName(firstName);
        t.setMiddleName(middleName);
        t.setBirthDate(birthDate);
        t.setEmail(email);
        t.setPhone(phone);
        t.setAddress(address);
        t.setPosition(position);
        t.setDepartment(department);
        t.setDegree(degree);
        t.setTitle(title);
        t.setHireDate(hireDate);
        t.setWorkload(workload);
        repo.save(t);
        return true;
    }
}
