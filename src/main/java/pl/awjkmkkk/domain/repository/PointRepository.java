package pl.awjkmkkk.domain.repository;

import pl.awjkmkkk.domain.entity.Point;

import java.util.List;
import java.util.UUID;

public class PointRepository implements BaseRepository<Point> {

    /*------------------------ FIELDS REGION ------------------------*/

    /*------------------------ METHODS REGION ------------------------*/

    @Override
    public List<Point> findAll() {
        return null;
    }

    @Override
    public Point findByUuid(UUID uuid) {
        return null;
    }

    @Override
    public void save(Point object) {

    }

    @Override
    public void update(Point object) {

    }

    @Override
    public void deleteById(UUID uuid) {

    }

    @Override
    public void deleteAll() {

    }
}
