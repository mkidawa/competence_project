package pl.teamsix.competenceproject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.teamsix.competenceproject.domain.service.person.PersonService;
import pl.teamsix.competenceproject.domain.service.point.PointService;
import pl.teamsix.competenceproject.domain.service.trace.TraceService;

@SpringBootApplication
public class Main implements CommandLineRunner {

    /*------------------------ FIELDS REGION ------------------------*/
    // TODO THIS IS ONLY FOR SHOW PURPOSES IF NOT NEEDED THEN DELETE
    private final PersonService personService;
    private final PointService pointService;
    private final TraceService traceService;

    /*------------------------ METHODS REGION ------------------------*/
    // TODO THIS IS ONLY FOR SHOW PURPOSES IF NOT NEEDED THEN DELETE
    public Main(PersonService personService, PointService pointService, TraceService traceService) {
        this.personService = personService;
        this.pointService = pointService;
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
