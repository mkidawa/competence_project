package pl.teamsix.competenceproject.ui;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Component;
import pl.teamsix.competenceproject.domain.exception.ObjectNotFound;
import pl.teamsix.competenceproject.domain.service.hotspot.HotspotService;
import pl.teamsix.competenceproject.domain.service.trace.TraceService;
import pl.teamsix.competenceproject.domain.service.user.UserBackupService;
import pl.teamsix.competenceproject.domain.service.user.UserService;
import pl.teamsix.competenceproject.logic.analysis.DataAnalysis;
import pl.teamsix.competenceproject.logic.anonymization.DataAnonymizator;
import pl.teamsix.competenceproject.logic.generation.HotspotsGenerator;
import pl.teamsix.competenceproject.logic.generation.TracesGenerator;
import pl.teamsix.competenceproject.logic.generation.UsersGenerator;

import java.util.Scanner;

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

    private final Scanner scanner = new Scanner(System.in);

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

    public void initialize() throws ObjectNotFound {
        JavaSparkContext jsc = new JavaSparkContext(sparkTrace.sparkContext());
        String choice;
        printAuthorsInfo();

        do {
            printMenu();
            choice = readFromStringInput();
            performSelectedAction(jsc, choice);
        } while (!choice.equals(String.valueOf(0)));

        jsc.close();
    }

    private void printAuthorsInfo() {
        //        TODO Authors of program and overall description
        printSeparator();
        System.out.println("");
        printSeparator();
    }

    private void printMenu() {
        //        TODO FINISH - ADD ALL OPTIONS
        System.out.println("CRUD");
        System.out.println("\t1. Display All Users");
        System.out.println("\t2. Display All Hotspots");
        System.out.println("\t4. Display Certain Number Of Users");
        System.out.println("\t5. Generate Users");
        System.out.println("Anonymization");
        System.out.println("\t6. Anonymizate Users Data");
        System.out.println("Analysis");
        System.out.println("\t8. Number Of Users By Hours");
        System.out.println("\t0. Exit");
        System.out.print("\nYour Choice: ");
    }

    private void performSelectedAction(JavaSparkContext jsc, String choice) throws ObjectNotFound {

        // TODO HERE YOU HAVE ALL ANALYSIS METHODS CALL, REMEMBER TO ADD OPTIONS TO CHOOSE HOW
        //  MANY OF ITEMS SHOULD BE SHOWN I MEAN 200 NUMBER
        //        this.dataAnalysis.rankByUsersInHotspot(jsc).show(200, false);
        //        this.dataAnalysis.rankByTimeSpentInHotspot(jsc).show(200, false);
        //        this.dataAnalysis.userTimeSpentInHotspot(jsc).show(200, false);
        //        this.dataAnalysis.rankByFrequentUsers(jsc).show(200, false);
        //        this.dataAnalysis.numberOfUsersByHours(jsc).show(200, false);
        //        System.out.println(this.dataAnalysis.longestRoute(jsc));
        //        this.dataAnalysis.numberOfUsersByHours(jsc).show(200, false);

        switch (choice) {
            case "1": {
                //TODO ADD NICE WAY TO DISPLAY THIS DATA, MAYBE FANCY LIBRARY WITH TABLES ???
                System.out.println(userService.findAll());
                break;
            }
            case "2": {
                System.out.println(hotspotService.findAll());
                break;
            }
            case "3": {

                break;
            }
            case "4": {
                final int numberOfObjects = requestNumberOfObjects();
                System.out.println(userService.findLimitedNumberFromBeginning(numberOfObjects));
                break;
            }
            case "5": {
                final int numberOfItemToGenerate = requestNumberOfItemToGenerate("Users");
                usersGenerator.generate(numberOfItemToGenerate);
                break;
            }
            case "6": {
                dataAnonymizator.anonymizateUser();
                break;
            }
            case "7": {

                break;
            }
            case "8": {
                final int numberOfRows = requestNumberOfRows();
                Dataset<Row> result = this.dataAnalysis.numberOfUsersByHours(jsc);
                // TODO OR MAYBE SAVE TO FILE ???
                result.show(numberOfRows, false);
                break;
            }
            case "9": {

                break;
            }
            case "10": {

                break;
            }
            default: {
                //TODO WRONG VALUE INFO
            }
        }
    }

    private int requestNumberOfRows() {
        System.out.print("Enter Number Of Rows To Display: ");
        return readFromIntegerInput();
    }

    private int requestNumberOfObjects() {
        System.out.print("Enter Number Of Rows To Display: ");
        return readFromIntegerInput();
    }

    private int requestNumberOfItemToGenerate(String itemName) {
        System.out.print("Enter Number Of " + itemName + " To Generate: ");
        return readFromIntegerInput();
    }

    private void printSeparator() {
        System.out.println("\n-----------------------------------------------------------------\n");
    }

    private String readFromStringInput() {
        return scanner.nextLine();
    }

    private double readFromDoubleInput() {
        return Double.valueOf(readFromStringInput());
    }

    private int readFromIntegerInput() {
        return Integer.valueOf(readFromStringInput());
    }
}
