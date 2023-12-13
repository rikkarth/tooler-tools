package pt.codeforge.toolertools.props;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PropertiesLoaderTest {

    @BeforeAll
    static void setup() {
        System.setProperty("test", "src/test/resources/test.properties");
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

    @ParameterizedTest
    @ValueSource(strings = "src/test/resources/wrong.properties")
    void givenWrongInput_testLoadFromStringPath_shouldThrowPropertiesLoadingException(String input) {
        assertThrows(PropertiesLoadingException.class, () -> PropertiesLoader.loadFromPath(input));
    }

    @ParameterizedTest
    @ValueSource(strings = "src/test/resources/wrong.properties")
    void givenWrongInput_testLoadFromPath_shouldThrowPropertiesLoadingException(String input) {
        Path path = Paths.get(input);
        assertThrows(PropertiesLoadingException.class, () -> PropertiesLoader.loadFromPath(path));
    }

    @ParameterizedTest
    @ValueSource(strings = "src/test/resources/wrong.properties")
    void givenWrongInput_testLoadFromFile_shouldThrowPropertiesLoadingException(String input) {
        File file = new File(input);
        assertThrows(PropertiesLoadingException.class, () -> PropertiesLoader.loadFromFile(file));
    }

    @Test
    void givenEmptyInput_testLoadFromStringPath_shouldThrowPropertiesLoadingException() {
        assertThrows(PropertiesLoadingException.class, () -> PropertiesLoader.loadFromPath(""));
    }

    @Test
    void givenEmptyInput_testLoadFromPath_shouldThrowPropertiesLoadingException() {
        Path path = Paths.get("");
        assertThrows(PropertiesLoadingException.class, () -> PropertiesLoader.loadFromPath(path));
    }

    @Test
    void givenEmptyInput_testLoadFromFile_shouldThrowPropertiesLoadingException() {
        File file = new File("");
        assertThrows(PropertiesLoadingException.class, () -> PropertiesLoader.loadFromFile(file));
    }

    @Test
    void givenNullInput_testLoadFromStringPath_shouldThrowPropertiesLoadingException() {
        assertThrows(IllegalArgumentException.class, () -> PropertiesLoader.loadFromPath((String) null));
    }

    @Test
    void givenNullInput_testLoadFromPath_shouldThrowPropertiesLoadingException() {
        assertThrows(IllegalArgumentException.class, () -> PropertiesLoader.loadFromPath((Path) null));
    }

    @Test
    void givenNullInput_testLoadFromFile_shouldThrowPropertiesLoadingException() {
        assertThrows(IllegalArgumentException.class, () -> PropertiesLoader.loadFromFile(null));
    }
}
