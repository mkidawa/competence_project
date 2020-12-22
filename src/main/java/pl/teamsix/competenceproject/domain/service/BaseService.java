package pl.teamsix.competenceproject.domain.service;

import pl.teamsix.competenceproject.domain.exception.ObjectNotFound;

import java.util.List;

public interface BaseService<T> {

    T findById(String id) throws ObjectNotFound;

    List<T> findAll() throws ObjectNotFound;

    List<T> findLimitedNumberFromBeginning(int numberOfObjects) throws ObjectNotFound;

    T save(T object);

    List<T> saveAll(List<T> objects);
}
