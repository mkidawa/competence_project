package pl.teamsix.competenceproject.logic.anonymization;

import com.google.common.hash.Hashing;
import org.springframework.stereotype.Component;
import pl.teamsix.competenceproject.domain.entity.User;
import pl.teamsix.competenceproject.domain.entity.UserBackup;
import pl.teamsix.competenceproject.domain.exception.ObjectNotFound;
import pl.teamsix.competenceproject.domain.service.user.UserBackupService;
import pl.teamsix.competenceproject.domain.service.user.UserService;
import pl.teamsix.competenceproject.logic.generation.UsersGenerator;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class DataAnonymizator {

    /*------------------------ FIELDS REGION ------------------------*/
    private final UserService userService;
    private final UserBackupService userBackupService;
    private final UsersGenerator usersGenerator;

    /*------------------------ METHODS REGION ------------------------*/
    public DataAnonymizator(UserService userService, UserBackupService userBackupService,
                            UsersGenerator usersGenerator) {
        this.userService = userService;
        this.userBackupService = userBackupService;
        this.usersGenerator = usersGenerator;
    }

    /**
     * Call this method in order to anonymizate collections of users
     */
    public void anonymizateUser() throws ObjectNotFound {
        List<User> users = userService.findAll();

        users.forEach((user) -> {
            UserBackup userBackup = new UserBackup(
                    user.getFirstName(), user.getLastName(), user.getAge(), user.getGender(),
                    user.getInterests(), user.getProfile(), user.getPhoneNumber()
            );

            userBackupService.save(userBackup);
            changeUserProperties(user, userBackup);
        });

        userService.updateAll(users);
    }

    private void changeUserProperties(User user, UserBackup userBackup) {
        user.setHashedId(Hashing.sha256().hashString(
                userBackup.getId(), StandardCharsets.UTF_8
        ).toString());

        User randomUser = null;
        do {
            randomUser = usersGenerator.generateSingleUser();
        } while (user.getFirstName().equals(randomUser.getFirstName())
                || user.getLastName().equals(randomUser.getLastName()));

        user.setFirstName(randomUser.getFirstName());
        user.setLastName(randomUser.getLastName());
        user.setAge(randomUser.getAge());
        user.setGender(randomUser.getGender());
        user.setInterests(randomUser.getInterests());
        user.setProfile(randomUser.getProfile());
        user.setPhoneNumber(randomUser.getPhoneNumber());
    }
}
