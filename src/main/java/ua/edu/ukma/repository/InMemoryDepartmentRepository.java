package ua.edu.ukma.repository;

import ua.edu.ukma.domain.Department;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InMemoryDepartmentRepository {

    private final List<Department> departments = new ArrayList<>();

    public void save(Department department) {
        departments.add(department);
    }

    public Department findById(Integer id) {
        for (Department d : departments) {
            if (d.getId() == id) return d;
        }
        return null;
    }

    public List<Department> findAll() {
        return new ArrayList<>(departments);
    }

    public boolean deleteById(Integer id) {
        Iterator<Department> it = departments.iterator();
        while (it.hasNext()) {
            if (it.next().getId() == id) {
                it.remove();
                return true;
            }
        }
        return false;
    }
}
