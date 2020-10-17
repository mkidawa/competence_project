package pl.awjkmkkk.domain.repository;

import pl.awjkmkkk.domain.entity.Person;

import java.util.List;
import java.util.UUID;

public class PersonRepository implements BaseRepository<Person> {

    /*------------------------ FIELDS REGION ------------------------*/

    /*------------------------ METHODS REGION ------------------------*/

    @Override
    public List<Person> findAll() {
        return null;
    }

    @Override
    public Person findByUuid(UUID uuid) {
        return null;
    }

    @Override
    public void save(Person object) {

    }

    @Override
    public void update(Person object) {

    }

    @Override
    public void deleteById(UUID uuid) {

    }

    @Override
    public void deleteAll() {

    }
}
