package com.example.fileop;

import org.junit.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class FileReverserTest {
    @Test
    public void testReverse() {
        FileReverser fileReverse = new FileReverser();
        assertEquals("olleH", fileReverse.reverse("Hello"));
        assertEquals("dlroW", fileReverse.reverse("World"));
    }

    @Test
    //unit test without hitting file system
    public void testReverseFileContentsWithoutHittingFS() throws IOException {
        String input = "Hello\nWorld\n";
        // Expected output
        String expectedOutput = "olleH\ndlroW\n";

        // Set up input and output streams
        StringReader reader = new StringReader(input);
        StringWriter writer = new StringWriter();

        // Call the method under test
        FileReverser reverser = new FileReverser();
        reverser.reverseFileContents(reader, writer);

        // Check the output
        assertEquals(expectedOutput, writer.toString());
    }

    @Test
    //unit test with file system
    public void testReverseFileContentsWithActualFile() throws IOException {
        String INPUT_FILE = "input.txt";
        String OUTPUT_FILE = "output.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INPUT_FILE))) {
            writer.write("Hello\n");
            writer.write("World\n");
        }

        FileReverser fileReverser = new FileReverser();
        fileReverser.reverseFileContents(new FileReader(INPUT_FILE), new FileWriter(OUTPUT_FILE));
        try (BufferedReader reader = new BufferedReader(new FileReader(OUTPUT_FILE))) {
            assertEquals("olleH", reader.readLine());
            assertEquals("dlroW", reader.readLine());
            assertTrue(reader.readLine() == null); // Ensure no more lines exist
        }

        new java.io.File(INPUT_FILE).delete();
        new java.io.File(OUTPUT_FILE).delete();
    }
}