package ua.edu.ukma.repository;

import ua.edu.ukma.domain.Department;

import java.util.ArrayList;
import java.util.List;

public class InMemoryDepartmentRepository implements Repository<Department, Integer> {

    private final List<Department> departments = new ArrayList<>();

    @Override
    public void save(Department department) {
        departments.add(department);
    }

    @Override
    public Department findById(Integer id) {
        for (Department d : departments) {
            if (d.getId() == id) return d;
        }
        return null;
    }

    @Override
    public List<Department> findAll() {
        return new ArrayList<>(departments);
    }

    @Override
    public boolean deleteById(Integer id) {
        for (Department d : departments) {
            if (d.getId() == id) {
                departments.remove(d);
                return true;
            }
        }
        return false;
    }
}
