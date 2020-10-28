package pl.teamsix.competenceproject.domain.service.person;

import pl.teamsix.competenceproject.domain.entity.Person;
import pl.teamsix.competenceproject.domain.repository.PersonRepository;

import java.util.List;

public class PersonServiceImpl implements PersonService {

    /*------------------------ FIELDS REGION ------------------------*/
    private final PersonRepository personRepository;

    /*------------------------ METHODS REGION ------------------------*/
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person findById(String id) {
        return null;
    }

    @Override
    public List<Person> findAll() {
        return null;
    }

    @Override
    public Person save(Person object) {
        return null;
    }

    @Override
    public Person saveAll(List<Person> object) {
        return null;
    }

    @Override
    public Person update(Person object) {
        return null;
    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public void delete(Person object) {

    }

    @Override
    public void deleteAll() {

    }
}
