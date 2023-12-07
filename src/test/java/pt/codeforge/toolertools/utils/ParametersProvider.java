package pt.codeforge.toolertools.utils;

import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;

public class ParametersProvider {

    public static final String PARAMETERS_PROVIDER_CLASS = "pt.codeforge.toolertools.utils.ParametersProvider";

    public static Stream<Arguments> getEnvPaths(){
        return Stream.of(
            Arguments.of("$HOMEDRIVE/$HOMEPATH"),
            Arguments.of("$TEMP/test"),
            Arguments.of("test/$HOMEPATH/"),
            Arguments.of("../$HOMEPATH/"),
            Arguments.of("./$HOMEPATH/"),
            Arguments.of("$HOMEDRIVE/$HOMEPATH/test"),
            Arguments.of("")
        );
    }
}
