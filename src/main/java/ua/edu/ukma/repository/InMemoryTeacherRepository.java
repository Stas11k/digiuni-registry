package ua.edu.ukma.repository;

import ua.edu.ukma.domain.Teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryTeacherRepository implements Repository<Teacher, UUID> {

    private final List<Teacher> teachers = new ArrayList<>();

    @Override
    public void save(Teacher teacher) {
        teachers.add(teacher);
    }

    @Override
    public Teacher findById(UUID id) {
        for (Teacher t : teachers) {
            if (t.getId().equals(id)) return t;
        }
        return null;
    }

    @Override
    public List<Teacher> findAll() {
        return new ArrayList<>(teachers);
    }

    @Override
    public boolean deleteById(UUID id) {
        for (Teacher t : teachers) {
            if (t.getId().equals(id)) {
                teachers.remove(t);
                return true;
            }
        }
        return false;
    }
}
