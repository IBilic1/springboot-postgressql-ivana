package hr.algebra.ivanabilic.iisproject.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHendler {

    public static void createFile(String url) throws IOException {
        Path pathToFile = Paths.get(url);

        Files.createDirectories(pathToFile.getParent());
        if (!Files.exists(pathToFile)) {
            Files.createFile(pathToFile);
        }
    }

    public static void writeInFile(String url, byte[] data) throws IOException {
        try (FileOutputStream myWriter = new FileOutputStream(url)) {
            myWriter.write(data);
        }
    }

    public static String parseExtension(String extension) throws IOException {
        return extension.substring(extension.lastIndexOf("/") + 1);
    }
}
