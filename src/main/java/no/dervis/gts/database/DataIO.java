package no.dervis.gts.database;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Objects;

public class DataIO {

    public static final String dataFolder = "./data";
    public static final String defaultFilename = "twitter.stats.data";
    private static final String fileName = "twitter.stats";
    private static final String fileExtension = "data";
    public static final String dateTimePattern = "yyyy-MM-dd-HH-mm-A";
    private static final String separator = File.pathSeparator;

    public static boolean defaultDbFileExists() {
        return Files.isReadable(Paths.get(DataIO.dataFolder, DataIO.defaultFilename).toAbsolutePath());
    }

    /**
     * Reads in an object from a file.
     *
     * @param clazz The type of object that was saved.
     * @param filePath The location of the file.
     * @param <T> Type of object returned from this method.
     * @return The object that was saved.
     */
    public static <T> T readFile(Class<? extends T> clazz, String filePath) {
        T t;

        try {
            FileInputStream input = new FileInputStream(filePath);
            ObjectInputStream oos = new ObjectInputStream(input);
            t = clazz.cast(oos.readObject());
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            throw new RuntimeException("Could not read input file.", e);
        }

        return t;
    }

    /**
     * Saves an object to a file named <code>twitter.stats-YEAR-MONTH-DAY-TIME.data</code>
     * in a directory given by the <code>folder</code> argument.
     *
     * @param object The object to save
     * @param folder The destination folder to save to
     * @see DateTimeFormatterBuilder#appendPattern(String)
     */
    public static File saveAsFile(Object object, String folder) {
        Objects.requireNonNull(object);
        Objects.requireNonNull(folder);

        LocalDateTime date = LocalDateTime.now();

        String filename = String.format("%s%s%s.%s", folder, separator, fileName, fileExtension);

        File outputFile = new File(Paths.get(filename).toAbsolutePath().toString());

        if (Objects.nonNull(outputFile.getParent()) && outputFile.getParentFile().mkdirs()) {
            System.out.println("Created data folder: " + outputFile.getParent());
        }

        try {
            FileOutputStream output = new FileOutputStream(outputFile);
            ObjectOutputStream oos = new ObjectOutputStream(output);
            oos.writeObject(object);
        } catch (IOException e) {
            throw new RuntimeException("Could not create output file.", e);
        }

        return outputFile;
    }

    /**
     * Saves an object to a file named <code>twitter.stats.data</code>.
     * If the file exists, it will be overwritten.
     *
     * @param object The object to save.
     * @return The File object to the save file.
     */
    public static File saveAsFile(Object object) {
        return DataIO.saveAsFile(object, dataFolder);
    }

}
