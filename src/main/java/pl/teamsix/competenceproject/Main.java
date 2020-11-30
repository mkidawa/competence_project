package pl.teamsix.competenceproject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.teamsix.competenceproject.domain.service.hotspot.HotspotService;
import pl.teamsix.competenceproject.domain.service.trace.TraceService;
import pl.teamsix.competenceproject.domain.service.user.UserService;
import pl.teamsix.competenceproject.logic.generation.HotspotsGenerator;
import pl.teamsix.competenceproject.logic.generation.TracesGenerator;
import pl.teamsix.competenceproject.logic.generation.UsersGenerator;

@SpringBootApplication
public class Main implements CommandLineRunner {

    /*------------------------ FIELDS REGION ------------------------*/
    private final UserService userService;
    private final HotspotService hotspotService;
    private final TraceService traceService;
    private final UsersGenerator usersGenerator;
    private final HotspotsGenerator hotspotsGenerator;
    private final TracesGenerator tracesGenerator;

    /*------------------------ METHODS REGION ------------------------*/
    public Main(UserService userService, HotspotService hotspotService,
                TraceService traceService, UsersGenerator usersGenerator,
                HotspotsGenerator hotspotsGenerator, TracesGenerator tracesGenerator) {
        this.userService = userService;
        this.hotspotService = hotspotService;
        this.traceService = traceService;
        this.usersGenerator = usersGenerator;
        this.hotspotsGenerator = hotspotsGenerator;
        this.tracesGenerator = tracesGenerator;
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
