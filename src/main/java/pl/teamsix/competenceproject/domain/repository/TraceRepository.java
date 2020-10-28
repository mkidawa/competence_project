package pl.teamsix.competenceproject.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.teamsix.competenceproject.domain.entity.Trace;

public interface TraceRepository extends MongoRepository<Trace, String> {

}
