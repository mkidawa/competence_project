package pl.teamsix.competenceproject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main implements CommandLineRunner {

    /**
     * This is the real Spring boot main method where fg. UI can be started
     */
    @Override
    public void run(String... args) throws Exception {
        // TODO DELETE BELOW CODE, THIS IS ONLY FOR SHOW PURPOSES
        System.out.println("---------------------------------");
        System.out.println("SAMPLE TEXT TO DELETE");
        System.out.println("---------------------------------");
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
