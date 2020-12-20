package pl.teamsix.competenceproject.logic.analysis;

import com.mongodb.spark.MongoSpark;
import com.mongodb.spark.config.ReadConfig;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.scheduler.IndirectTaskResult;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.spark.sql.functions.*;

/*

Map<String, String> readOverrides = new HashMap<String, String>();
// readOverrides.put("database", "database_name");
readOverrides.put("collection", "details");
ReadConfig readConfig = ReadConfig.create(jsc).withOptions(readOverrides);

// Read another table 2 (details table )
JavaMongoRDD<Document> detailsRdd = MongoSpark.load(jsc, readConfig);
 */
/* trace schema
root
 |-- _id: struct (nullable = true)
 |    |-- oid: string (nullable = true)
 |-- user: struct (nullable = true)
 |    |-- $ref: string (nullable = true)
 |    |-- $id: struct (nullable = true)
 |    |    |-- oid: string (nullable = true)
 |-- hotspot: struct (nullable = true)
 |    |-- $ref: string (nullable = true)
 |    |-- $id: struct (nullable = true)
 |    |    |-- oid: string (nullable = true)
 |-- entryTime: timestamp (nullable = true)
 |-- exitTime: timestamp (nullable = true)
 |-- _class: string (nullable = true)
 */
@Service
public class DataAnalysis {
    private SparkSession sparkTrace = SparkSession.builder()
            .master("local")
            .appName("appname")
            .config("spark.mongodb.input.uri", "mongodb://localhost:27017/competence_project_name.trace")
            .config("spark.mongodb.output.uri", "mongodb://localhost:27017/competence_project_name.trace")
            .getOrCreate();

    public void rankByUsersInHotspot(){
        JavaSparkContext jsc = new JavaSparkContext(sparkTrace.sparkContext());
        Dataset<Row> df = MongoSpark.load(jsc).toDF();
        Dataset<Row> result = df
                .groupBy("hotspot")
                .count()
                .sort(col("count")
                        .desc()
                );
        result.show(false);
        jsc.close();
        System.out.println("rankByUsersInHotspot");
    }

    public void rankByTimeSpentInHotspot() {
        JavaSparkContext jsc = new JavaSparkContext(sparkTrace.sparkContext());
        Dataset<Row> df = MongoSpark.load(jsc).toDF();
        Dataset<Row> xd =  df.select(
                col("hotspot"),
                col("exitTime").cast("long")
                        .minus(col("entryTime").cast("long")).as("xd"),
                to_timestamp(col("exitTime"))
                        .minus(to_timestamp(col("entryTime"))).as("timeSpent")
                ).sort(col("xd").desc()
        );
        xd.show(30);
        System.out.println(xd.count());
        Dataset<Row> times = df.select(
                col("hotspot"),
                col("exitTime").cast("long")
                    .minus(col("entryTime").cast("long")).divide(60).as("timeSpent")
            );
        //Dataset<Row> result = times.groupBy("hotspot").agg(sum(col("timeSpent")),avg(col("timeSpent")),max(col("timeSpent")));
        Dataset<Row> result = times
                .groupBy("hotspot")
                .agg(
                        round(sum(col("timeSpent")), 2),
                        round(avg(col("timeSpent")), 2),
                        round(max(col("timeSpent")), 2)
                );
        result.show(false);
        jsc.close();
        System.out.println("rankByTimeSpentInHotspot");
    }

    public void rankByFrequentUsers() {
        JavaSparkContext jsc = new JavaSparkContext(sparkTrace.sparkContext());
        Dataset<Row> df = MongoSpark.load(jsc).toDF();
        Dataset<Row> frequency = df
                .groupBy("hotspot", "user")
                .count()
                .sort(col("count")
                        .desc()
                )
                .groupBy("hotspot").agg(
                        first("user"),
                        max("count").as("maximum"))
                .sort(
                        col("maximum").desc()
                );
        frequency.show(false);
        jsc.close();
        System.out.println("rankByFrequentUsers");
    }

    public void userTimeSpentInHotspot(){
        JavaSparkContext jsc = new JavaSparkContext(sparkTrace.sparkContext());
        Dataset<Row> df = MongoSpark.load(jsc).toDF();
        Dataset<Row> times = df
                .select(
                        col("user"),
                        col("hotspot"),
                        col("exitTime").cast("long")
                                .minus(col("entryTime").cast("long"))
                                .divide(60).as("timeSpent"))

                .groupBy("user", "hotspot")
                .agg(
                        round(sum(col("timeSpent")), 2),
                        round(avg(col("timeSpent")), 2),
                        round(max(col("timeSpent")), 2)
                );
        times.show(false);
        jsc.close();
        System.out.println("userTimeSpentInHotspot");
    }

