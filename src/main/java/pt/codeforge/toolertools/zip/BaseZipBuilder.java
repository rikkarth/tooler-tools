package pt.codeforge.toolertools.zip;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
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

           /* if(this.files.get(0).isDirectory()){
                String name = this.files.get(0).getAbsolutePath() + File.separator + "output\\test2.properties";
                System.out.println(name);
                insertInZip(new File(name), zos);
            }*/

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
        try {
            ZipEntry zipEntry = new ZipEntry(file.getName());
            zos.putNextEntry(zipEntry);
            Files.copy(file.getAbsoluteFile().toPath(), zos);
            zos.closeEntry();
        } catch (IOException ioe) {
            throw new ZipBuilderException("unable to add to zip", ioe);
        }
    }
}
