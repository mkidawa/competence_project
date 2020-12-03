package pl.teamsix.competenceproject.logic.generation;

import org.springframework.stereotype.Service;
import pl.teamsix.competenceproject.domain.entity.User;
import pl.teamsix.competenceproject.domain.service.user.UserService;
import pl.teamsix.competenceproject.logic.FileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Integer.parseInt;
import static pl.teamsix.competenceproject.logic.Constants.FIRST_NAMES_DB_TXT;
import static pl.teamsix.competenceproject.logic.Constants.HOBBIES_DB_TXT;
import static pl.teamsix.competenceproject.logic.Constants.LAST_NAMES_DB_TXT;
import static pl.teamsix.competenceproject.logic.Constants.PROFESSION_DB_TXT;

@Service
public class UsersGenerator {

    private static final int BATCH_SIZE = 100;

    private final UserService userService;

    private final FileReader fileReader = new FileReader();
    private List<String> firstNamesList;
    private List<String> lastNamesList;
    private List<String> hobbiesList;
    private List<String> profileList; //in format (jobName,ageFrom,ageTo)

    public UsersGenerator(final UserService userService) {
        this.userService = userService;
        loadAllLists();
    }

    public void generate(int quantity) {
        for (int i = 0; i < quantity; i += BATCH_SIZE) {
            userService.saveAll(
                    IntStream.range(0, Math.min(quantity - i, BATCH_SIZE))
                            .mapToObj(x -> generateSingleUser())
                            .collect(Collectors.toList()));
        }
    }

    public User generateSingleUser() {
        Random rand = new Random();
        //getting first name
        String firstName = firstNamesList.get(rand.nextInt(firstNamesList.size()));
        //getting lastName
        String lastName = lastNamesList.get(rand.nextInt(lastNamesList.size()));
        //getting gender 50/50 chance
        char gender;
        if (rand.nextInt(2) == 1) {
            gender = 'F';
        } else {
            gender = 'M';
        }
        //getting profession and age
        String[] segments = profileList.get(rand.nextInt(profileList.size())).split(",");
        String profile = segments[0];
        int age = rand.nextInt(
                parseInt(segments[2]) - parseInt(segments[1])) + parseInt(segments[1]) + 1;
        //getting hobby/hobbies - up to 3
        int noOfHobbies = rand.nextInt(3) + 1;
        ArrayList interests = new ArrayList();
        for (int i = 0; i != noOfHobbies; i++) {
            interests.add(hobbiesList.get(rand.nextInt(hobbiesList.size())));
        }
        //creating phone number - american format
        String phoneNumber = "+1" + (rand.nextInt(800) + 200) + (rand.nextInt(9000000) + 1000000);

        return new User(firstName, lastName, age, gender, interests, profile, phoneNumber);
    }

    public void loadAllLists() {
        firstNamesList = fileReader.readFromSimpleFile(FIRST_NAMES_DB_TXT);
        lastNamesList = fileReader.readFromSimpleFile(LAST_NAMES_DB_TXT);
        hobbiesList = fileReader.readFromSimpleFile(HOBBIES_DB_TXT);
        profileList = fileReader.readFromSimpleFile(PROFESSION_DB_TXT);
    }
}
