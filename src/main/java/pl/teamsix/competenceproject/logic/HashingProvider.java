package pl.teamsix.competenceproject.logic;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class HashingProvider {

    /*------------------------ FIELDS REGION ------------------------*/

    /*------------------------ METHODS REGION ------------------------*/
    public static String hashString(String text) {
        return Hashing.sha256().hashString(text, StandardCharsets.UTF_8).toString();
    }
}
