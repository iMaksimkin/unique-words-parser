import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.imaksimkin.utils.DataUtil;

import static org.junit.jupiter.api.Assertions.*;
import static ru.imaksimkin.clients.Client.getProperty;

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
}


