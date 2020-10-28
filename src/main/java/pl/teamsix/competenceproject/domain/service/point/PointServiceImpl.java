package pl.teamsix.competenceproject.domain.service.point;

import pl.teamsix.competenceproject.domain.entity.Point;
import pl.teamsix.competenceproject.domain.exception.PointNotFound;
import pl.teamsix.competenceproject.domain.repository.PointRepository;

import java.util.List;
import java.util.Optional;

public class PointServiceImpl implements PointService {

    /*------------------------ FIELDS REGION ------------------------*/
    private final PointRepository pointRepository;

    /*------------------------ METHODS REGION ------------------------*/
    public PointServiceImpl(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    @Override
    public Point findById(String id) throws PointNotFound {
        Optional<Point> point = pointRepository.findById(id);

        if (!point.isPresent()) {
            throw new PointNotFound();
        }

        return point.get();
    }

    @Override
    public List<Point> findAll() throws PointNotFound {
        List<Point> points = pointRepository.findAll();

        if (points == null || points.size() == 0) {
            throw new PointNotFound();
        }

        return points;
    }

    @Override
    public Point save(Point object) {
        return pointRepository.save(object);
    }

    @Override
    public List<Point> saveAll(List<Point> objects) {
        return pointRepository.saveAll(objects);
    }

    @Override
    public Point update(Point object) {
        return pointRepository.save(object);
    }

    @Override
    public List<Point> updateAll(List<Point> objects) {
        return pointRepository.saveAll(objects);
    }

    @Override
    public void deleteById(String id) {
        pointRepository.deleteById(id);
    }

    @Override
    public void delete(Point object) {
        pointRepository.delete(object);
    }

    @Override
    public void deleteAll() {
        pointRepository.deleteAll();
    }
}
