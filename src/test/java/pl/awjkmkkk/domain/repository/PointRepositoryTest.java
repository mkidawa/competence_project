package pl.awjkmkkk.domain.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.awjkmkkk.domain.entity.Point;
import pl.awjkmkkk.domain.exception.ObjectNotFound;
import pl.awjkmkkk.domain.exception.ReaderException;
import pl.awjkmkkk.domain.exception.UpdateNotPossible;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.awjkmkkk.domain.constant.Constants.NUMBER_OF_RECORDS_IN_FILE;
import static pl.awjkmkkk.domain.constant.Constants.PATH_FILE_MOCK_POINTS;

class PointRepositoryTest {

    /*------------------------ FIELDS REGION ------------------------*/
    private JsonIO jsonIO = new JsonIO();
    private PointRepository pointRepository = new PointRepository(PATH_FILE_MOCK_POINTS);
    private UUID chosenUuid;

    /*------------------------ METHODS REGION ------------------------*/
    @BeforeEach
    void setUp() throws ReaderException {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_RECORDS_IN_FILE; i++) {
            points.add(new Point("abc", "cde", Point.PointType.INDOOR, 1.15f));
        }

        jsonIO.writeToFile(Point.class, points, PATH_FILE_MOCK_POINTS);
        chosenUuid = pointRepository.findAll().get(0).getUuid();
    }

    @AfterEach
    void tearDown() throws ReaderException {
        jsonIO.writeToFile(Point.class, new ArrayList<>(), PATH_FILE_MOCK_POINTS);
    }

    @Test
    void findAllTest() throws ReaderException {
        assertEquals(NUMBER_OF_RECORDS_IN_FILE, pointRepository.findAll().size());
    }

    @Test
    void findByUuidTest() throws ObjectNotFound, ReaderException {
        assertNotNull(pointRepository.findByUuid(chosenUuid));
    }

    @Test
    void saveTest() throws ReaderException {
        pointRepository.save(new Point("abc", "cde", Point.PointType.INDOOR, 1.15f));
        assertEquals(NUMBER_OF_RECORDS_IN_FILE + 1, pointRepository.findAll().size());
    }

    @Test
    void updateTest() throws ReaderException, UpdateNotPossible, ObjectNotFound {
        Point pointToUpdate = pointRepository.findAll().get(0);
        pointToUpdate.setType(Point.PointType.OUTDOOR);
        pointRepository.update(pointToUpdate);

        assertEquals(
                Point.PointType.OUTDOOR,
                pointRepository
                        .findAll()
                        .stream()
                        .filter((it) -> it.getUuid().equals(pointToUpdate.getUuid()))
                        .findFirst()
                        .get()
                        .getType()
        );
    }

    @Test
    void updateExceptionTest() {
        assertThrows(UpdateNotPossible.class, () -> {
            pointRepository.update(new Point("abc", "cde", Point.PointType.INDOOR, 1.15f));
        });
    }

    @Test
    void deleteByUuidTest() throws ObjectNotFound, ReaderException {
        pointRepository.deleteById(chosenUuid);
        assertFalse(pointRepository
                .findAll()
                .stream()
                .anyMatch((it) -> it.getUuid().equals(chosenUuid))
        );
    }

    @Test
    void deleteAllTest() throws ReaderException {
        pointRepository.deleteAll();
        assertEquals(0, pointRepository.findAll().size());
    }
}
