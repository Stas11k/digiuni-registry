package ua.edu.ukma.repository;

import ua.edu.ukma.domain.Department;

import java.util.ArrayList;
import java.util.List;

public class InMemoryDepartmentRepository implements Repository<Department, String> {

    private final List<Department> departments = new ArrayList<>();

    @Override
    public void save(Department department) {
        departments.add(department);
    }

    @Override
    public Department findById(String code) {
        for (Department d : departments) {
            if (d.getCode().equals(code)) return d;
        }
        return null;
    }

    @Override
    public List<Department> findAll() {
        return new ArrayList<>(departments);
    }

    @Override
    public boolean deleteById(String code) {
        for (Department d : departments) {
            if (d.getCode().equals(code)) {
                departments.remove(d);
                return true;
            }
        }
        return false;
    }
}
