package pt.codeforge.toolertools.zip;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

class ZipHelper {

    private ZipHelper() {
        throw new AssertionError(ZipHelper.class + " should not be instantiated.");
    }

    protected static String treatDirName(String dirName) {
        if (!dirName.isEmpty() && !dirName.endsWith("/")) {
            return dirName + "/";
        }

        return dirName;
    }

    protected static List<File> getListFiles(File dir) {
        try {
            return Arrays.asList(Objects.requireNonNull(dir.listFiles()));
        } catch (NullPointerException npe) {
            return Collections.emptyList();
        }
    }

    protected static String getEntryName(File file, String parentDirName) {
        String fileName = file.getName();

        if (file.isDirectory()) {
            String dirName = ZipHelper.treatDirName(fileName);
            return getEntryName(parentDirName, dirName);
        }

        return getEntryName(parentDirName, fileName);
    }

    protected static String getEntryName(String parentDirName, String dirName) {
        return parentDirName.isEmpty() ? dirName : parentDirName + dirName;
    }

    protected static boolean isEmptyZip(String path) throws IOException {
        if (path.isEmpty()) {
            throw new IllegalArgumentException("provided argument is empty or null");
        }

        if (!path.endsWith(".zip")) {
            throw new IllegalArgumentException("file is not .zip");
        }

        try (ZipFile zipFile = new ZipFile(new File(path))) {
            return zipFile.size() == 0;
        } catch (ZipException ze) {
            if (zipExceptionMsgContainsEmpty(ze)) {
                return true;
            }

            throw ze;
        }
    }

    protected static int getLength(String path) throws IOException {
        if (path.isEmpty()) {
            throw new IllegalArgumentException("provided argument is empty or null");
        }

        if (!path.endsWith(".zip")) {
            throw new IllegalArgumentException("file is not .zip");
        }

        try (ZipFile zipFile = new ZipFile(new File(path))) {
            return zipFile.size();
        } catch (ZipException ze) {
            if (zipExceptionMsgContainsEmpty(ze)) {
                return 0;
            }

            throw ze;
        }
    }

    protected static int getLength(File file) throws IOException {
        try (ZipFile zipFile = new ZipFile(file)) {
            return zipFile.size();
        } catch (ZipException ze) {
            if (zipExceptionMsgContainsEmpty(ze)) {
                return 0;
            }

            throw ze;
        }
    }

    private static boolean zipExceptionMsgContainsEmpty(ZipException ze) {
        return ze.getMessage().contains("empty");
    }
}
