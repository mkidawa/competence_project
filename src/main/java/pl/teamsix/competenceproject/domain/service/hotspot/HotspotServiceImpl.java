package pl.teamsix.competenceproject.domain.service.hotspot;

import org.springframework.stereotype.Service;
import pl.teamsix.competenceproject.domain.entity.Hotspot;
import pl.teamsix.competenceproject.domain.exception.PointNotFound;
import pl.teamsix.competenceproject.domain.repository.HotspotRepository;

import java.util.List;
import java.util.Optional;

@Service
public class HotspotServiceImpl implements HotspotService {

    /*------------------------ FIELDS REGION ------------------------*/
    private final HotspotRepository hotspotRepository;

    /*------------------------ METHODS REGION ------------------------*/
    public HotspotServiceImpl(HotspotRepository hotspotRepository) {
        this.hotspotRepository = hotspotRepository;
    }

    @Override
    public Hotspot findById(String id) throws PointNotFound {
        Optional<Hotspot> point = hotspotRepository.findById(id);

        if (!point.isPresent()) {
            throw new PointNotFound();
        }

        return point.get();
    }

    @Override
    public List<Hotspot> findAll() throws PointNotFound {
        List<Hotspot> hotspots = hotspotRepository.findAll();

        if (hotspots == null || hotspots.size() == 0) {
            throw new PointNotFound();
        }

        return hotspots;
    }

    @Override
    public Hotspot save(Hotspot object) {
        return hotspotRepository.save(object);
    }

    @Override
    public List<Hotspot> saveAll(List<Hotspot> objects) {
        return hotspotRepository.saveAll(objects);
    }

    /**
     * Save method also updates when passed object exists in database.
     */
    @Override
    public Hotspot update(Hotspot object) {
        return hotspotRepository.save(object);
    }

    /**
     * SaveAll method also updates when passed object exists in database.
     */
    @Override
    public List<Hotspot> updateAll(List<Hotspot> objects) {
        return hotspotRepository.saveAll(objects);
    }

    @Override
    public void deleteById(String id) {
        hotspotRepository.deleteById(id);
    }

    @Override
    public void delete(Hotspot object) {
        hotspotRepository.delete(object);
    }

    @Override
    public void deleteAll() {
        hotspotRepository.deleteAll();
    }
}
