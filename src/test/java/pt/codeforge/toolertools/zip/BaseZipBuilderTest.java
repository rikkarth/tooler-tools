package pt.codeforge.toolertools.zip;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BaseZipBuilderTest {

    private static final String TARGET_PATH = "src/test/resources/output/my.zip";

    @BeforeEach
    void setup() throws IOException {
        Files.deleteIfExists(Paths.get(TARGET_PATH));
    }

    @Test
    void givenNoTargetPath_testCreateZip_shouldThrowIllegalStateException() {
        BaseZipBuilder baseZipBuilder = new BaseZipBuilder();

        assertThrows(IllegalStateException.class, baseZipBuilder::createZip,
            "should throw IllegalStateException when createZip is called without setting target path");
    }

    @Test
    void givenNullTargetPath_testBaseZipBuilder_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> new BaseZipBuilder((Path) null),
            "should throw NullPointerException when Path is null");

        assertThrows(NullPointerException.class, () -> new BaseZipBuilder((String) null),
            "should throw NullPointerException when String is null");
    }

    @Test
    void givenEmptyTargetPath_testBaseZipBuilder_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new BaseZipBuilder(""),
            "should throw IllegalArgumentException when String is empty");

        Path emptyPath = Paths.get("");
        assertThrows(IllegalArgumentException.class, () -> new BaseZipBuilder(emptyPath),
            "should throw IllegalArgumentException when Path is empty");
    }

    @Test
    void givenSetterNullTargetPath_testBaseZipBuilder_shouldThrowNullPointerException() {
        BaseZipBuilder baseZipBuilder = new BaseZipBuilder();

        assertThrows(NullPointerException.class, () -> baseZipBuilder.setTargetPath((Path) null),
            "should throw NullPointerException when Path is null");

        assertThrows(NullPointerException.class, () -> baseZipBuilder.setTargetPath((String) null),
            "should throw NullPointerException when String is null");
    }

    @Test
    void givenSetterEmptyTargetPath_testBaseZipBuilder_shouldThrowIllegalArgumentException() {
        BaseZipBuilder baseZipBuilder = new BaseZipBuilder();

        assertThrows(IllegalArgumentException.class, () -> baseZipBuilder.setTargetPath(""),
            "should throw IllegalArgumentException when String is empty");

        Path emptyPath = Paths.get("");
        assertThrows(IllegalArgumentException.class, () -> baseZipBuilder.setTargetPath(emptyPath),
            "should throw IllegalArgumentException when Path is empty");
    }

    @Test
    void givenIncorrectPath_testCreateZip_shouldThrowZipBuilderException() {
        BaseZipBuilder baseZipBuilder = new BaseZipBuilder("src/test/resources/output");

        assertThrows(ZipBuilderException.class, baseZipBuilder::createZip);
    }

    @Test
    void givenValidPathAndNoFile_testCreateZip_shouldCreateEmptyZip() throws IOException {
        BaseZipBuilder baseZipBuilder = new BaseZipBuilder(TARGET_PATH);

        baseZipBuilder.createZip();

        assertTrue(ZipHelper.isEmptyZip(TARGET_PATH), "zip output file should be empty");
    }

    @Test
    void givenNonExistentDirAndFilesAndExistentFile_testCreateZip_shouldThrowZipBuilderExceptionAndZipFileNotExist() {
        BaseZipBuilder baseZipBuilder = new BaseZipBuilder(TARGET_PATH);
        List<File> files = Arrays.asList(
            new File("src/test/resources/iDontExist.xml"),
            new File("src/test/resources/nonExistentDir/"),
            new File("src/test/resources/test_resource_1.xml"),
            new File("src/test/resources/whatIsMy.properties")
        );

        baseZipBuilder.addAllToZip(files);

        assertThrows(ZipBuilderException.class, baseZipBuilder::createZip);
        assertFalse(new File(TARGET_PATH).exists(), "zip output file should not exist");
    }

    @Test
    void givenValidSingleFile_testAddToZipAndCreateZip_shouldCreateZipWithTheOneValidFile() {
        BaseZipBuilder zipBuilder = new BaseZipBuilder().setTargetPath(TARGET_PATH);

        File file = new File("src/test/resources/test_resource_1.xml");

        zipBuilder.addToZip(file);

        assertDoesNotThrow(zipBuilder::createZip);
        assertTrue(new File(TARGET_PATH).exists(), "zip output file should exist");
    }

    @Test
    void givenNonExistentFile_testAddToZipAndCreateZip_shouldThrowZipBuilderExceptionAndNotCreateOutputZip() {
        BaseZipBuilder zipBuilder = new BaseZipBuilder().setTargetPath(TARGET_PATH);

        File file = new File("src/test/resources/test_non_existent_resource.xml");

        zipBuilder.addToZip(file);

        assertThrows(ZipBuilderException.class, zipBuilder::createZip);
        assertFalse(new File(TARGET_PATH).exists(), "zip output file should not exist");
    }

    @Test
    void givenInvalidPaths_testAddToZip_shouldThrowZipBuilderException() {
        BaseZipBuilder zipBuilder = new BaseZipBuilder().setTargetPath(TARGET_PATH);

        assertThrows(IllegalArgumentException.class, () -> zipBuilder.addToZip(""),
            "should throw IllegalArgumentException when empty");
        assertThrows(NullPointerException.class, () -> zipBuilder.addToZip((String) null),
            "should throw NullPointerException when null");
        assertThrows(NullPointerException.class, () -> zipBuilder.addToZip((File) null),
            "should throw IllegalArgumentException when empty");
    }
}
