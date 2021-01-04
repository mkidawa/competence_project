package pl.teamsix.competenceproject.logic;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static pl.teamsix.competenceproject.logic.Constants.FIRST_NAMES_DB_TXT;

@Disabled
class FileReaderTest {

    private final FileReader fileReader = new FileReader();

    @Test
    void readFromSimpleFile() {
        List<String> result = fileReader.readFromSimpleFile(FIRST_NAMES_DB_TXT);
        result.forEach((item) -> System.out.println(item));
    }
}
