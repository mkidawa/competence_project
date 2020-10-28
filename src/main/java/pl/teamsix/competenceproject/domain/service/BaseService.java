package pl.teamsix.competenceproject.domain.service;

import java.util.List;

public interface BaseService<T> {

    T findById(String id);

    List<T> findAll();

    T save(T object);

    T saveAll(List<T> object);
}
