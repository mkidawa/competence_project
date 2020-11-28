package pl.teamsix.competenceproject.logic.generation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.springframework.stereotype.Service;
import pl.teamsix.competenceproject.domain.entity.Hotspot;
import pl.teamsix.competenceproject.domain.entity.Trace;
import pl.teamsix.competenceproject.domain.entity.User;
import pl.teamsix.competenceproject.domain.service.trace.TraceService;

@Service
public class TracesGenerator {

    private static final int BATCH_SIZE = 1000;
    private static final int MILLISECONDS_IN_HOUR = 3600000;

    private final List<Trace> traces = new ArrayList<>();

    private final TraceService traceService;

    public TracesGenerator(final TraceService traceService) {
        this.traceService = traceService;
    }

    public void generate(List<User> users, List<Hotspot> hotspots, LocalDateTime startTime, double durationInHours,
            double avgMovementsPerHour) {
        long duration = (long) (durationInHours * MILLISECONDS_IN_HOUR);
        double lambda = avgMovementsPerHour / MILLISECONDS_IN_HOUR;
        final ExponentialDistribution timeDistribution = new ExponentialDistribution(1.0 / lambda);
        final ExponentialDistribution hotspotsDistribution = new ExponentialDistribution(1.0 / 6.0);
        for (User user : users) {
            double time = timeDistribution.sample();
            while (time < duration) {
                double exitTime = time + timeDistribution.sample();
                int nextHotspot;
                do {
                    nextHotspot = (int) (hotspotsDistribution.sample() * hotspots.size());
                } while (nextHotspot >= hotspots.size());
                generateSingleTrace(user, hotspots.get(nextHotspot), toLocalDateTime(time, startTime),
                        toLocalDateTime(exitTime, startTime));
                time = exitTime;
            }
        }
    }

    private void generateSingleTrace(User user, Hotspot hotspot, LocalDateTime entryTime, LocalDateTime exitTime) {
        traces.add(new Trace(user, hotspot, entryTime, exitTime));
        if (traces.size() == BATCH_SIZE) {
            traceService.saveAll(traces);
            traces.clear();
        }
    }

    private LocalDateTime toLocalDateTime(double time, LocalDateTime startTime) {
        return startTime.plusSeconds((long) time / 1000);
    }
}
