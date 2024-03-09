package pt.codeforge.toolertools.zip;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class BaseZipBuilderTest {

    @Test
    void givenNoTargetPath_testCreateZip_shouldThrowIllegalStateException() {
        BaseZipBuilder baseZipBuilder = new BaseZipBuilder();

        assertThrows(IllegalStateException.class, baseZipBuilder::createZip);
    }

    @Test
    void givenIncorrectPath_testCreateZip_shouldThrowZipBuilderException() {
        BaseZipBuilder baseZipBuilder = new BaseZipBuilder("src/test/resources/output");

        assertThrows(ZipBuilderException.class, baseZipBuilder::createZip);
    }

    @Test
    void givenValidPathAndNoFile_testCreateZip_shouldCreateEmptyZip() throws IOException {
        String targetPath = "src/test/resources/output/my.zip";
        BaseZipBuilder baseZipBuilder = new BaseZipBuilder(targetPath);

        baseZipBuilder.createZip();

        assertTrue(ZipHelper.isEmptyZip(targetPath), "zip output file should be empty");
    }

    @Test
    @Disabled
    void test() {
        BaseZipBuilder zipBuilder = new BaseZipBuilder().setTargetPath("src/test/resources/output/my.zip");

        /*List<File> files = Arrays.asList(
            new File("src/test/resources/test_resource_1.xml"),
            new File("src/test/resources/test_resource_2.xml"),
            new File("src/test/resources/test.properties")
        );*/

        List<File> files = Arrays.asList(
            new File("src/test/resources/zipThisDir/")
        );

        zipBuilder.addAllToZip(files);

        zipBuilder.createZip();
    }

}
