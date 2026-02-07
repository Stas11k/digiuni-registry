package ua.edu.ukma.repository;

import ua.edu.ukma.domain.Specialty;

import java.util.ArrayList;
import java.util.List;

public class InMemorySpecialtyRepository implements Repository<Specialty, Integer> {

    private final List<Specialty> specialties = new ArrayList<>();

    @Override
    public void save(Specialty specialty) {
        specialties.add(specialty);
    }

    @Override
    public Specialty findById(Integer id) {
        for (Specialty s : specialties) {
            if (s.getId() == id) return s;
        }
        return null;
    }

    @Override
    public List<Specialty> findAll() {
        return new ArrayList<>(specialties);
    }

    @Override
    public boolean deleteById(Integer id) {
        for (Specialty s : specialties) {
            if (s.getId() == id) {
                specialties.remove(s);
                return true;
            }
        }
        return false;
    }
}
