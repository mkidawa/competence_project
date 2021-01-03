package pl.teamsix.competenceproject.domain.service.user;

import pl.teamsix.competenceproject.domain.entity.User;
import pl.teamsix.competenceproject.domain.entity.UserBackup;
import pl.teamsix.competenceproject.domain.exception.ObjectNotFound;
import pl.teamsix.competenceproject.domain.service.BaseService;
import pl.teamsix.competenceproject.domain.service.DeleteService;
import pl.teamsix.competenceproject.domain.service.UpdateService;

public interface UserBackupService
        extends BaseService<UserBackup>, DeleteService<UserBackup>, UpdateService<UserBackup> {

    User findFakeUser(String id) throws ObjectNotFound;
}
