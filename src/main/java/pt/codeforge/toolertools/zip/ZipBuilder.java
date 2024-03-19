package pt.codeforge.toolertools.zip;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

/**
 * This interface defines the contract for a ZipBuilder. A ZipBuilder is responsible for creating a zip file from a list
 * of files and directories.
 */
public interface ZipBuilder {

    /**
     * Creates the zip file at the target path with the files added to the builder.
     */
    void createZip();

    /**
     * Adds a file to the list of files to be added to the zip. If the File object represents a directory, it will be
     * processed as a directory, and all files within that directory will be added to the zip file recursively.
     *
     * @param file File or directory to be added to the zip
     */
    void addToZip(File file);

    /**
     * Adds a file to the list of files to be added to the zip. If the File object represents a directory, it will be
     * processed as a directory, and all files within that directory will be added to the zip file recursively.
     *
     * @param filePath Path of the file or directory to be added to the zip
     */
    void addToZip(String filePath);

    /**
     * Adds a list of files to the list of files to be added to the zip. If the File object represents a directory, it
     * will be processed as a directory, and all files within that directory will be added to the zip file recursively.
     *
     * @param files List of files or directories to be added to the zip
     */
    void addAllToZip(List<File> files);

    /**
     * Returns the number of files to be added to the zip.
     *
     * @return Number of files to be added to the zip
     */
    int zipSize();

    /**
     * Sets the target path where the zip file will be created.
     *
     * @param targetPath Path where the zip file will be created
     * @return this
     */
    ZipBuilder setTargetPath(Path targetPath);

    /**
     * Sets the target path where the zip file will be created.
     *
     * @param targetPath String representing the path where the zip file will be created
     * @return this
     */
    ZipBuilder setTargetPath(String targetPath);

}
