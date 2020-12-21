package pl.teamsix.competenceproject.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

    /*------------------------ FIELDS REGION ------------------------*/

    /*------------------------ METHODS REGION ------------------------*/
    public List readFromSimpleFile(String filePath) {
        ArrayList<String> data = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(filePath))) {
            String sCurrentLine;

            while ((sCurrentLine = reader.readLine()) != null) {
                data.add(sCurrentLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
}
