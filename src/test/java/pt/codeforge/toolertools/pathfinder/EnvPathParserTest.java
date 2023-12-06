package pt.codeforge.toolertools.pathfinder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class EnvPathParserTest {

    @ParameterizedTest
    @ValueSource(strings = "$HOME")
    void givenUnixValidEnvVariable_testgetEnvPath_shouldReturnValidPath(String input) {
        String result = EnvPathParser.getEnvPath(input);

        assertValidResult(input, result);
    }

    @ParameterizedTest
    @ValueSource(strings = "$HOME/$TEMP")
    void givenUnixMultipleValidEnvVariables_testgetEnvPath_shouldReturnValidPath(String input) {
        String result = EnvPathParser.getEnvPath(input);

        assertValidResult(input, result);
    }

    @ParameterizedTest
    @ValueSource(strings = "$HOME/%HOME%")
    void givenUnixAndMysMultipleValidEnvVariables_testgetEnvPath_shouldReturnValidPath(String input) {
        String result = EnvPathParser.getEnvPath(input);

        assertValidResult(input, result);
    }

    @Test
    void givenNullInput_testGetEnvPath_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> EnvPathParser.getEnvPath(null),
            "EnvPathParser#getEnvPath should throw IllegalArgumentException.class.");
    }

    private static void assertValidResult(String input, String result) {
        assertNotEquals(input, result, "Input should not be equal to result.");
        assertFalse(result.isEmpty(), "Result should not be empty.");
    }
}
