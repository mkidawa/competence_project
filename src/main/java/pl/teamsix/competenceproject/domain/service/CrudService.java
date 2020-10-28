package pl.teamsix.competenceproject.domain.service;

import java.util.List;

public interface CrudService<T> extends BaseService<T> {

    T update(T object);

    List<T> updateAll(List<T> objects);

    void deleteById(String id);

    void delete(T object);

    void deleteAll();
}
