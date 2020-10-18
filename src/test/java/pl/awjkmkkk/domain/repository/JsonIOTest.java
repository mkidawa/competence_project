package pl.awjkmkkk.domain.repository;

import org.junit.jupiter.api.Test;
import pl.awjkmkkk.domain.entity.Person;
import pl.awjkmkkk.domain.exception.ReaderException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static pl.awjkmkkk.domain.constant.Constants.NUMBER_OF_RECORDS_IN_FILE;
import static org.junit.jupiter.api.Assertions.*;
import static pl.awjkmkkk.domain.constant.Constants.PATH_FILE_MOCK_PERSONS;

class JsonIOTest {

    /*------------------------ FIELDS REGION ------------------------*/
    private static final String TEST_FILENAME = "./sampleFile.json";
    private JsonIO jsonIO = new JsonIO();

    /*------------------------ METHODS REGION ------------------------*/
    @Test
    void readWriteTest() throws IOException {
        Files.deleteIfExists(Paths.get(TEST_FILENAME));
        List<Person> people = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_RECORDS_IN_FILE; i++) {
            people.add(new Person(String.valueOf(i * 10), Person.Profile.STUDENT));
        }

        jsonIO.writeToFile(people, TEST_FILENAME);
        assertEquals(people, jsonIO.<Person>readFromFile(Person.class, TEST_FILENAME));
    }
}
