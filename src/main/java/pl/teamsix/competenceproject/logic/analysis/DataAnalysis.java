package pl.teamsix.competenceproject.logic.analysis;

import com.mongodb.spark.MongoSpark;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Service;
import scala.collection.Seq;

import static org.apache.spark.sql.functions.col;

/*

Map<String, String> readOverrides = new HashMap<String, String>();
// readOverrides.put("database", "database_name");
readOverrides.put("collection", "details");
ReadConfig readConfig = ReadConfig.create(jsc).withOptions(readOverrides);

// Read another table 2 (details table )
JavaMongoRDD<Document> detailsRdd = MongoSpark.load(jsc, readConfig);
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
        result.show();
    }
    public void rankByTimeSpentInHotspot(){
        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());
        Dataset<Row> df = MongoSpark.load(jsc).toDF();
        Dataset<Row> result =  df.groupBy("hotspot").sum((Seq<String>) org.apache.spark.sql.functions.datediff(col("exitTime"),col("entryTime")));

        result.show();

    }

}
