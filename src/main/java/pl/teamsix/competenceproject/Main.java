package pl.teamsix.competenceproject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.teamsix.competenceproject.domain.service.user.UserService;
import pl.teamsix.competenceproject.domain.service.hotspot.HotspotService;
import pl.teamsix.competenceproject.domain.service.trace.TraceService;

@SpringBootApplication
public class Main implements CommandLineRunner {

    /*------------------------ FIELDS REGION ------------------------*/
    // TODO THIS IS ONLY FOR SHOW PURPOSES IF NOT NEEDED THEN DELETE
    private final UserService userService;
    private final HotspotService hotspotService;
    private final TraceService traceService;

    /*------------------------ METHODS REGION ------------------------*/
    // TODO THIS IS ONLY FOR SHOW PURPOSES IF NOT NEEDED THEN DELETE
    public Main(UserService userService, HotspotService hotspotService, TraceService traceService) {
        this.userService = userService;
        this.hotspotService = hotspotService;
        this.traceService = traceService;
    }

    /**
     * This is the "real" Spring boot main method where fg. UI can be started.
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
