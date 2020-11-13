package pl.teamsix.competenceproject.logic.generation;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import pl.teamsix.competenceproject.domain.entity.Hotspot;
import pl.teamsix.competenceproject.domain.service.hotspot.HotspotService;

@Service
public class HotspotsGenerator {

    private static final int BATCH_SIZE = 100;

    private final HotspotService hotspotService;

    public HotspotsGenerator(final HotspotService hotspotService) {
        this.hotspotService = hotspotService;
    }

    public void generate(int quantity) {
        for (int i = 0; i < quantity; i += BATCH_SIZE) {
            hotspotService.saveAll(IntStream.range(0, Math.min(quantity - i, BATCH_SIZE))
                    .mapToObj(x -> generateSingleHotspot())
                    .collect(Collectors.toList()));
        }
    }

    private Hotspot generateSingleHotspot() {
        final Hotspot hotspot = new Hotspot();
        generateHotspotPosition(hotspot);
        //TODO Ola - generate all field values except coordinates
        return hotspot;
    }

    private void generateHotspotPosition(Hotspot hotspot) {
        //TODO Dominik - fill x, y values
    }
}
