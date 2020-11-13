package pl.teamsix.competenceproject.domain.service.person;

import org.springframework.stereotype.Service;
import pl.teamsix.competenceproject.domain.entity.Person;
import pl.teamsix.competenceproject.domain.exception.PersonNotFound;
import pl.teamsix.competenceproject.domain.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    /*------------------------ FIELDS REGION ------------------------*/
    private final PersonRepository personRepository;

    /*------------------------ METHODS REGION ------------------------*/
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person findById(String id) throws PersonNotFound {
        Optional<Person> person = personRepository.findById(id);

        if (!person.isPresent()) {
            throw new PersonNotFound();
        }

        return person.get();
    }

    @Override
    public List<Person> findAll() throws PersonNotFound {
        List<Person> people = personRepository.findAll();

        if (people == null || people.size() == 0) {
            throw new PersonNotFound();
        }

        return people;
    }

    @Override
    public Person save(Person object) {
        return personRepository.save(object);
    }

    @Override
    public List<Person> saveAll(List<Person> objects) {
        return personRepository.saveAll(objects);
    }

    /**
     * Save method also updates when passed object exists in database.
     */
    @Override
    public Person update(Person object) {
        return personRepository.save(object);
    }

    /**
     * SaveAll method also updates when passed object exists in database.
     */
    @Override
    public List<Person> updateAll(List<Person> objects) {
        return personRepository.saveAll(objects);
    }

    @Override
    public void deleteById(String id) {
        personRepository.deleteById(id);
    }

    @Override
    public void delete(Person object) {
        personRepository.delete(object);
    }

    @Override
    public void deleteAll() {
        personRepository.deleteAll();
    }
}
