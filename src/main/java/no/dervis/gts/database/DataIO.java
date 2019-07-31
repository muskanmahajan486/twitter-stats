package no.dervis.gts.database;

import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Objects;

public class DataIO {

    private static final String dataFolder = "./data";
    private static final String fileName = "twitter.stats";
    private static final String fileExtension = "data";
    private static final String dateTimePattern = "yyyy-MM-dd-HH-mm-AA";

    public static <T> T readFile(Class<? extends T> clazz, String file) {
        T t;

        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream oos = new ObjectInputStream(fis);
            t = clazz.cast(oos.readObject());
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            throw new RuntimeException("Could not read input file.", e);
        }

        return t;
    }

    /**
     * Saves an object to a file named twitter.stats.data.
     *
     * @param object The object to save
     * @param folder The destination folder to save to
     * @param newFile If true, create a new file, otherwise replace existing file.
     * @see DateTimeFormatterBuilder#appendPattern(String)
     */
    public static File saveAsFile(Object object, String folder, boolean newFile) {
        Objects.requireNonNull(object);
        Objects.requireNonNull(folder);

        String filename = String.format("%s/%s.%s", folder, fileName, fileExtension);
        if (newFile) {
            LocalDateTime date = LocalDateTime.now();

            String formattedDate = date.format(DateTimeFormatter.ofPattern(dateTimePattern));
            filename = String.format("%s/%s.%s.%s", folder, fileName, formattedDate, fileExtension);
        }

        File outputFile = new File(Paths.get(filename).normalize().toString());

        if (Objects.nonNull(outputFile.getParent()) && outputFile.getParentFile().mkdirs()) {
            System.out.println("Created data folder: " + outputFile.getParent());
        }

        try {
            FileOutputStream fout = new FileOutputStream(outputFile);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(object);
        } catch (IOException e) {
            throw new RuntimeException("Could not create output file.", e);
        }

        return outputFile;
    }

    public static File saveAsNewFile(Object object) {
        return DataIO.saveAsFile(object, dataFolder, true);
    }

    public static File saveAsFile(Object object) {
        return DataIO.saveAsFile(object, dataFolder, false);
    }

}
