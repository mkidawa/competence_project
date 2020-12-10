package pl.teamsix.competenceproject.logic.analysis;

import com.mongodb.spark.MongoSpark;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Service;
import scala.collection.Seq;

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

    public void rankByUsersInHotspot(){
        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());
        Dataset<Row> df = MongoSpark.load(jsc).toDF();
        Dataset<Row> result =  df
                                .groupBy("hotspot")
                                .count()
                                .sort(col("count")
                                    .desc()
                                    );
        result.show(false);
        System.out.println("rankByUsersInHotspot");
    }
    public void rankByTimeSpentInHotspot(){
        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());
        Dataset<Row> df = MongoSpark.load(jsc).toDF();
        Dataset<Row> xd =  df.select(col("hotspot"),col("exitTime").cast("long").minus(col("entryTime").cast("long")).as("xd"),to_timestamp(col("exitTime")).minus(to_timestamp(col("entryTime"))).as("timeSpent"));
        xd.sort(col("xd").desc()).show(30);
        //xd.show(200);
        System.out.println(xd.count());
        Dataset<Row> times =  df.select(col("hotspot"),col("exitTime").cast("long").minus(col("entryTime").cast("long")).divide(60).as("timeSpent"));
        //Dataset<Row> result = times.groupBy("hotspot").agg(sum(col("timeSpent")),avg(col("timeSpent")),max(col("timeSpent")));
        Dataset<Row> result = times.groupBy("hotspot").agg(round(sum(col("timeSpent")),2),round(avg(col("timeSpent")),2),round(max(col("timeSpent")),2));
        result.show(false);
        System.out.println("rankByTimeSpentInHotspot");
    }

    public void rankByFrequentUsers(){
        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());
        Dataset<Row> df = MongoSpark.load(jsc).toDF();
        Dataset<Row> frequency =  df
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
        System.out.println("rankByFrequentUsers");
    }

    public void userTimeSpentInHotspot(){
        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());
        Dataset<Row> df = MongoSpark.load(jsc).toDF();
        Dataset<Row> times =  df
                .select(
                        col("user"),
                        col("hotspot"),
                        col("exitTime").cast("long")
                                .minus(col("entryTime").cast("long"))
                                .divide(60).as("timeSpent"))

                    .groupBy("user", "hotspot")
                    .agg(
                            round(sum(col("timeSpent")),2),
                            round(avg(col("timeSpent")), 2),
                            round(max(col("timeSpent")),2)
                    );
        times.show(false);
        System.out.println("userTimeSpentInHotspot");
    }

}
