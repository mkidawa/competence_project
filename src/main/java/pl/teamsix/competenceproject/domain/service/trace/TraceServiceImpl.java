package pl.teamsix.competenceproject.domain.service.trace;

import org.springframework.stereotype.Service;
import pl.teamsix.competenceproject.domain.entity.Trace;
import pl.teamsix.competenceproject.domain.exception.TraceNotFound;
import pl.teamsix.competenceproject.domain.repository.PersonRepository;
import pl.teamsix.competenceproject.domain.repository.PointRepository;
import pl.teamsix.competenceproject.domain.repository.TraceRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TraceServiceImpl implements TraceService {

    /*------------------------ FIELDS REGION ------------------------*/
    private final PersonRepository personRepository;
    private final PointRepository pointRepository;
    private final TraceRepository traceRepository;

    /*------------------------ METHODS REGION ------------------------*/
    public TraceServiceImpl(PersonRepository personRepository,
                            PointRepository pointRepository,
                            TraceRepository traceRepository) {
        this.personRepository = personRepository;
        this.pointRepository = pointRepository;
        this.traceRepository = traceRepository;
    }

    @Override
    public Trace findById(String id) throws TraceNotFound {
        Optional<Trace> trace = traceRepository.findById(id);

        if (!trace.isPresent()) {
            throw new TraceNotFound();
        }

        return trace.get();
    }

    @Override
    public List<Trace> findAll() throws TraceNotFound {
        List<Trace> traces = traceRepository.findAll();

        if (traces == null || traces.size() == 0) {
            throw new TraceNotFound();
        }

        return traces;
    }

    /**
     * Important info - person and point are saved because during saving ID for this entities
     * are generated and only then there is a possibility to save Trace object because
     * it contains @DBRef to Person and Point object.
     */
    @Override
    public Trace save(Trace object) {
        personRepository.save(object.getPerson());
        pointRepository.save(object.getPoint());
        return traceRepository.save(object);
    }

    /**
     * Important info - person and point are saved because during saving ID for this entities
     * are generated and only then there is a possibility to save Trace object because
     * it contains @DBRef to Person and Point object.
     */
    @Override
    public List<Trace> saveAll(List<Trace> objects) {
        personRepository.saveAll(
                objects.stream()
                        .map((it) -> it.getPerson())
                        .collect(Collectors.toList())
        );

        pointRepository.saveAll(
                objects.stream()
                        .map((it) -> it.getPoint())
                        .collect(Collectors.toList())
        );

        return traceRepository.saveAll(objects);
    }
}
