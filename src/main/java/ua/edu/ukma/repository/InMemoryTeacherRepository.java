package ua.edu.ukma.repository;

import ua.edu.ukma.domain.Teacher;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InMemoryTeacherRepository {

    private final List<Teacher> teachers = new ArrayList<>();

    public void save(Teacher teacher) {
        teachers.add(teacher);
    }

    public Teacher findById(Integer id) {
        for (Teacher s : teachers) {
            if (s.getId() == id) return s;
        }
        return null;
    }

    public List<Teacher> findAll() {
        return new ArrayList<>(teachers);
    }

    public boolean deleteById(Integer id) {
        Iterator<Teacher> it = teachers.iterator();
        while (it.hasNext()) {
            if (it.next().getId() == id) {
                it.remove();
                return true;
            }
        }
        return false;
    }
}
