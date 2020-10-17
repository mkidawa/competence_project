package pl.awjkmkkk.domain.repository;

import pl.awjkmkkk.domain.entity.Trace;

import java.util.List;
import java.util.UUID;

public class TraceRepository implements BaseRepository<Trace> {

    /*------------------------ FIELDS REGION ------------------------*/

    /*------------------------ METHODS REGION ------------------------*/

    @Override
    public List<Trace> findAll() {
        return null;
    }

    @Override
    public Trace findByUuid(UUID uuid) {
        return null;
    }

    @Override
    public void save(Trace object) {

    }

    @Override
    public void update(Trace object) {

    }

    @Override
    public void deleteById(UUID uuid) {

    }

    @Override
    public void deleteAll() {

    }
}
