package pl.teamsix.competenceproject.logic.generation;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import pl.teamsix.competenceproject.domain.entity.User;
import pl.teamsix.competenceproject.domain.service.user.UserService;

@Service
public class UsersGenerator {

    private static final int BATCH_SIZE = 100;

    private final UserService userService;

    public UsersGenerator(final UserService userService) {
        this.userService = userService;
    }

    public void generate(int quantity) {
        for (int i = 0; i < quantity; i += BATCH_SIZE) {
            userService.saveAll(IntStream.range(0, Math.min(quantity - i, BATCH_SIZE))
                    .mapToObj(x -> generateSingleUser())
                    .collect(Collectors.toList()));
        }
    }

    private User generateSingleUser() {
        final User user = new User();
        //TODO Ola - generate all field values
        return user;
    }
}
