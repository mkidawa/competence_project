package pl.teamsix.competenceproject.domain.service.person;

import org.springframework.stereotype.Service;
import pl.teamsix.competenceproject.domain.entity.User;
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
    public User findById(String id) throws PersonNotFound {
        Optional<User> person = personRepository.findById(id);

        if (!person.isPresent()) {
            throw new PersonNotFound();
        }

        return person.get();
    }

    @Override
    public List<User> findAll() throws PersonNotFound {
        List<User> people = personRepository.findAll();

        if (people == null || people.size() == 0) {
            throw new PersonNotFound();
        }

        return people;
    }

    @Override
    public User save(User object) {
        return personRepository.save(object);
    }

    @Override
    public List<User> saveAll(List<User> objects) {
        return personRepository.saveAll(objects);
    }

    /**
     * Save method also updates when passed object exists in database.
     */
    @Override
    public User update(User object) {
        return personRepository.save(object);
    }

    /**
     * SaveAll method also updates when passed object exists in database.
     */
    @Override
    public List<User> updateAll(List<User> objects) {
        return personRepository.saveAll(objects);
    }

    @Override
    public void deleteById(String id) {
        personRepository.deleteById(id);
    }

    @Override
    public void delete(User object) {
        personRepository.delete(object);
    }

    @Override
    public void deleteAll() {
        personRepository.deleteAll();
    }
}
