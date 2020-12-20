package pl.teamsix.competenceproject;


import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.teamsix.competenceproject.domain.service.hotspot.HotspotService;
import pl.teamsix.competenceproject.domain.service.trace.TraceService;
import pl.teamsix.competenceproject.domain.service.user.UserService;
import pl.teamsix.competenceproject.logic.analysis.DataAnalysis;
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
    private final DataAnalysis dataAnalysis;

    /*------------------------ METHODS REGION ------------------------*/
    public Main(UserService userService, HotspotService hotspotService,
                TraceService traceService, UsersGenerator usersGenerator,
                HotspotsGenerator hotspotsGenerator, TracesGenerator tracesGenerator, DataAnalysis dataAnalysis) {
        this.userService = userService;
        this.hotspotService = hotspotService;
        this.traceService = traceService;
        this.usersGenerator = usersGenerator;
        this.hotspotsGenerator = hotspotsGenerator;
        this.tracesGenerator = tracesGenerator;
        this.dataAnalysis = dataAnalysis;
    }

    /**
     * This is the "real" Spring boot main method where fg. UI can be started.
     */
    @Override
    public void run(String... args) throws Exception {

        SparkSession sparkTrace = SparkSession.builder()
                .master("local")
                .appName("appname")
                .config("spark.mongodb.input.uri", "mongodb://localhost:27017/competence_project_name.trace")
                .config("spark.mongodb.output.uri", "mongodb://localhost:27017/competence_project_name.trace")
                .getOrCreate();
        JavaSparkContext jsc = new JavaSparkContext(sparkTrace.sparkContext());
//        this.dataAnalysis.rankByUsersInHotspot(jsc).show(200, false);
//        this.dataAnalysis.rankByTimeSpentInHotspot(jsc).show(200, false);
//        this.dataAnalysis.userTimeSpentInHotspot(jsc).show(200, false);
//        this.dataAnalysis.rankByFrequentUsers(jsc).show(200, false);
//        this.dataAnalysis.numberOfUsersByHours(jsc).show(200, false);
//        System.out.println(this.dataAnalysis.longestRoute(jsc));
        this.dataAnalysis.numberOfUsersByHours(jsc).show(200, false);
        jsc.close();
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
