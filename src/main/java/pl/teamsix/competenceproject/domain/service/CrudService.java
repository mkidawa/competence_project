package pl.teamsix.competenceproject.domain.service;

public interface CrudService<T> extends BaseService<T> {

    T update(T object);

    void deleteById(String id);

    void delete(T object);

    void deleteAll();
}
