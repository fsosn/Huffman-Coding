package pl.edu.pw.ee;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileNames {
    public static final String HEADER_INFORMATION_FILE = File.separator + "header_information.bin";
    public static final String COMPRESSED_FILE = File.separator + "compressed.bin";
    public static final String DECOMPRESSED_FILE = File.separator + "decompressed.txt";

    public static void checkPathToFile(String filePath) {
        if (filePath == null) {
            throw new IllegalArgumentException("Given file path string cannot be null.");
        }
        if (filePath.isEmpty()) {
            throw new IllegalArgumentException("Given file path string cannot be empty.");
        }

        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("File " + filePath + " does not exists.");
        }
        if (!Files.isReadable(path)) {
            throw new IllegalArgumentException("File " + filePath + " cannot be read.");
        }
    }

    public static void checkPathToDirectory(String pathToRootDir) {
        if (pathToRootDir == null) {
            throw new IllegalArgumentException("Given path string cannot be null.");
        }
        if (pathToRootDir.isEmpty()) {
            throw new IllegalArgumentException("Given path string cannot be empty.");
        }

        Path path = Paths.get(pathToRootDir);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("Filepath " + pathToRootDir + " does not exists.");
        }
        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("Filepath " + pathToRootDir + " is not a directory.");
        }
    }
}
