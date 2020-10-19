package pl.awjkmkkk.domain.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.awjkmkkk.domain.entity.Person;
import pl.awjkmkkk.domain.entity.Trace;
import pl.awjkmkkk.domain.exception.ObjectNotFound;
import pl.awjkmkkk.domain.exception.ReaderException;
import pl.awjkmkkk.domain.exception.UpdateNotPossible;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.awjkmkkk.domain.constant.Constants.NUMBER_OF_RECORDS_IN_FILE;
import static pl.awjkmkkk.domain.constant.Constants.PATH_FILE_MOCK_TRACES;

class TraceRepositoryTest {

    /*------------------------ FIELDS REGION ------------------------*/
    private JsonIO jsonIO = new JsonIO();
    private TraceRepository traceRepository = new TraceRepository(PATH_FILE_MOCK_TRACES);
    private UUID chosenUuid;
    private String date;

    /*------------------------ METHODS REGION ------------------------*/
    @BeforeEach
    void setUp() throws ReaderException {
        List<Trace> traces = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_RECORDS_IN_FILE; i++) {
            traces.add(new Trace(UUID.randomUUID(), UUID.randomUUID(),
                    LocalDateTime.now(), LocalDateTime.now()));
        }

        jsonIO.writeToFile(Trace.class, traces, PATH_FILE_MOCK_TRACES);
        chosenUuid = traceRepository.findAll().get(0).getUuid();
    }

    @AfterEach
    void tearDown() throws ReaderException {
        jsonIO.writeToFile(Trace.class, new ArrayList<>(), PATH_FILE_MOCK_TRACES);
    }

    @Test
    void findAllTest() throws ReaderException {
        assertEquals(NUMBER_OF_RECORDS_IN_FILE, traceRepository.findAll().size());
    }

    @Test
    void findByUuidTest() throws ObjectNotFound, ReaderException {
        assertNotNull(traceRepository.findByUuid(chosenUuid));
    }

    @Test
    void saveTest() throws ReaderException {
        traceRepository.save(new Trace(UUID.randomUUID(),
                UUID.randomUUID(), LocalDateTime.now(), LocalDateTime.now()));
        assertEquals(NUMBER_OF_RECORDS_IN_FILE + 1, traceRepository.findAll().size());
    }

    @Test
    void saveAllTest() throws ReaderException {
        final int numberOfNewRecords = 3;
        List<Trace> traceAdded = new ArrayList<>();
        for (int i = 0; i < numberOfNewRecords; i++) {
            traceAdded.add(new Trace(UUID.randomUUID(), UUID.randomUUID(),
                    LocalDateTime.now(), LocalDateTime.now()));
        }

        traceRepository.saveAll(traceAdded);
        assertEquals(NUMBER_OF_RECORDS_IN_FILE + numberOfNewRecords,
                traceRepository.findAll().size());
    }

    @Test
    void updateTest() throws ReaderException, UpdateNotPossible {
        Trace traceToUpdate = traceRepository.findAll().get(0);
        LocalDateTime localDateTime = LocalDateTime.now();
        traceToUpdate.setTimeOfExit(localDateTime);
        traceRepository.update(traceToUpdate);

        assertEquals(
                localDateTime,
                traceRepository
                        .findAll()
                        .stream()
                        .filter((it) -> it.getUuid().equals(traceToUpdate.getUuid()))
                        .findFirst()
                        .get()
                        .getTimeOfExit()
        );
    }

    @Test
    void updateExceptionTest() {
        assertThrows(UpdateNotPossible.class, () -> {
            traceRepository.update(new Trace(UUID.randomUUID(), UUID.randomUUID(),
                    LocalDateTime.now(), LocalDateTime.now()));
        });
    }

    @Test
    void deleteByUuidTest() throws ObjectNotFound, ReaderException {
        traceRepository.deleteById(chosenUuid);
        assertFalse(traceRepository
                .findAll()
                .stream()
                .anyMatch((it) -> it.getUuid().equals(chosenUuid))
        );
    }

    @Test
    void deleteAllTest() throws ReaderException {
        traceRepository.deleteAll();
        assertEquals(0, traceRepository.findAll().size());
    }
}
