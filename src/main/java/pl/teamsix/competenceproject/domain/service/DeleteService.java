package pl.teamsix.competenceproject.domain.service;

public interface DeleteService<T> {

    void deleteById(String id);

    void delete(T object);

    void deleteAll();
}
