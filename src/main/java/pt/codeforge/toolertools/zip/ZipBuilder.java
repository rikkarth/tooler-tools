package pt.codeforge.toolertools.zip;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public interface ZipBuilder {

    void createZip();

    void addToZip(File file);

    void addAllToZip(List<File> files);

    int zipSize();

    ZipBuilder setTargetPath(Path targetPath);

    ZipBuilder setTargetPath(String targetPath);

}
