package pl.teamsix.competenceproject.domain.service.trace;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import pl.teamsix.competenceproject.domain.entity.Trace;
import pl.teamsix.competenceproject.domain.exception.TraceNotFound;
import pl.teamsix.competenceproject.domain.repository.UserRepository;
import pl.teamsix.competenceproject.domain.repository.HotspotRepository;
import pl.teamsix.competenceproject.domain.repository.TraceRepository;

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

    /**
     * Important info - person and point are saved because during saving ID for this entities
     * are generated and only then there is a possibility to save Trace object because
     * it contains @DBRef to Person and Point object.
     */
    @Override
    public Trace save(Trace object) {
        userRepository.save(object.getUser());
        hotspotRepository.save(object.getHotspot());
        return traceRepository.save(object);
    }

    /**
     * Important info - person and point are saved because during saving ID for this entities
     * are generated and only then there is a possibility to save Trace object because
     * it contains @DBRef to Person and Point object.
     */
    @Override
    public List<Trace> saveAll(List<Trace> objects) {
        userRepository.saveAll(objects.stream().map((it) -> it.getUser()).collect(Collectors.toList()));

        hotspotRepository.saveAll(objects.stream().map((it) -> it.getHotspot()).collect(Collectors.toList()));

        return traceRepository.saveAll(objects);
    }
}
