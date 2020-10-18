package pl.awjkmkkk.domain.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import pl.awjkmkkk.domain.exception.ReaderException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class JsonIO {

    /*------------------------ FIELDS REGION ------------------------*/
    private Gson gson = new Gson();

    /*------------------------ METHODS REGION ------------------------*/
    public <T> void writeToFile(Class<T> clazz, List<T> jsonData,
                                String filename) throws ReaderException {
        try (FileWriter fileWriter = new FileWriter(filename)) {
            Type type = TypeToken.getParameterized(List.class, clazz).getType();
            gson.toJson(jsonData, type, fileWriter);
        } catch (IOException e) {
            throw new ReaderException(e);
        }
    }

    public <T> List readFromFile(Class<T> clazz, String filename) throws ReaderException {
        try (FileReader fileReader = new FileReader(filename)) {
            Type type = TypeToken.getParameterized(List.class, clazz).getType();
            return gson.fromJson(fileReader, type);
        } catch (IOException e) {
            throw new ReaderException(e);
        }
    }
}
