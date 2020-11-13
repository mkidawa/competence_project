package pl.teamsix.competenceproject.domain.service.point;

import org.springframework.stereotype.Service;
import pl.teamsix.competenceproject.domain.entity.Hotspot;
import pl.teamsix.competenceproject.domain.exception.PointNotFound;
import pl.teamsix.competenceproject.domain.repository.PointRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PointServiceImpl implements PointService {

    /*------------------------ FIELDS REGION ------------------------*/
    private final PointRepository pointRepository;

    /*------------------------ METHODS REGION ------------------------*/
    public PointServiceImpl(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    @Override
    public Hotspot findById(String id) throws PointNotFound {
        Optional<Hotspot> point = pointRepository.findById(id);

        if (!point.isPresent()) {
            throw new PointNotFound();
        }

        return point.get();
    }

    @Override
    public List<Hotspot> findAll() throws PointNotFound {
        List<Hotspot> hotspots = pointRepository.findAll();

        if (hotspots == null || hotspots.size() == 0) {
            throw new PointNotFound();
        }

        return hotspots;
    }

    @Override
    public Hotspot save(Hotspot object) {
        return pointRepository.save(object);
    }

    @Override
    public List<Hotspot> saveAll(List<Hotspot> objects) {
        return pointRepository.saveAll(objects);
    }

    /**
     * Save method also updates when passed object exists in database.
     */
    @Override
    public Hotspot update(Hotspot object) {
        return pointRepository.save(object);
    }

    /**
     * SaveAll method also updates when passed object exists in database.
     */
    @Override
    public List<Hotspot> updateAll(List<Hotspot> objects) {
        return pointRepository.saveAll(objects);
    }

    @Override
    public void deleteById(String id) {
        pointRepository.deleteById(id);
    }

    @Override
    public void delete(Hotspot object) {
        pointRepository.delete(object);
    }

    @Override
    public void deleteAll() {
        pointRepository.deleteAll();
    }
}
