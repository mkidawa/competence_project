package pl.teamsix.competenceproject.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.teamsix.competenceproject.domain.entity.Point;

public interface PointRepository extends MongoRepository<Point, String> {

}
