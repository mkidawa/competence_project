package pl.teamsix.competenceproject.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.teamsix.competenceproject.domain.entity.Person;

public interface PersonRepository extends MongoRepository<Person, String> {

}
