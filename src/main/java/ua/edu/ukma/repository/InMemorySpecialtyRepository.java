package ua.edu.ukma.repository;

import ua.edu.ukma.domain.Specialty;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InMemorySpecialtyRepository {

    private final List<Specialty> specialties = new ArrayList<>();

    public void save(Specialty specialty) {
        specialties.add(specialty);
    }

    public Specialty findById(Integer id) {
        for (Specialty s : specialties) {
            if (s.getId() == id) return s;
        }
        return null;
    }

    public List<Specialty> findAll() {
        return new ArrayList<>(specialties);
    }

    public boolean deleteById(Integer id) {
        Iterator<Specialty> it = specialties.iterator();
        while (it.hasNext()) {
            if (it.next().getId() == id) {
                it.remove();
                return true;
            }
        }
        return false;
    }
}
