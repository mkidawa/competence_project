package pl.awjkmkkk.domain.repository;

import pl.awjkmkkk.domain.entity.BaseEntity;

import java.util.List;
import java.util.UUID;

public interface BaseRepository<T extends BaseEntity> {

    List<T> findAll();

    T findByUuid(UUID uuid);

    void save(T object);

    void update(T object);

    void deleteById(UUID uuid);

    void deleteAll();
}
