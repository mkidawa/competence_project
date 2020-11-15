package pl.teamsix.competenceproject.logic.generation;

import org.apache.commons.rng.RestorableUniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;
import org.apache.commons.statistics.distribution.ContinuousDistribution;
import org.apache.commons.statistics.distribution.GammaDistribution;
import org.springframework.stereotype.Service;
import pl.teamsix.competenceproject.domain.entity.Hotspot;
import pl.teamsix.competenceproject.domain.service.hotspot.HotspotService;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class HotspotsGenerator {

    private static final int BATCH_SIZE = 100;
    private double maxDistance;
    private ContinuousDistribution.Sampler sampler;
    private final RestorableUniformRandomProvider rand;
    private  HotspotService hotspotService;

    public HotspotsGenerator(final HotspotService hotspotService) {
        this.rand = RandomSource.create(RandomSource.MT);
        this.sampler = new GammaDistribution(1,2).createSampler(rand);
        this.maxDistance = 5000;
        this.hotspotService = hotspotService;
    }

    public void setMaxDistance(double maxDistance) {
        this.maxDistance = maxDistance;
    }

    public void setGammaParams(double shape, double scale) {
        this.sampler = new GammaDistribution(shape,scale).createSampler(rand);
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
        double  angle = rand.nextDouble()*2*Math.PI; //uniform distribution
        double distance; //gamma distribution
        do{
            distance = sampler.sample()*1000;}
        while(distance>this.maxDistance);
        hotspot.setX(distance*Math.cos(angle));
        hotspot.setY(distance*Math.sin(angle));
    }
}
