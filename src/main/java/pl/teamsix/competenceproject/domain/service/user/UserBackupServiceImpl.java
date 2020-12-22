package pl.teamsix.competenceproject.domain.service.user;

import org.springframework.stereotype.Service;
import pl.teamsix.competenceproject.domain.entity.User;
import pl.teamsix.competenceproject.domain.entity.UserBackup;
import pl.teamsix.competenceproject.domain.exception.ObjectNotFound;
import pl.teamsix.competenceproject.domain.exception.UserNotFound;
import pl.teamsix.competenceproject.domain.repository.UserBackupRepository;
import pl.teamsix.competenceproject.logic.HashingProvider;

import java.util.List;
import java.util.Optional;

@Service
public class UserBackupServiceImpl implements UserBackupService {

    /*------------------------ FIELDS REGION ------------------------*/
    private final UserBackupRepository userBackupRepository;
    private final UserService userService;

    /*------------------------ METHODS REGION ------------------------*/
    public UserBackupServiceImpl(UserBackupRepository userBackupRepository,
                                 UserService userService) {
        this.userBackupRepository = userBackupRepository;
        this.userService = userService;
    }

    @Override
    public UserBackup findById(String id) throws ObjectNotFound {
        Optional<UserBackup> userBackup = userBackupRepository.findById(id);

        if (!userBackup.isPresent()) {
            throw new UserNotFound();
        }

        return userBackup.get();
    }

    @Override
    public List<UserBackup> findAll() throws ObjectNotFound {
        List<UserBackup> userBackups = userBackupRepository.findAll();

        if (userBackups == null || userBackups.size() == 0) {
            throw new UserNotFound();
        }

        return userBackups;
    }

    @Override
    public List<UserBackup> findLimitedNumberFromBeginning(int numberOfObjects)
            throws ObjectNotFound {
        return findAll().subList(0, numberOfObjects);
    }

    @Override
    public UserBackup save(UserBackup object) {
        return userBackupRepository.save(object);
    }

    @Override
    public List<UserBackup> saveAll(List<UserBackup> objects) {
        return userBackupRepository.saveAll(objects);
    }

    /**
     * Save method also updates when passed object exists in database.
     */
    @Override
    public UserBackup update(UserBackup object) {
        return userBackupRepository.save(object);
    }

    /**
     * SaveAll method also updates when passed object exists in database.
     */
    @Override
    public List<UserBackup> updateAll(List<UserBackup> objects) {
        return userBackupRepository.saveAll(objects);
    }

    @Override
    public void deleteById(String id) {
        userBackupRepository.deleteById(id);
    }

    @Override
    public void delete(UserBackup object) {
        userBackupRepository.delete(object);
    }

    @Override
    public void deleteAll() {
        userBackupRepository.deleteAll();
    }

    @Override
    public User findFakeUser(String id) throws ObjectNotFound {
        String hashedId = HashingProvider.hashString(id);

        return userService.findAll()
                .stream()
                .filter((user) -> user.getHashedId().equals(hashedId))
                .findFirst()
                .orElseThrow(UserNotFound::new);
    }
}
