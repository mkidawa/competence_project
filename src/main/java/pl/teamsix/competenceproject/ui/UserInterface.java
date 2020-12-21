package pl.teamsix.competenceproject.ui;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Component;
import pl.teamsix.competenceproject.domain.service.hotspot.HotspotService;
import pl.teamsix.competenceproject.domain.service.trace.TraceService;
import pl.teamsix.competenceproject.domain.service.user.UserBackupService;
import pl.teamsix.competenceproject.domain.service.user.UserService;
import pl.teamsix.competenceproject.logic.analysis.DataAnalysis;
import pl.teamsix.competenceproject.logic.anonymization.DataAnonymizator;
import pl.teamsix.competenceproject.logic.generation.HotspotsGenerator;
import pl.teamsix.competenceproject.logic.generation.TracesGenerator;
import pl.teamsix.competenceproject.logic.generation.UsersGenerator;

@Component
public class UserInterface {

    /*------------------------ FIELDS REGION ------------------------*/
    public static final String MONGO_DB_TRACE_COLLECTION_PATH =
            "mongodb://localhost:27017/competence_project_name.trace";

    private final UserService userService;
    private final UserBackupService userBackupService;
    private final HotspotService hotspotService;
    private final TraceService traceService;

    private final UsersGenerator usersGenerator;
    private final HotspotsGenerator hotspotsGenerator;
    private final TracesGenerator tracesGenerator;

    private final DataAnonymizator dataAnonymizator;
    private final DataAnalysis dataAnalysis;

    private final SparkSession sparkTrace = SparkSession.builder()
            .master("local")
            .appName("appname")
            .config("spark.mongodb.input.uri", MONGO_DB_TRACE_COLLECTION_PATH)
            .config("spark.mongodb.output.uri", MONGO_DB_TRACE_COLLECTION_PATH)
            .getOrCreate();

    /*------------------------ METHODS REGION ------------------------*/
    public UserInterface(UserService userService, UserBackupService userBackupService,
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

    public void initialize() {
        // TODO HERE IMPLEMENT ALL CMD STUFF - FIELD SHOWN ABOVE ARE INJECTED USING IOC - SO THEY
        //  ARE INITIALIZED IN CTOR AUTOMATICALLY

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
}
