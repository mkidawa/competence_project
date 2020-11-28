package pl.teamsix.competenceproject.domain.service;

import java.util.List;

import pl.teamsix.competenceproject.domain.exception.ObjectNotFound;

public interface BaseService<T> {

    T findById(String id) throws ObjectNotFound;

    List<T> findAll() throws ObjectNotFound;

    T save(T object);

    List<T> saveAll(List<T> objects);
}
