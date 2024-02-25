package pt.codeforge.toolertools.zip;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class BaseZipBuilderTest {

    @Test
    @Disabled
    void givenNoTargetPath_testCreateZip_shouldThrowIllegalStateException() {
        BaseZipBuilder baseZipBuilder = new BaseZipBuilder();

        assertThrows(IllegalStateException.class, baseZipBuilder::createZip);
    }

    @Test
    @Disabled
    void givenIncorrectPath_testCreateZip_shouldThrowZipBuilderException(){
        BaseZipBuilder baseZipBuilder = new BaseZipBuilder("src/test/resources/output");

        assertThrows(ZipBuilderException.class, baseZipBuilder::createZip);
    }

    @Test
    @Disabled
    void givenValidPath_testCreateZip(){
        BaseZipBuilder baseZipBuilder = new BaseZipBuilder("src/test/resources/output/my.zip");

        baseZipBuilder.createZip();
    }

    @Test
    @Disabled("wip")
    void test() {
        BaseZipBuilder zipBuilder = new BaseZipBuilder().setTargetPath("src/test/resources/output/my.zip");

        /*List<File> files = Arrays.asList(
            new File("src/test/resources/test_resource_1.xml"),
            new File("src/test/resources/test_resource_2.xml"),
            new File("src/test/resources/test.properties")
        );*/

        List<File> files = Arrays.asList(
            new File("src/test/resources/")
        );

        zipBuilder.addAllToZip(files);

        zipBuilder.createZip();
    }

}
