package pt.codeforge.toolertools.pathfinder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class EnvPathParserTest {

    @BeforeEach
    void setup() {
        System.setProperty("$HOME", "test");
    }

    @ParameterizedTest
    @ValueSource(strings = "$HOMEDRIVE/$HOMEPATH")
    void testGetEnvPath(String input) {

        System.out.println(EnvPathParser.getEnvPath(input));
    }

    @Test
    void testBadGetEnvPath() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> EnvPathParser.getEnvPath(null));
    }

}
