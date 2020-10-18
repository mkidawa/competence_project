package pl.awjkmkkk.domain.repository;

import pl.awjkmkkk.domain.entity.Point;
import pl.awjkmkkk.domain.exception.ObjectNotFound;
import pl.awjkmkkk.domain.exception.ReaderException;
import pl.awjkmkkk.domain.exception.UpdateNotPossible;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PointRepository implements BaseRepository<Point> {

    /*------------------------ FIELDS REGION ------------------------*/
    private final JsonIO jsonIO = new JsonIO();
    private List<Point> points = new ArrayList<>();
    private final String filename;

    /*------------------------ METHODS REGION ------------------------*/
    public PointRepository(String filename) {
        this.filename = filename;
    }

    @Override
    public List<Point> findAll() throws ReaderException {
        readData();
        return points;
    }

    @Override
    public Point findByUuid(UUID uuid) throws ReaderException, ObjectNotFound {
        readData();

        return points
                .stream()
                .filter((it) -> it.getUuid().equals(uuid))
                .findFirst()
                .orElseThrow(ObjectNotFound::new);
    }

    @Override
    public void save(Point object) throws ReaderException {
        updateData(() -> points.add(object));
    }

    @Override
    public void update(Point object) throws ReaderException, UpdateNotPossible {
        readData();
        boolean isObjectAlreadyExists = points
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
    public void deleteById(UUID uuid) throws ReaderException {
        readData();
        points.removeIf((it) -> it.getUuid().equals(uuid));
        writeData();
    }

    @Override
    public void deleteAll() throws ReaderException {
        updateData(() -> points.clear());
    }

    private void updateData(Runnable runnable) throws ReaderException {
        readData();
        runnable.run();
        writeData();
    }

    private void readData() throws ReaderException {
        points = jsonIO.readFromFile(Point.class, this.filename);
    }

    private void writeData() throws ReaderException {
        jsonIO.writeToFile(Point.class, points, this.filename);
    }
}
