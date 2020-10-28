package pl.teamsix.competenceproject.domain.service.trace;

import pl.teamsix.competenceproject.domain.entity.Trace;
import pl.teamsix.competenceproject.domain.repository.PersonRepository;
import pl.teamsix.competenceproject.domain.repository.PointRepository;
import pl.teamsix.competenceproject.domain.repository.TraceRepository;

import java.util.List;

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
    public Trace findById(String id) {
        return null;
    }

    @Override
    public List<Trace> findAll() {
        return null;
    }

    @Override
    public Trace save(Trace object) {
        return null;
    }

    @Override
    public Trace saveAll(List<Trace> object) {
        return null;
    }
}
