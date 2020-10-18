package pl.awjkmkkk.domain.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.awjkmkkk.domain.entity.Person;
import pl.awjkmkkk.domain.exception.ObjectNotFound;
import pl.awjkmkkk.domain.exception.ReaderException;
import pl.awjkmkkk.domain.exception.UpdateNotPossible;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static pl.awjkmkkk.domain.constant.Constants.NUMBER_OF_RECORDS_IN_FILE;
import static pl.awjkmkkk.domain.constant.Constants.PATH_FILE_MOCK_PERSONS;

class PersonRepositoryTest {

    /*------------------------ FIELDS REGION ------------------------*/
    private JsonIO jsonIO = new JsonIO();
    private PersonRepository personRepository = new PersonRepository(PATH_FILE_MOCK_PERSONS);
    List<Person> people = new ArrayList<>();
    private UUID chosenUuid;

    /*------------------------ METHODS REGION ------------------------*/
    @BeforeEach
    void setUp() throws ReaderException {
        for (int i = 0; i < NUMBER_OF_RECORDS_IN_FILE; i++) {
            people.add(new Person(String.valueOf(i * 10), Person.Profile.STUDENT));
        }

        jsonIO.writeToFile(Person.class, people, PATH_FILE_MOCK_PERSONS);
        chosenUuid = personRepository.findAll().get(0).getUuid();
    }

    @AfterEach
    void tearDown() throws ReaderException {
        jsonIO.writeToFile(Person.class, new ArrayList<>(), PATH_FILE_MOCK_PERSONS);
    }

    @Test
    void findAllTest() throws ReaderException {
        assertEquals(NUMBER_OF_RECORDS_IN_FILE, personRepository.findAll().size());
    }

    @Test
    void findByUuidTest() throws ObjectNotFound, ReaderException {
        assertNotNull(personRepository.findByUuid(chosenUuid));
    }

    @Test
    void saveTest() throws ReaderException {
        personRepository.save(new Person(String.valueOf(10), Person.Profile.STUDENT));
        assertEquals(NUMBER_OF_RECORDS_IN_FILE + 1, personRepository.findAll().size());
    }

    @Test
    void updateTest() throws ReaderException, UpdateNotPossible, ObjectNotFound {
        Person personToUpdate = personRepository.findAll().get(0);
        personToUpdate.setPersonProfile(Person.Profile.TEACHER);
        personRepository.update(personToUpdate);

        assertEquals(
                Person.Profile.TEACHER,
                personRepository
                        .findAll()
                        .stream()
                        .filter((it) -> it.getUuid().equals(personToUpdate.getUuid()))
                        .findFirst()
                        .get()
                        .getPersonProfile()
        );
    }

    @Test
    void deleteByUuidTest() throws ObjectNotFound, ReaderException {
        personRepository.deleteById(chosenUuid);
        assertFalse(personRepository
                .findAll()
                .stream()
                .anyMatch((it) -> it.getUuid().equals(chosenUuid))
        );
    }

    @Test
    void deleteAllTest() throws ReaderException {
        personRepository.deleteAll();
        assertEquals(0, personRepository.findAll().size());
    }
}
