package pl.teamsix.competenceproject.domain.service.trace;

import org.springframework.stereotype.Service;
import pl.teamsix.competenceproject.domain.entity.Trace;
import pl.teamsix.competenceproject.domain.exception.ObjectNotFound;
import pl.teamsix.competenceproject.domain.exception.TraceNotFound;
import pl.teamsix.competenceproject.domain.repository.HotspotRepository;
import pl.teamsix.competenceproject.domain.repository.TraceRepository;
import pl.teamsix.competenceproject.domain.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TraceServiceImpl implements TraceService {

    /*------------------------ FIELDS REGION ------------------------*/
    private final UserRepository userRepository;
    private final HotspotRepository hotspotRepository;
    private final TraceRepository traceRepository;

    /*------------------------ METHODS REGION ------------------------*/
    public TraceServiceImpl(UserRepository userRepository, HotspotRepository hotspotRepository,
                            TraceRepository traceRepository) {
        this.userRepository = userRepository;
        this.hotspotRepository = hotspotRepository;
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

    @Override
    public List<Trace> findLimitedNumberFromBeginning(int numberOfObjects) throws ObjectNotFound {
        return findAll().subList(0, numberOfObjects);
    }

    @Override
    public long count() {
        return traceRepository.count();
    }

    @Override
    public Trace save(Trace object) {
        return traceRepository.save(object);
    }

    @Override
    public List<Trace> saveAll(List<Trace> objects) {
        return traceRepository.saveAll(objects);
    }

    @Override
    public void deleteById(String id) {
        traceRepository.deleteById(id);
    }

    @Override
    public void delete(Trace object) {
        traceRepository.delete(object);
    }

    @Override
    public void deleteAll() {
        traceRepository.deleteAll();
    }
}
