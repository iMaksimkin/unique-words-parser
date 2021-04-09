import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.imaksimkin.utils.DataUtil;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static ru.imaksimkin.clients.Client.getProperty;
import static ru.imaksimkin.utils.DataUtil.pathToFile;

class TestApp {

    //TODO: create more unit tests
    public final String propertyPath = "properties/database.properties";

    @Test
    @DisplayName("Passed while getting property")
    void getPropertyTest() {
        String username = "username";
        String valueFromProperty = getProperty(propertyPath, username);
        assertEquals("user123", valueFromProperty, "Failed while getting property");
    }

    @Test
    @DisplayName("Test assert NullPointerException while getting property")
    void getBadPropertyTest() {

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            assertNull(getProperty(propertyPath, "root"));
        });
        assertEquals("There is no such property", exception.getMessage());
    }

    @Test
    @DisplayName("Test URL validator")
    void validateURL() {
        assertTrue(new DataUtil().validateURL("https://sibirsoft.com"),
                "Failed while trying to validate URL with HTTPS protocol");
        assertTrue(new DataUtil().validateURL("http://apache.org"),
                "Failed while trying to validate URL with HTTP protocol");
    }

    @Test
    @DisplayName("Test saving artifact, and work with data")
    void checkMethodWorkedWithData() {
        String words = "A A A -A easy easy common common common common ee !";
        String wordsFromSavedFile = "";
        Map<String, Integer> wordsAndFrequencies = new DataUtil().deleteNonUniqueValuesFromString(words);
        try {
            wordsFromSavedFile = FileUtils.readFileToString(new File(pathToFile), "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        assertEquals(2, wordsAndFrequencies.get("easy"));
        assertEquals(4, wordsAndFrequencies.get("common"));
        assertEquals(1, wordsAndFrequencies.get("ee"));
        assertEquals(null, wordsAndFrequencies.get("!"));
        assertEquals(null, wordsAndFrequencies.get("A"));
        assertEquals(null, wordsAndFrequencies.get("-A"));
        assertEquals(words, wordsFromSavedFile);
    }
}


