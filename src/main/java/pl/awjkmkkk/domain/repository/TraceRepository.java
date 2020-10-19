package pl.awjkmkkk.domain.repository;

import pl.awjkmkkk.domain.entity.Trace;
import pl.awjkmkkk.domain.exception.ObjectNotFound;
import pl.awjkmkkk.domain.exception.ReaderException;
import pl.awjkmkkk.domain.exception.UpdateNotPossible;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TraceRepository implements BaseRepository<Trace> {

    /*------------------------ FIELDS REGION ------------------------*/
    private final JsonIO jsonIO = new JsonIO();
    private List<Trace> traces;
    private final String filename;

    /*------------------------ METHODS REGION ------------------------*/
    public TraceRepository(String filename) {
        this.filename = filename;
    }

    @Override
    public List<Trace> findAll() throws ReaderException {
        readData();
        return traces;
    }

    @Override
    public Trace findByUuid(UUID uuid) throws ReaderException, ObjectNotFound {
        readData();

        return traces
                .stream()
                .filter((it) -> it.getUuid().equals(uuid))
                .findFirst()
                .orElseThrow(ObjectNotFound::new);
    }

    @Override
    public void save(Trace object) throws ReaderException {
        updateData(() -> traces.add(object));
    }

    @Override
    public void saveAll(List<Trace> objects) throws ReaderException {
        updateData(() -> traces.addAll(objects));
    }

    @Override
    public void update(Trace object) throws ReaderException, UpdateNotPossible {
        readData();
        boolean isObjectAlreadyExists = traces
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
        traces.removeIf((it) -> it.getUuid().equals(uuid));
        writeData();
    }

    @Override
    public void deleteAll() throws ReaderException {
        updateData(() -> traces.clear());
    }

    private void updateData(Runnable runnable) throws ReaderException {
        readData();
        runnable.run();
        writeData();
    }

    private void readData() throws ReaderException {
        List<Trace> tracesTemp = jsonIO.readFromFile(Trace.class, this.filename);

        if (tracesTemp != null) {
            this.traces = tracesTemp;
        } else {
            this.traces = new ArrayList<>();
        }
    }

    private void writeData() throws ReaderException {
        jsonIO.writeToFile(Trace.class, traces, this.filename);
    }
}
