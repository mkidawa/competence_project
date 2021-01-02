package pl.teamsix.competenceproject.logic.analysis;

import com.mongodb.spark.MongoSpark;
import com.mongodb.spark.config.ReadConfig;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.ml.clustering.KMeans;
import org.apache.spark.ml.clustering.KMeansModel;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.sql.*;
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

    public Dataset<Row> rankByUsersInHotspot(JavaSparkContext jsc){

        return MongoSpark.load(jsc).toDF()
                .groupBy("hotspot")
                .count()
                .sort(col("count")
                        .desc()
                );
    }

    public Dataset<Row> rankByTimeSpentInHotspot(JavaSparkContext jsc) {
        return  MongoSpark.load(jsc).toDF()
         .select(
                col("hotspot"),
                col("exitTime").cast("long")
                    .minus(col("entryTime").cast("long")).divide(60).as("timeSpent")
            )
            .groupBy("hotspot")
            .agg(
                    round(sum(col("timeSpent")), 2),
                    round(avg(col("timeSpent")), 2),
                    round(max(col("timeSpent")), 2)
            );
    }

    public Dataset<Row> rankByFrequentUsers(JavaSparkContext jsc) {
        return MongoSpark.load(jsc).toDF()
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
    }

    public Dataset<Row> userTimeSpentInHotspot(JavaSparkContext jsc){
        return MongoSpark.load(jsc).toDF()
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
    }

    public Dataset<Row> numberOfUsersByHours(JavaSparkContext jsc) {
        return MongoSpark.load(jsc).toDF()
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
    }

    public Dataset<Row> numberOfUsersByWeekDay(JavaSparkContext jsc){
        return MongoSpark.load(jsc).toDF()
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
    }

    public List<RowRecord> longestRoute(JavaSparkContext jsc){
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
        return records;
    }

    public Map<String,Integer> mostPopularNextHotspot(JavaSparkContext jsc) {
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
        return result;
    }

    public Dataset<Row> clusterByUsers(int k, JavaSparkContext jsc){
        VectorAssembler assembler = new VectorAssembler()
                .setInputCols(new String[]{"count"})
                .setOutputCol("features");
        Dataset<Row> dataset = assembler.transform(rankByUsersInHotspot(jsc).na().drop()).select("hotspot","features");

        KMeans kMeans = new KMeans().setK(k).setSeed(1L);
        KMeansModel model = kMeans.fit(dataset);

        return model.transform(dataset);
    }

    public Dataset<Row> clusterByTimeSpent(int k, JavaSparkContext jsc){
        VectorAssembler assembler = new VectorAssembler()
                .setInputCols(new String[]{"round(sum(timeSpent), 2)","round(avg(timeSpent), 2)","round(max(timeSpent), 2)"})
                .setOutputCol("features");
        Dataset<Row> dataset = assembler.transform(rankByTimeSpentInHotspot(jsc).na().drop()).select("hotspot","features");

        KMeans kMeans = new KMeans().setK(k).setSeed(1L);
        KMeansModel model = kMeans.fit(dataset);

        return model.transform(dataset);
    }

    public Dataset<Row> clusterByFrequentUser(int k, JavaSparkContext jsc){
        VectorAssembler assembler = new VectorAssembler()
                .setInputCols(new String[]{"maximum"})
                .setOutputCol("features");
        Dataset<Row> dataset = assembler.transform(rankByFrequentUsers(jsc).na().drop()).select("hotspot","features");

        KMeans kMeans = new KMeans().setK(k).setSeed(1L);
        KMeansModel model = kMeans.fit(dataset);

        return model.transform(dataset);
    }

    public Dataset<Row> clusterByUsersInWeekDay(int k, JavaSparkContext jsc){
        VectorAssembler assembler = new VectorAssembler()
                .setInputCols(new String[]{"People in Monday", "People in Tuesday","People in Wednesday","People in Thursday","People in Friday","People in Saturday","People in Sunday"})
                .setOutputCol("features");
        Dataset<Row> dataset = assembler.transform(numberOfUsersByWeekDay(jsc).na().drop()).select("hotspot","features");

        KMeans kMeans = new KMeans().setK(k).setSeed(1L);
        KMeansModel model = kMeans.fit(dataset);

        return model.transform(dataset);
    }

    public Dataset<Row> clusterByDayTime(int k, JavaSparkContext jsc){
        VectorAssembler assembler = new VectorAssembler()
                .setInputCols(new String[]{"0"})
                .setOutputCol("features");
        Dataset<Row> data = numberOfUsersByHours(jsc).na().drop().agg(
                sum(col("People in between 0-2")).as("People in between 0-2"),
                sum(col("People in between 2-4")).as("People in between 2-4"),
                sum(col("People in between 4-6")).as("People in between 4-6"),
                sum(col("People in between 6-8")).as("People in between 6-8"),
                sum(col("People in between 8-10")).as("People in between 8-10"),
                sum(col("People in between 10-12")).as("People in between 10-12"),
                sum(col("People in between 12-14")).as("People in between 12-14"),
                sum(col("People in between 14-16")).as("People in between 14-16"),
                sum(col("People in between 16-18")).as("People in between 16-18"),
                sum(col("People in between 18-20")).as("People in between 18-20"),
                sum(col("People in between 20-22")).as("People in between 20-22"),
                sum(col("People in between 22-24")).as("People in between 22-24")
        ).withColumn("time",lit(0));
        Column[] cols = Arrays
                .stream(data.columns())
                .filter(x -> ! x.equals("time"))
                .map(n -> struct(lit(n).alias("c"), col(n).alias("v")))
                .toArray(Column[]::new);

        Dataset<Row> exploded_df = data.select( col("time"), explode(array(cols)))
                .groupBy(col("col.c"))
                .pivot("time")
                .agg(first(col("col.v")))
                .orderBy("c");

        Dataset<Row> dataset = assembler.transform(exploded_df).select(col("c").as("times"),col("features"));

        KMeans kMeans = new KMeans().setK(k).setSeed(1L);
        KMeansModel model = kMeans.fit(dataset);

        return model.transform(dataset);
    }

    public static class RowRecord implements Serializable {
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
