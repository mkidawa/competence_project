package pl.teamsix.competenceproject.domain.service.user;

import pl.teamsix.competenceproject.domain.entity.User;
import pl.teamsix.competenceproject.domain.entity.UserBackup;
import pl.teamsix.competenceproject.domain.exception.ObjectNotFound;
import pl.teamsix.competenceproject.domain.service.CrudService;

public interface UserBackupService extends CrudService<UserBackup> {

    User findFakeUser(String id) throws ObjectNotFound;
}