    public void numberOfUsersByHours() {
        JavaSparkContext jsc = new JavaSparkContext(sparkTrace.sparkContext());
        Dataset<Row> temp = MongoSpark.load(jsc).toDF()
                .select(
                        col("hotspot"),
                        when(hour(col("entryTime")).between(0, 2), 1)
                                .when(hour(col("exitTime")).between(0, 2), 1)
                                .otherwise(0).as("entered0"),
                        when(hour(col("entryTime")).between(2, 4), 1)
                                .when(hour(col("exitTime")).between(2, 4), 1)
                                .otherwise(0).as("entered1"),
                        when(hour(col("entryTime")).between(4, 6), 1)
                                .when(hour(col("exitTime")).between(4, 6), 1)
                                .otherwise(0).as("entered2"),
                        when(hour(col("entryTime")).between(6, 8), 1)
                                .when(hour(col("exitTime")).between(6, 8), 1)
                                .otherwise(0).as("entered3"),
                        when(hour(col("entryTime")).between(8, 10), 1)
                                .when(hour(col("exitTime")).between(8, 10), 1)
                                .otherwise(0).as("entered4"),
                        when(hour(col("entryTime")).between(10, 12), 1)
                                .when(hour(col("exitTime")).between(10, 12), 1)
                                .otherwise(0).as("entered5"),
                        when(hour(col("entryTime")).between(12, 14), 1)
                                .when(hour(col("exitTime")).between(12, 14), 1)
                                .otherwise(0).as("entered6"),
                        when(hour(col("entryTime")).between(14, 16), 1)
                                .when(hour(col("exitTime")).between(14, 16), 1)
                                .otherwise(0).as("entered7"),
                        when(hour(col("entryTime")).between(16, 18), 1)
                                .when(hour(col("exitTime")).between(16, 18), 1)
                                .otherwise(0).as("entered8"),
                        when(hour(col("entryTime")).between(18, 20), 1)
                                .when(hour(col("exitTime")).between(18, 20), 1)
                                .otherwise(0).as("entered9"),
                        when(hour(col("entryTime")).between(20, 22), 1)
                                .when(hour(col("exitTime")).between(20, 22), 1)
                                .otherwise(0).as("entered10"),
                        when(hour(col("entryTime")).between(22, 24), 1)
                                .when(hour(col("exitTime")).between(22, 24), 1)
                                .otherwise(0).as("entered11")
                )
                .groupBy("hotspot")
                .agg(
                        sum("entered0").as("People in between 0-2"),
                        sum("entered1").as("People in between 2-4"),
                        sum("entered2").as("People in between 4-6"),
                        sum("entered3").as("People in between 6-8"),
                        sum("entered4").as("People in between 8-10"),
                        sum("entered5").as("People in between 10-12"),
                        sum("entered6").as("People in between 12-14"),
                        sum("entered7").as("People in between 14-16"),
                        sum("entered8").as("People in between 16-18"),
                        sum("entered9").as("People in between 18-20"),
                        sum("entered10").as("People in between 20-22"),
                        sum("entered11").as("People in between 22-24")
                );

        temp.show(200, false);
        jsc.close();
        System.out.println("numberOfUsersByHours");
    }

    public void numberOfUsersByWeekDay(){
        JavaSparkContext jsc = new JavaSparkContext(sparkTrace.sparkContext());
        Dataset<Row> temp = MongoSpark.load(jsc).toDF()
                .select(
                col("hotspot")
                ,when(dayofweek(col("entryTime")).equalTo(1),1).otherwise(0).as("entered1")
                ,when(dayofweek(col("entryTime")).equalTo(2),1).otherwise(0).as("entered2")
                ,when(dayofweek(col("entryTime")).equalTo(3),1).otherwise(0).as("entered3")
                ,when(dayofweek(col("entryTime")).equalTo(4),1).otherwise(0).as("entered4")
                ,when(dayofweek(col("entryTime")).equalTo(5),1).otherwise(0).as("entered5")
                ,when(dayofweek(col("entryTime")).equalTo(6),1).otherwise(0).as("entered6")
                ,when(dayofweek(col("entryTime")).equalTo(7),1).otherwise(0).as("entered7")

        )
                .groupBy("hotspot")
                .agg(
                        sum("entered2").as("People in Monday"),
                        sum("entered3").as("People in Tuesday"),
                        sum("entered4").as("People in Wednesday"),
                        sum("entered5").as("People in Thursday"),
                        sum("entered6").as("People in Friday"),
                        sum("entered7").as("People in Saturday"),
                        sum("entered1").as("People in Sunday")
                );

        temp.show(200,false);
        System.out.println("numberOfUsersByWeekDay");
    }

