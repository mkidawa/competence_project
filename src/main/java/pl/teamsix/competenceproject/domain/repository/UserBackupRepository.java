package pl.teamsix.competenceproject.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.teamsix.competenceproject.domain.entity.UserBackup;

public interface UserBackupRepository extends MongoRepository<UserBackup, String> {

}
