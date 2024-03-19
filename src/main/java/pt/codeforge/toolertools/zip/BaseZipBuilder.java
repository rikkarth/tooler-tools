package pt.codeforge.toolertools.zip;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Base implementation of a ZipBuilder.
 */
public class BaseZipBuilder implements ZipBuilder {

    private final List<File> files = new ArrayList<>();
    private static final String DEFINED_TARGET_PATH_ISNULL = "Target path defined is null.";
    private static final String UNDEFINED_TARGET_PATH = "Target path is not defined (null).";
    private static final String EMPTY_TARGET_PATH = "Target path is empty.";
    private Path targetPath;

    public BaseZipBuilder() {
    }

    public BaseZipBuilder(Path targetPath) {
        Objects.requireNonNull(targetPath, DEFINED_TARGET_PATH_ISNULL);

        if (targetPath.toString().isEmpty()) {
            throw new IllegalArgumentException(EMPTY_TARGET_PATH);
        }

        this.targetPath = targetPath;
    }

    public BaseZipBuilder(String targetPath) {
        Objects.requireNonNull(targetPath, DEFINED_TARGET_PATH_ISNULL);

        if (targetPath.isEmpty()) {
            throw new IllegalArgumentException(EMPTY_TARGET_PATH);
        }

        this.targetPath = Paths.get(targetPath);
    }

    @Override
    public void createZip() {
        checkIfTargetPathIsDefined();

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ZipOutputStream zos = new ZipOutputStream(baos, StandardCharsets.UTF_8)) {

            this.files.forEach(file -> insertInZip(file, zos));
            zos.finish();

            writeZipFile(baos);

        } catch (IOException | ZipBuilderException e) {
            throw new ZipBuilderException("Unable to create zip.", e);
        }
    }

    @Override
    public void addToZip(File file) {
        Objects.requireNonNull(file, "File cannot be null.");

        this.files.add(file);
    }

    @Override
    public void addToZip(String filePath) {
        Objects.requireNonNull(filePath, "File path cannot be null.");

        if (filePath.isEmpty()) {
            throw new IllegalArgumentException("File path cannot be empty.");
        }

        File file = new File(filePath);

        this.files.add(file);
    }

    @Override
    public void addAllToZip(List<File> files) {
        Objects.requireNonNull(files, "File's list cannot be null.");

        List<File> filteredFiles = files.stream().filter(Objects::nonNull).collect(Collectors.toList());

        this.files.addAll(filteredFiles);
    }

    @Override
    public BaseZipBuilder setTargetPath(Path targetPath) {
        Objects.requireNonNull(targetPath, DEFINED_TARGET_PATH_ISNULL);

        if (targetPath.toString().isEmpty()) {
            throw new IllegalArgumentException(EMPTY_TARGET_PATH);
        }

        this.targetPath = targetPath;
        return this;
    }

    @Override
    public BaseZipBuilder setTargetPath(String targetPath) {
        Objects.requireNonNull(targetPath, DEFINED_TARGET_PATH_ISNULL);

        if (targetPath.isEmpty()) {
            throw new IllegalArgumentException(EMPTY_TARGET_PATH);
        }

        this.targetPath = Paths.get(targetPath);
        return this;
    }

    @Override
    public int zipSize() {
        return this.files.size();
    }

    private void checkIfTargetPathIsDefined() {
        try {
            Objects.requireNonNull(this.targetPath, UNDEFINED_TARGET_PATH);
        } catch (NullPointerException npe) {
            throw new IllegalStateException(npe.getMessage());
        }
    }

    private void writeZipFile(ByteArrayOutputStream baos) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(this.targetPath.toString())) {
            baos.writeTo(fos);
        }
    }

    private void insertInZip(File file, ZipOutputStream zos) {
        if (!file.exists()) {
            throw new ZipBuilderException(String.format("file does not exist: %s", file.getAbsolutePath()));
        }

        insertInZip(file, zos, "");
    }

    private void insertInZip(File file, ZipOutputStream zos, String dirName) {
        try {
            if (file.isDirectory()) {
                insertDirectory(file, zos, dirName);
            } else {
                insertFile(file, zos, dirName);
            }
        } catch (IOException ioe) {
            throw new ZipBuilderException("Failed to insert file/directory into zip: " + file.getAbsolutePath(), ioe);
        }
    }

    private void insertFile(File file, ZipOutputStream zos, String parentDirName) throws IOException {
        String entryName = ZipHelper.getEntryName(file, parentDirName);

        ZipEntry zipEntry = new ZipEntry(entryName);
        zos.putNextEntry(zipEntry);
        Files.copy(file.toPath(), zos);
        zos.closeEntry();
    }

    private void insertDirectory(File dir, ZipOutputStream zos, String parentDirName) throws IOException {
        String entryName = ZipHelper.getEntryName(dir, parentDirName);
        ZipEntry zipEntry = new ZipEntry(entryName);

        zos.putNextEntry(zipEntry);

        ZipHelper.getListFiles(dir).forEach(file -> insertInZip(file, zos, entryName));

        zos.closeEntry();
    }
}
