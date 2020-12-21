package pl.teamsix.competenceproject.domain.service.user;

import org.springframework.stereotype.Service;
import pl.teamsix.competenceproject.domain.entity.User;
import pl.teamsix.competenceproject.domain.exception.UserNotFound;
import pl.teamsix.competenceproject.domain.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    /*------------------------ FIELDS REGION ------------------------*/
    private final UserRepository userRepository;

    /*------------------------ METHODS REGION ------------------------*/
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(String id) throws UserNotFound {
        Optional<User> person = userRepository.findById(id);

        if (!person.isPresent()) {
            throw new UserNotFound();
        }

        return person.get();
    }

    @Override
    public List<User> findAll() throws UserNotFound {
        List<User> people = userRepository.findAll();

        if (people == null || people.size() == 0) {
            throw new UserNotFound();
        }

        return people;
    }

    @Override
    public User save(User object) {
        return userRepository.save(object);
    }

    @Override
    public List<User> saveAll(List<User> objects) {
        return userRepository.saveAll(objects);
    }

    /**
     * Save method also updates when passed object exists in database.
     */
    @Override
    public User update(User object) {
        return userRepository.save(object);
    }

    /**
     * SaveAll method also updates when passed object exists in database.
     */
    @Override
    public List<User> updateAll(List<User> objects) {
        return userRepository.saveAll(objects);
    }

    @Override
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public void delete(User object) {
        userRepository.delete(object);
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }
}
