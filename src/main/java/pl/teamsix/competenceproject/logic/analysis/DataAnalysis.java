package pl.teamsix.competenceproject.logic.analysis;

import com.mongodb.spark.MongoSpark;
import com.mongodb.spark.rdd.api.java.JavaMongoRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.bson.Document;
import org.springframework.stereotype.Service;

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
    private final static SparkSession spark = SparkSession.builder()
            .master("local")
            .appName("appname")
            .config("spark.mongodb.input.uri", "mongodb://localhost:27017/competence_project_name.trace")
            .config("spark.mongodb.output.uri", "mongodb://localhost:27017/competence_project_name.trace")
            .getOrCreate();

    public void rankByUsersInHotspot() {
        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());
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
        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());
        Dataset<Row> df = MongoSpark.load(jsc).toDF();
        Dataset<Row> xd = df.select(col("hotspot"), col("exitTime").cast("long").minus(col("entryTime").cast("long")).as("xd"), to_timestamp(col("exitTime")).minus(to_timestamp(col("entryTime"))).as("timeSpent"));
        xd.sort(col("xd").desc()).show(30);
        //xd.show(200);
        System.out.println(xd.count());
        Dataset<Row> times = df.select(col("hotspot"), col("exitTime").cast("long").minus(col("entryTime").cast("long")).divide(60).as("timeSpent"));
        //Dataset<Row> result = times.groupBy("hotspot").agg(sum(col("timeSpent")),avg(col("timeSpent")),max(col("timeSpent")));
        Dataset<Row> result = times.groupBy("hotspot").agg(round(sum(col("timeSpent")), 2), round(avg(col("timeSpent")), 2), round(max(col("timeSpent")), 2));
        result.show(false);
        jsc.close();
        System.out.println("rankByTimeSpentInHotspot");
    }

    public void rankByFrequentUsers() {
        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());
        Dataset<Row> df = MongoSpark.load(jsc).toDF();
        Dataset<Row> frequency = df
                .groupBy("hotspot", "user")
                .count()
                .sort(col("count")
                        .desc()
                )
                .groupBy("hotspot").agg(first("user"), max("count").as("maximum"))
                .sort(
                        col("maximum").desc()
                );
        frequency.show(false);
        jsc.close();
        System.out.println("rankByFrequentUsers");
    }

    public void userTimeSpentInHotspot() {
        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());
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
        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());
        Dataset<Row> df = MongoSpark.load(jsc).toDF();
        Dataset<Row> temp = df
                .select(
                        col("hotspot"),
                        when(
                                hour(col("entryTime"))
                                        .between(0, 2), 1)
                                .when(
                                        hour(col("exitTime"))
                                                .between(0, 2), 1)
                                .otherwise(0).as("entered0"),
                        when(hour(col("entryTime")).between(2, 4), 1).when(hour(col("exitTime")).between(2, 4), 1).otherwise(0).as("entered1"), when(hour(col("entryTime")).between(4, 6), 1).when(hour(col("exitTime")).between(4, 6), 1).otherwise(0).as("entered2"), when(hour(col("entryTime")).between(6, 8), 1).when(hour(col("exitTime")).between(6, 8), 1).otherwise(0).as("entered3"), when(hour(col("entryTime")).between(8, 10), 1).when(hour(col("exitTime")).between(8, 10), 1).otherwise(0).as("entered4"), when(hour(col("entryTime")).between(10, 12), 1).when(hour(col("exitTime")).between(10, 12), 1).otherwise(0).as("entered5"), when(hour(col("entryTime")).between(12, 14), 1).when(hour(col("exitTime")).between(12, 14), 1).otherwise(0).as("entered6"), when(hour(col("entryTime")).between(14, 16), 1).when(hour(col("exitTime")).between(14, 16), 1).otherwise(0).as("entered7"), when(hour(col("entryTime")).between(16, 18), 1).when(hour(col("exitTime")).between(16, 18), 1).otherwise(0).as("entered8"), when(hour(col("entryTime")).between(18, 20), 1).when(hour(col("exitTime")).between(18, 20), 1).otherwise(0).as("entered9"), when(hour(col("entryTime")).between(20, 22), 1).when(hour(col("exitTime")).between(20, 22), 1).otherwise(0).as("entered10"), when(hour(col("entryTime")).between(22, 24), 1).when(hour(col("exitTime")).between(22, 24), 1).otherwise(0).as("entered11")
                )
                .groupBy("hotspot")
                .agg(
                        sum("entered0").as("People in between 0-2"), sum("entered1").as("People in between 2-4"), sum("entered2").as("People in between 4-6"), sum("entered3").as("People in between 6-8"), sum("entered4").as("People in between 8-10"), sum("entered5").as("People in between 10-12"), sum("entered6").as("People in between 12-14"), sum("entered7").as("People in between 14-16"), sum("entered8").as("People in between 16-18"), sum("entered9").as("People in between 18-20"), sum("entered10").as("People in between 20-22"), sum("entered11").as("People in between 22-24")
                );

        temp.show(200, false);
        jsc.close();
        System.out.println("numberOfUsersByHours");
    }
}
