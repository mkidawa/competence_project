package pl.teamsix.competenceproject.domain.service.user;

import pl.teamsix.competenceproject.domain.entity.User;
import pl.teamsix.competenceproject.domain.service.BaseService;
import pl.teamsix.competenceproject.domain.service.DeleteService;
import pl.teamsix.competenceproject.domain.service.UpdateService;

public interface UserService extends BaseService<User>, DeleteService<User>, UpdateService<User> {

}
