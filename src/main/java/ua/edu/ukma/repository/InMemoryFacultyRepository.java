package ua.edu.ukma.repository;

import ua.edu.ukma.domain.Faculty;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InMemoryFacultyRepository {

    private final List<Faculty> faculties = new ArrayList<>();

    public void save(Faculty faculty) {
        faculties.add(faculty);
    }

    public Faculty findById(Integer id) {
        for (Faculty f : faculties) {
            if (f.getId() == id) return f;
        }
        return null;
    }

    public List<Faculty> findAll() {
        return new ArrayList<>(faculties);
    }

    public boolean deleteById(Integer id) {
        Iterator<Faculty> it = faculties.iterator();
        while (it.hasNext()) {
            if (it.next().getId() == id) {
                it.remove();
                return true;
            }
        }
        return false;
    }
}
