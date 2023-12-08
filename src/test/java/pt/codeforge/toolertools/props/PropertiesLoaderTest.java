package pt.codeforge.toolertools.props;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PropertiesLoaderTest {

    @BeforeAll
    static void setup() {
        System.setProperty("test", "src/test/resources/test.properties"
        );
    }

    @Test
    void givenWrongInput_testLoadFromPropertyName_shouldThrowPropertiesLoadingException() {
        assertThrows(PropertiesLoadingException.class, () -> PropertiesLoader.loadFromPropertyName("wrong"));
    }

    @Test
    void givenEmptyInput_testLoadFromPropertyName_shouldThrowPropertiesLoadingException() {
        assertThrows(PropertiesLoadingException.class, () -> PropertiesLoader.loadFromPropertyName(""));
    }

    @Test
    void givenNullInput_testLoadFromPropertyName_shouldThrowPropertiesLoadingException() {
        assertThrows(PropertiesLoadingException.class, () -> PropertiesLoader.loadFromPropertyName(null));
    }
}
