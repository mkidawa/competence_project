package pl.teamsix.competenceproject.domain.service.user;

import org.springframework.stereotype.Service;
import pl.teamsix.competenceproject.domain.entity.UserBackup;
import pl.teamsix.competenceproject.domain.exception.ObjectNotFound;
import pl.teamsix.competenceproject.domain.exception.PersonNotFound;
import pl.teamsix.competenceproject.domain.repository.UserBackupRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserBackupServiceImpl implements UserBackupService {

    /*------------------------ FIELDS REGION ------------------------*/
    private final UserBackupRepository userBackupRepository;

    /*------------------------ METHODS REGION ------------------------*/
    public UserBackupServiceImpl(UserBackupRepository userBackupRepository) {
        this.userBackupRepository = userBackupRepository;
    }

    @Override
    public UserBackup findById(String id) throws ObjectNotFound {
        Optional<UserBackup> userBackup = userBackupRepository.findById(id);

        if (!userBackup.isPresent()) {
            throw new PersonNotFound();
        }

        return userBackup.get();
    }

    @Override
    public List<UserBackup> findAll() throws ObjectNotFound {
        List<UserBackup> userBackups = userBackupRepository.findAll();

        if (userBackups == null || userBackups.size() == 0) {
            throw new PersonNotFound();
        }

        return userBackups;
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
}
