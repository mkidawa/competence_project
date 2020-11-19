package pl.teamsix.competenceproject.logic.generation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    private List<String> profileList;

    public UsersGenerator(final UserService userService) {
        this.userService = userService;
        loadAllLists();

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

    public void loadAllLists() {
        firstNamesList = readFromSimpleFile("src/main/resources/firstNamesDB.txt");
        lastNamesList = readFromSimpleFile("src/main/resources/lastNamesDB.txt");
        hobbiesList = readFromSimpleFile("src/main/resources/hobbiesDB.txt");
        //TODO extract ages
        profileList = readFromSimpleFile("src/main/resources/professionDB.txt");;

    }

    public List readFromSimpleFile(String filePath) {
        ArrayList<String> arr = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
        {
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
