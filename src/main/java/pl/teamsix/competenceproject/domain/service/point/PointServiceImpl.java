package pl.teamsix.competenceproject.domain.service.point;

import pl.teamsix.competenceproject.domain.entity.Point;
import pl.teamsix.competenceproject.domain.repository.PointRepository;

import java.util.List;

public class PointServiceImpl implements PointService{

    /*------------------------ FIELDS REGION ------------------------*/
    private final PointRepository pointRepository;

    /*------------------------ METHODS REGION ------------------------*/
    public PointServiceImpl(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    @Override
    public Point findById(String id) {
        return null;
    }

    @Override
    public List<Point> findAll() {
        return null;
    }

    @Override
    public Point save(Point object) {
        return null;
    }

    @Override
    public Point saveAll(List<Point> object) {
        return null;
    }

    @Override
    public Point update(Point object) {
        return null;
    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public void delete(Point object) {

    }

    @Override
    public void deleteAll() {

    }
}
