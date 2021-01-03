package pl.teamsix.competenceproject.logic.analysis;

import org.apache.spark.sql.Row;

import java.io.Serializable;
import java.util.List;

public class RowRecord implements Serializable {
    String userId;
    Integer numOfTraces;
    String traces;

    public RowRecord(String userId, Integer numOfTraces, List<Row> traces) {
        this.userId = userId;
        this.numOfTraces = numOfTraces;
        StringBuilder stringBuilder = new StringBuilder();
        for (Row row : traces) {
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
