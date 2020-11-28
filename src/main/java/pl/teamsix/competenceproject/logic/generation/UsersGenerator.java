package pl.teamsix.competenceproject.logic.generation;

import static java.lang.Integer.parseInt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import pl.teamsix.competenceproject.domain.entity.User;
import pl.teamsix.competenceproject.domain.service.user.UserService;

@Service
public class UsersGenerator {

    private static final int BATCH_SIZE = 100;

    private final UserService userService;

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
                    IntStream.range(0, Math.min(quantity - i, BATCH_SIZE)).mapToObj(x -> generateSingleUser())
                            .collect(Collectors.toList()));
        }
    }

    private User generateSingleUser() {
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
        String profile;
        int age;
        String[] segments = profileList.get(rand.nextInt(profileList.size())).split(",");
        profile = segments[0];
        age = rand.nextInt(parseInt(segments[2]) - parseInt(segments[1])) + parseInt(segments[1]) + 1;
        //getting hobby/hobbies - up to 3
        int noOfHobbies = rand.nextInt(3) + 1;
        ArrayList interests = new ArrayList();
        for (int i = 0; i != noOfHobbies; i++) {
            interests.add(hobbiesList.get(rand.nextInt(hobbiesList.size())));
        }
        //creating phone number - american format
        String phoneNumber = "+1" + (rand.nextInt(800) + 200) + (rand.nextInt(9000000) + 1000000);

        final User user = new User(firstName, lastName, age, gender, interests, profile, phoneNumber);
        return user;
    }

    public void loadAllLists() {
        firstNamesList = readFromSimpleFile("src/main/resources/firstNamesDB.txt");
        lastNamesList = readFromSimpleFile("src/main/resources/lastNamesDB.txt");
        hobbiesList = readFromSimpleFile("src/main/resources/hobbiesDB.txt");
        profileList = readFromSimpleFile("src/main/resources/professionDB.txt");
        ;

    }

    public List readFromSimpleFile(String filePath) {
        ArrayList<String> arr = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                arr.add(sCurrentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }
}
