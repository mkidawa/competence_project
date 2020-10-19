package pl.awjkmkkk.domain.repository;

import pl.awjkmkkk.domain.entity.BaseEntity;
import pl.awjkmkkk.domain.exception.ObjectNotFound;
import pl.awjkmkkk.domain.exception.ReaderException;
import pl.awjkmkkk.domain.exception.UpdateNotPossible;

import java.util.List;
import java.util.UUID;

public interface BaseRepository<T extends BaseEntity> {

    List<T> findAll() throws ReaderException;

    T findByUuid(UUID uuid) throws ReaderException, ObjectNotFound;

    void save(T object) throws ReaderException;

    void saveAll(List<T> objects);

    void update(T object) throws ReaderException, UpdateNotPossible, ObjectNotFound;

    void deleteById(UUID uuid) throws ReaderException, ObjectNotFound;

    void deleteAll() throws ReaderException;
}
