package pl.teamsix.competenceproject.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.teamsix.competenceproject.domain.entity.User;

public interface UserRepository extends MongoRepository<User, String> {

}
