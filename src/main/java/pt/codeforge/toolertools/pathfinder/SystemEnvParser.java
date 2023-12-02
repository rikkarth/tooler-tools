package pt.codeforge.toolertools.pathfinder;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class SystemEnvParser {

    private SystemEnvParser() {
        throw new AssertionError("SystemEnvParser should not be instantiated.");
    }

    public static String getEnvPath(String input) {
        AtomicReference<String> inputCopy = new AtomicReference<>(input);
        List<String> pathComponentsList = Arrays.asList((inputCopy.get()).split("[/\\\\:-]"));
        parseIfMys(inputCopy, pathComponentsList);
        parseIfUnix(inputCopy, pathComponentsList);
        return inputCopy.get();
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
