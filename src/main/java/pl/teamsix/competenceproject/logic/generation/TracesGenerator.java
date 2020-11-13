package pl.teamsix.competenceproject.logic.generation;

import java.time.Duration;

import org.springframework.stereotype.Service;
import pl.teamsix.competenceproject.domain.service.trace.TraceService;

@Service
public class TracesGenerator {

    private final TraceService traceService;

    public TracesGenerator(final TraceService traceService) {
        this.traceService = traceService;
    }

    public void generate(int numberOfUsers, int numberOfHotspots, Duration duration) {
        //TODO Jan - implement traces generation
    }
}
