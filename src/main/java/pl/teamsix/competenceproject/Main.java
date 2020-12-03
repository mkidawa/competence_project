package pl.teamsix.competenceproject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.teamsix.competenceproject.ui.UserInterface;

@SpringBootApplication
public class Main implements CommandLineRunner {

    /*------------------------ FIELDS REGION ------------------------*/
    private final UserInterface userInterface;

    /*------------------------ METHODS REGION ------------------------*/
    public Main(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    /**
     * This is the "real" Spring boot main method where fg. UI can be started.
     */
    @Override
    public void run(String... args) throws Exception {
        userInterface.initialize();
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
