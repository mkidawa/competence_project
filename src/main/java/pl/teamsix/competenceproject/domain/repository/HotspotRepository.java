package pl.teamsix.competenceproject.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.teamsix.competenceproject.domain.entity.Hotspot;

public interface HotspotRepository extends MongoRepository<Hotspot, String> {

}
