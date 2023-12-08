package pt.codeforge.toolertools.props;

import java.util.Properties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PropertiesLoaderTest {

    @BeforeAll
    static void setup() {
        System.setProperty("test", "src/test/resources/test.properties"
        );
    }

    @Test
    void givenWrongInput_testLoadFromPropertyName_shouldThrowPropertiesLoadingException(){
        Assertions.assertThrows(PropertiesLoadingException.class, () -> PropertiesLoader.loadFromPropertyName("wrong"));
    }

    @Test
    void givenEmptyInput_testLoadFromPropertyName_shouldThrowPropertiesLoadingException(){
        Assertions.assertThrows(PropertiesLoadingException.class, () -> PropertiesLoader.loadFromPropertyName(""));
    }

    @Test
    void givenNullInput_testLoadFromPropertyName_shouldThrowPropertiesLoadingException() {
        Assertions.assertThrows(PropertiesLoadingException.class, () -> PropertiesLoader.loadFromPropertyName(null));
    }
}
