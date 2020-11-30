package pl.teamsix.competenceproject.logic.generation;

import org.apache.commons.rng.RestorableUniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;
import org.apache.commons.statistics.distribution.ContinuousDistribution;
import org.apache.commons.statistics.distribution.GammaDistribution;
import org.springframework.stereotype.Service;
import pl.teamsix.competenceproject.domain.entity.Hotspot;
import pl.teamsix.competenceproject.domain.service.hotspot.HotspotService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Double.parseDouble;

@Service
public class HotspotsGenerator {

    private static final int BATCH_SIZE = 100;
    private double maxDistance;
    private ContinuousDistribution.Sampler sampler;
    private final RestorableUniformRandomProvider rand;
    private HotspotService hotspotService;
    List<String> hotspotList; //in format (name, outdoor probability)
    List<String> facilitiesList;

    public HotspotsGenerator(final HotspotService hotspotService) {
        this.rand = RandomSource.create(RandomSource.MT);
        this.sampler = new GammaDistribution(1, 2).createSampler(rand);
        this.maxDistance = 5000;
        this.hotspotService = hotspotService;
        loadAllLists();
    }

    public void setMaxDistance(double maxDistance) {
        this.maxDistance = maxDistance;
    }

    public void setGammaParams(double shape, double scale) {
        this.sampler = new GammaDistribution(shape, scale).createSampler(rand);
    }

    public void generate(int quantity) {
        for (int i = 0; i < quantity; i += BATCH_SIZE) {
            hotspotService.saveAll(
                    IntStream.range(0, Math.min(quantity - i, BATCH_SIZE)).mapToObj(x -> generateSingleHotspot())
                            .collect(Collectors.toList()));
        }
    }

    private Hotspot generateSingleHotspot() {
        final Hotspot hotspot = new Hotspot();
        generateHotspotPosition(hotspot);
        //generating hotspot
        Random rand = new Random();
        String hotspotName;
        String type;
        if (rand.nextDouble() > 0.5) {
            String[] segments = hotspotList.get(rand.nextInt(2)).split(",");
            hotspotName = segments[0] + " of " + facilitiesList.get(rand.nextInt(facilitiesList.size()));
            if (rand.nextDouble() < parseDouble(segments[1]) || segments[1] == "1") {
                type = "outdoor";
            } else {
                type = "indoor";
            }
        } else {
            String[] segments = hotspotList.get(rand.nextInt(hotspotList.size() - 2) + 2).split(",");
            hotspotName = segments[0];
            if (rand.nextDouble() < parseDouble(segments[1]) || segments[1] == "1") {
                type = "outdoor";
            } else {
                type = "indoor";
            }
        }
        hotspot.setName(hotspotName);
        hotspot.setType(type);

        return hotspot;
    }

    private void generateHotspotPosition(Hotspot hotspot) {
        double angle = rand.nextDouble() * 2 * Math.PI; //uniform distribution
        double distance; //gamma distribution
        do {
            distance = sampler.sample() * 1000;
        } while (distance > this.maxDistance);
        hotspot.setX(distance * Math.cos(angle));
        hotspot.setY(distance * Math.sin(angle));
    }

    public void loadAllLists() {
        hotspotList = readFromSimpleFile("src/main/resources/hotspotNamesDB.txt");
        facilitiesList = readFromSimpleFile("src/main/resources/facultiesDB.txt");
    }

    public List readFromSimpleFile(String filePath) {
        ArrayList<String> arr = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                arr.add(sCurrentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }
}