    public void longestRoute(){

        JavaSparkContext jsc = new JavaSparkContext(sparkTrace.sparkContext());
        Dataset<Row> tempTrace = MongoSpark.load(jsc).toDF()
            .select(
                    col("user.$id.oid").as("user"),
                    col("hotspot.$id.oid").as("hotspot"),
                    col("entryTime")
            ).sort(
                    col("user"),
                    col("entryTime").asc()
            );
        Map<String, String> readOverrides = new HashMap<>();
        readOverrides.put("collection", "user");
        ReadConfig readConfig = ReadConfig.create(jsc).withOptions(readOverrides);
        Dataset<Row> tempUser = MongoSpark.load(jsc, readConfig).toDF()
                .select(
                        col("_id.oid")
                );

        String id;
        List<Row> route,
                list = tempUser.collectAsList(),
                currentRoute = new ArrayList<>(),
                longestKnownRoute = new ArrayList<>();
        Dataset<Row> set;
        List<RowRecord> records = new ArrayList<>();

        for(Row row : list){
            id = row.get(0).toString();
            set = tempTrace
                    .select("hotspot")
                    .where("user = '" + id + "'");
            route = set.collectAsList();
            longestKnownRoute.clear();
            for(Row currRoute: route){
                if(currentRoute.contains(currRoute)){
                    if(currentRoute.size() > longestKnownRoute.size()){
                        longestKnownRoute = new ArrayList<>(currentRoute);
                    }
                    currentRoute.clear();
                } else {
                    currentRoute.add(currRoute);
                }
            }
            if(currentRoute.size() > longestKnownRoute.size()){
                longestKnownRoute = new ArrayList<>(currentRoute);
            }
            records.add(new RowRecord(id, longestKnownRoute.size(), new ArrayList<>(longestKnownRoute)));
        }
        for (RowRecord record : records) {
            System.out.println(record.toString());
            System.out.println("longestRoute");
        }
        jsc.close();
    }

    public Map<String,Integer> mostPopularNextHotspot() {

        JavaSparkContext jsc = new JavaSparkContext(sparkTrace.sparkContext());
        Dataset<Row> tempTrace = MongoSpark.load(jsc).toDF()
                .select(
                        col("user.$id.oid").as("user"),
                        col("hotspot.$id.oid").as("hotspot"),
                        col("entryTime")
                ).sort(
                        col("user"),
                        col("entryTime").asc()
                );
        Map<String, String> readOverrides = new HashMap<>();
        readOverrides.put("collection", "user");
        ReadConfig readConfig = ReadConfig.create(jsc).withOptions(readOverrides);
        Dataset<Row> tempHotspot = MongoSpark.load(jsc, readConfig).toDF()
                .select(
                        col("_id.oid")
                ).limit(5);
        String id;
        List<Row> userTrace,
                list = tempHotspot.collectAsList();
        Dataset<Row> set;
        HashMap<String,Integer> allUsersTraces = new HashMap<>();
        List<String> userTraces = new ArrayList<>();

        for(Row row : list){
            id = row.get(0).toString();
            set = tempTrace
                    .select("hotspot")
                    .where("user = '" + id + "'");
            userTrace = set.collectAsList();

            for(int i = 0; i < userTrace.size()-1; i++){
                userTraces.add(
                                userTrace.get(i).get(0).toString()+ "," +
                                userTrace.get(i+1).get(0).toString());
            }
            userTraces.forEach(a -> allUsersTraces.merge(a,1,Integer::sum));
            userTraces.clear();
        }

        Map<String,Integer> result = allUsersTraces.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new)
                );
        jsc.close();
        return result;
    }

    static class RowRecord implements Serializable {
        String userId;
        Integer numOfTraces;
        String traces;

        public RowRecord(String userId, Integer numOfTraces, List<Row> traces) {
            this.userId = userId;
            this.numOfTraces = numOfTraces;
            StringBuilder stringBuilder = new StringBuilder();
            for(Row row : traces){
                stringBuilder.append(row).append(";");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            this.traces = stringBuilder.toString();
        }

        @Override
        public String toString() {
            return "userId='" + userId + '\'' +
                    ", numOfTraces=" + numOfTraces +
                    ", traces='" + traces + '\'';
        }
    }
}
