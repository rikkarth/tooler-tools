package pt.codeforge.toolertools.pathfinder;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Utility class for parsing and expanding environment variables in file paths. This class provides static methods for
 * parsing input paths, replacing environment variable placeholders with their respective values on both Windows and
 * Unix-like systems.
 *
 * <p><b>Note:</b> This class should not be instantiated as it consists of static utility methods only.
 *
 * @see EnvPathParser#getEnvPath(String)
 * @see EnvPathParser#parseIfMys(AtomicReference, List)
 * @see EnvPathParser#parseIfUnix(AtomicReference, List)
 */
public class EnvPathParser {

    private EnvPathParser() {
        throw new AssertionError("EnvPathParser should not be instantiated.");
    }

    /**
     * Parses the input path by replacing environment variable placeholders with their respective values. Environment
     * variable placeholders are denoted by percent signs ('%') on both ends for Windows and by a dollar sign ('$') at
     * the beginning for Unix-like systems. The method handles both styles. After parsing, the method returns the
     * normalized path as a String.
     *
     * @param input The input path to be parsed. Must not be null.
     * @return The parsed and normalized path as a String.
     * @throws IllegalArgumentException If the input parameter is null.
     */
    public static String getEnvPath(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null.");
        }

        AtomicReference<String> inputCopy = new AtomicReference<>(input);
        List<String> pathComponentsList = Arrays.asList((inputCopy.get()).split("[/\\\\:-]"));
        parseIfMys(inputCopy, pathComponentsList);
        parseIfUnix(inputCopy, pathComponentsList);

        return Paths.get(inputCopy.get()).toString();
    }

    private static void parseIfMys(AtomicReference<String> inputCopy,
        List<String> pathComponentsList) {
        pathComponentsList
            .stream()
            .filter(component -> component.startsWith("%") && component.endsWith("%"))
            .forEach(component -> {
                String envVarName = component.substring(1, component.length() - 1);
                String envVarValue = System.getenv(envVarName);
                if (envVarValue != null) {
                    inputCopy.set((inputCopy.get()).replace(component, envVarValue));
                }
            });
    }

    private static void parseIfUnix(AtomicReference<String> inputCopy,
        List<String> pathComponentsList) {
        pathComponentsList
            .stream()
            .filter(component -> component.startsWith("$"))
            .forEach(component -> {
                String envVarName = component.substring(1);
                String envVarValue = System.getenv(envVarName);
                if (envVarValue != null) {
                    inputCopy.set((inputCopy.get()).replace(component, envVarValue));
                }
            });
    }
}
