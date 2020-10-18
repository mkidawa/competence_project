package pl.awjkmkkk.domain.repository;

import pl.awjkmkkk.domain.entity.Person;
import pl.awjkmkkk.domain.exception.ObjectNotFound;
import pl.awjkmkkk.domain.exception.ReaderException;
import pl.awjkmkkk.domain.exception.UpdateNotPossible;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PersonRepository implements BaseRepository<Person> {

    /*------------------------ FIELDS REGION ------------------------*/
    private final JsonIO jsonIO = new JsonIO();
    private List<Person> people = new ArrayList<>();
    private final String filename;

    /*------------------------ METHODS REGION ------------------------*/
    public PersonRepository(String filename) {
        this.filename = filename;
    }

    @Override
    public List<Person> findAll() throws ReaderException {
        readData();
        return people;
    }

    @Override
    public Person findByUuid(UUID uuid) throws ReaderException, ObjectNotFound {
        readData();

        return people
                .stream()
                .filter((it) -> it.getUuid().equals(uuid))
                .findFirst()
                .orElseThrow(ObjectNotFound::new);
    }

    @Override
    public void save(Person object) throws ReaderException {
        updateData(() -> people.add(object));
    }

    @Override
    public void update(Person object) throws ReaderException, UpdateNotPossible, ObjectNotFound {
        readData();
        boolean isObjectAlreadyExists = people
                .stream()
                .anyMatch((it) -> it.getUuid().equals(object.getUuid()));

        if (isObjectAlreadyExists) {
            deleteById(object.getUuid());
            save(object);
            writeData();
        } else {
            throw new UpdateNotPossible();
        }
    }

    @Override
    public void deleteById(UUID uuid) throws ReaderException, ObjectNotFound {
        readData();
        people.removeIf((it) -> it.getUuid().equals(uuid));
        writeData();
    }

    @Override
    public void deleteAll() throws ReaderException {
        updateData(() -> people.clear());
    }

    private void updateData(Runnable runnable) throws ReaderException {
        readData();
        runnable.run();
        writeData();
    }

    private void readData() throws ReaderException {
        people = jsonIO.readFromFile(Person.class, this.filename);
    }

    private void writeData() throws ReaderException {
        jsonIO.writeToFile(Person.class, people, this.filename);
    }
}
