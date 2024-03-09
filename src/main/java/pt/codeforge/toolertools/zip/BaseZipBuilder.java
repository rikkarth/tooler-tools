package pt.codeforge.toolertools.zip;

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

public class BaseZipBuilder implements ZipBuilder {

    private final List<File> files = new ArrayList<>();
    private Path targetPath;

    public BaseZipBuilder() {
    }

    public BaseZipBuilder(Path targetPath) {
        this.targetPath = targetPath;
    }

    public BaseZipBuilder(String targetPath) {
        this.targetPath = Paths.get(targetPath);
    }

    @Override
    public void createZip() {
        if (this.targetPath == null) {
            throw new IllegalStateException("Target path is not defined.");
        }

        try (FileOutputStream fos = new FileOutputStream(this.targetPath.toString());
            ZipOutputStream zos = new ZipOutputStream(fos, StandardCharsets.UTF_8)) {

            this.files.forEach(file -> insertInZip(file, zos));
        } catch (IOException ioe) {
            throw new ZipBuilderException("Unable to create zip.", ioe);
        }
    }

    @Override
    public void addToZip(File file) {
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null.");
        }

        this.files.add(file);
    }

    @Override
    public void addAllToZip(List<File> files) {
        if (files == null) {
            throw new IllegalArgumentException("File's list cannot be null.");
        }

        List<File> filteredFiles = files.stream().filter(Objects::nonNull).collect(Collectors.toList());

        this.files.addAll(filteredFiles);
    }

    @Override
    public BaseZipBuilder setTargetPath(Path targetPath) {
        this.targetPath = targetPath;
        return this;
    }

    @Override
    public BaseZipBuilder setTargetPath(String targetPath) {
        this.targetPath = Paths.get(targetPath);
        return this;
    }

    @Override
    public int zipSize() {
        return this.files.size();
    }

    private void insertInZip(File file, ZipOutputStream zos) {
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
            throw new ZipBuilderException("unable to add to zip", ioe);
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
