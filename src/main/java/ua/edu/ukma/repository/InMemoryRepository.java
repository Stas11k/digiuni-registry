package ua.edu.ukma.repository;

import java.util.*;

public class InMemoryRepository<T extends Identifiable<ID>, ID> implements Repository<T, ID> {

    protected final Map<ID, T> storage = new LinkedHashMap<>();

    @Override
    public synchronized T save(T entity) {
        if (entity == null) throw new IllegalArgumentException("Entity cannot be null");
        storage.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public synchronized Optional<T> findById(ID id) {
        if (id == null) return Optional.empty();
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public synchronized List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public synchronized boolean deleteById(ID id) {
        if (id == null) return false;
        return storage.remove(id) != null;
    }

    @Override
    public synchronized void clear() {
        storage.clear();
    }
}