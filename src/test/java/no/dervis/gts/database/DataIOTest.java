package no.dervis.gts.database;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class DataIOTest implements Serializable {
    private static final long serialVersionUID = -641731695413538871L;

    @Test
    void saveAsFileAndReadObject() throws IOException {
        final Path tempDirectory = Files.createTempDirectory("twitter-temp");

        final String data = "twitter stats data test";

        assertDoesNotThrow(() -> {
            final File savedFile = DataIO.saveAsFile(new TestClass(data), tempDirectory.toString(), false);

            assertTrue(Files.isReadable(Paths.get(savedFile.getAbsolutePath())));

            final TestClass readFile = DataIO.readFile(TestClass.class, savedFile.getAbsolutePath());

            assertNotNull(readFile);
            assertEquals(data, readFile.text);

            assertTrue(savedFile.delete(), "Cant delete temp file.");
            assertTrue(Files.deleteIfExists(tempDirectory), "Cant delete temp folder.");
        });
    }

    class TestClass implements Serializable {
        private static final long serialVersionUID = 6806605198088289511L;
        private String text;

        TestClass(String text) {
            this.text = text;
        }
    }
}