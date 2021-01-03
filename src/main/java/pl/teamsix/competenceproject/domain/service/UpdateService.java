package pl.teamsix.competenceproject.domain.service;

import java.util.List;

public interface UpdateService<T> {

    T update(T object);

    List<T> updateAll(List<T> objects);
}
