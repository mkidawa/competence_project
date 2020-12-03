package pl.teamsix.competenceproject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.teamsix.competenceproject.domain.service.hotspot.HotspotService;
import pl.teamsix.competenceproject.domain.service.trace.TraceService;
import pl.teamsix.competenceproject.domain.service.user.UserBackupService;
import pl.teamsix.competenceproject.domain.service.user.UserService;
import pl.teamsix.competenceproject.logic.analysis.DataAnalysis;
import pl.teamsix.competenceproject.logic.anonymization.DataAnonymizator;
import pl.teamsix.competenceproject.logic.generation.HotspotsGenerator;
import pl.teamsix.competenceproject.logic.generation.TracesGenerator;
import pl.teamsix.competenceproject.logic.generation.UsersGenerator;

@SpringBootApplication
public class Main implements CommandLineRunner {

    /*------------------------ FIELDS REGION ------------------------*/
    private final UserService userService;
    private final UserBackupService userBackupService;
    private final HotspotService hotspotService;
    private final TraceService traceService;
    private final UsersGenerator usersGenerator;
    private final HotspotsGenerator hotspotsGenerator;
    private final TracesGenerator tracesGenerator;
    private final DataAnonymizator dataAnonymizator;
    private final DataAnalysis dataAnalysis;

    /*------------------------ METHODS REGION ------------------------*/
    public Main(UserService userService, UserBackupService userBackupService,
                HotspotService hotspotService, TraceService traceService,
                UsersGenerator usersGenerator, HotspotsGenerator hotspotsGenerator,
                TracesGenerator tracesGenerator, DataAnonymizator dataAnonymizator,
                DataAnalysis dataAnalysis) {
        this.userService = userService;
        this.userBackupService = userBackupService;
        this.hotspotService = hotspotService;
        this.traceService = traceService;
        this.usersGenerator = usersGenerator;
        this.hotspotsGenerator = hotspotsGenerator;
        this.tracesGenerator = tracesGenerator;
        this.dataAnonymizator = dataAnonymizator;
        this.dataAnalysis = dataAnalysis;
    }

    /**
     * This is the "real" Spring boot main method where fg. UI can be started.
     */
    @Override
    public void run(String... args) throws Exception {
        // TODO DELETE BELOW CODE, THIS IS ONLY FOR SHOW PURPOSES
        System.out.println("---------------------------------");
        System.out.println(this.hotspotService.findAll().size());
        System.out.println("---------------------------------");
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
