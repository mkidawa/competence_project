package pl.teamsix.competenceproject.ui;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Component;
import pl.teamsix.competenceproject.domain.entity.Hotspot;
import pl.teamsix.competenceproject.domain.entity.Trace;
import pl.teamsix.competenceproject.domain.entity.User;
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

import java.util.List;
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
        printSeparator();
        System.out.println("Competence project");
        System.out.println("Authors:");
        System.out.println("Michał Suszek - 216895");
        System.out.println("Piotr Plichtowski - 216867");
        System.out.println("Aleksandra Ruta - 216880");
        System.out.println("Dominik Szczepański - 216897");
        System.out.println("Aleksandra Wnuk - 216924");
        System.out.println("Michał Kidawa - 216796");
        System.out.println("Jan Karwowski - 216793");
        System.out.println("Kamil Kowalewski - 216806");
        printSeparator();
    }

    private void printMenu() {
        System.out.println("CRUD");
        System.out.println("\t1. Display All Users");
        System.out.println("\t2. Display All Hotspots");
        System.out.println("\t3. Display All Traces");
        System.out.println("\t4. Display Certain Number Of Users");
        System.out.println("\t5. Display Certain Number Of Hotspots");
        System.out.println("\t6. Display Certain Number Of Traces");
        System.out.println("\t7. Generate Data");
        System.out.println("\t8. Delete All Data From Database");
        System.out.println("Anonymization");
        System.out.println("\t9. Anonymizate Users Data");
        System.out.println("Analysis");
        System.out.println("\t10. Number Of Users By Hours");
        System.out.println("\t11. Rank By Users In Hotspot");
        System.out.println("\t12. Rank By Time Spent In Hotspot");
        System.out.println("\t13. User Time Spent In Hotspot");
        System.out.println("\t14. Rank By Frequent Users");
        System.out.println("\t15. Longest Route");
        System.out.println("\t0. Exit");
        System.out.print("\nYour Choice: ");
    }

    private void performSelectedAction(JavaSparkContext jsc, String choice) throws ObjectNotFound {

        switch (choice) {
            case "1": {
                System.out.println("Number Of Users: " + userService.count());
                printUsers(userService.findAll());
                break;
            }
            case "2": {
                System.out.println("Number Of Hotspots: " + hotspotService.count());
                printHotspots(hotspotService.findAll());
                break;
            }
            case "3": {
                System.out.println("Number Of Traces: " + traceService.count());
                printTraces(traceService.findAll());
                break;
            }
            case "4": {
                final int numberOfObjects = requestNumberOfObjects();
                printUsers(userService.findLimitedNumberFromBeginning(numberOfObjects));
                break;
            }
            case "5": {
                final int numberOfObjects = requestNumberOfObjects();
                printHotspots(hotspotService.findLimitedNumberFromBeginning(numberOfObjects));
                break;
            }
            case "6": {
                final int numberOfObjects = requestNumberOfObjects();
                printTraces(traceService.findLimitedNumberFromBeginning(numberOfObjects));
                break;
            }
            case "7": {
                final int numberOfUsersToGenerate = requestNumberOfItemToGenerate("Users");
                usersGenerator.generate(numberOfUsersToGenerate);
                final int numberOfHotspotsToGenerate = requestNumberOfItemToGenerate("Hotspots");
                hotspotsGenerator.generate(numberOfHotspotsToGenerate);
                System.out.println("Traces Generation");
                final double duration = requestDuration();
                final int avgMovements = requestAvgMovementsPerHour();
                tracesGenerator.generate(
                        userService.findAll(), hotspotService.findAll(),
                        java.sql.Date.valueOf(java.time.LocalDate.now()),
                        duration, avgMovements
                );
                break;
            }
            case "8": {
                userService.deleteAll();
                userBackupService.deleteAll();
                hotspotService.deleteAll();
                traceService.deleteAll();
                break;
            }
            case "9": {
                dataAnonymizator.anonymizateUser();
                break;
            }
            case "10": {
                final int numberOfRows = requestNumberOfRows();
                Dataset<Row> result = this.dataAnalysis.numberOfUsersByHours(jsc);
                result.show(numberOfRows, false);
                break;
            }
            case "11": {
                final int numberOfRows = requestNumberOfRows();
                Dataset<Row> result = this.dataAnalysis.rankByUsersInHotspot(jsc);
                result.show(numberOfRows, false);
                break;
            }
            case "12": {
                final int numberOfRows = requestNumberOfRows();
                Dataset<Row> result = this.dataAnalysis.rankByTimeSpentInHotspot(jsc);
                result.show(numberOfRows, false);
                break;
            }
            case "13": {
                final int numberOfRows = requestNumberOfRows();
                Dataset<Row> result = this.dataAnalysis.userTimeSpentInHotspot(jsc);
                result.show(numberOfRows, false);
                break;
            }
            case "14": {
                final int numberOfRows = requestNumberOfRows();
                Dataset<Row> result = this.dataAnalysis.rankByFrequentUsers(jsc);
                result.show(numberOfRows, false);
                break;
            }
            case "15": {
                printLongestRoute(this.dataAnalysis.longestRoute(jsc));
                break;
            }
            default: {
                System.out.println("Wrong number, please choose again");
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

    private double requestDuration() {
        System.out.print("Enter Duration Time In Hours: ");
        return readFromDoubleInput();
    }

    private int requestAvgMovementsPerHour() {
        System.out.print("Enter Average Movements Per Hour: ");
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

    private void printUsers(List<User> users) {
        String format = "%-25s%-15s%-30s%-7s%-8s%-50s%-20s%-10s%n";
        System.out.printf(format, "id", "firstName", "lastName", "age",
                "gender", "interests", "profile", "phoneNumber");
        for (User user : users) {
            System.out.printf(
                    format, user.getId(), user.getFirstName(), user.getLastName(), user.getAge(),
                    user.getGender(), user.getInterests(), user.getProfile(), user.getPhoneNumber()
            );
        }
    }

    private void printHotspots(List<Hotspot> hotspots) {
        String format = "%-25s%-60s%-12s%-20s%-20s%n";
        System.out.printf(format, "id", "name", "type", "x", "y");
        for (Hotspot hotspot : hotspots) {
            System.out.printf(
                    format, hotspot.getId(), hotspot.getName(),
                    hotspot.getType(), hotspot.getX(), hotspot.getY()
            );
        }
    }

    private void printTraces(List<Trace> traces) {
        String format = "%-27s%-27s%-27s%-30s%-30s%n";
        System.out.printf(format, "id", "user", "hotspot", "entryTime", "exitTime");
        for (Trace trace : traces) {
            System.out.printf(
                    format, trace.getId(), trace.getUser().getId(),
                    trace.getHotspot().getId(), trace.getEntryTime(), trace.getExitTime()
            );
        }
    }

    private void printLongestRoute(List<DataAnalysis.RowRecord> longestRoutes) {
        for (DataAnalysis.RowRecord longestRoute : longestRoutes) {
            System.out.println(longestRoute.toString());
        }
    }
}
