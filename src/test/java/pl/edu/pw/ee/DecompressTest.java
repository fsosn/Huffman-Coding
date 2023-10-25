package pl.edu.pw.ee;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DecompressTest {
    private Huffman huffman;
    private static final String PATH_TO_ROOT_DIR = "src/main/resources";
    private static final Logger LOG = Logger.getLogger(DecompressTest.class.getName());

    private static final String COMPRESSED_DIR_PATH = PATH_TO_ROOT_DIR + "/input_file_compressed/";
    private static final String INPUT_FILE = "input_file.txt";
    private static final String INPUT_FILE_PATH = PATH_TO_ROOT_DIR + File.separator + INPUT_FILE;

    @Test
    public void shouldReturnCorrectNumOfCharsWhenDecompress() {
        //given
        huffman = new Huffman();
        int expectedNumOfChars = countAllCharsInInputFile();

        //when
        int actualNumOfChars = huffman.huffman(COMPRESSED_DIR_PATH, false);

        //then
        assertEquals(expectedNumOfChars, actualNumOfChars);
    }

    private int countAllCharsInInputFile() {
        int numOfChars = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_PATH))) {
            while (br.read() != -1) {
                numOfChars++;
            }
        } catch (IOException e) {
            LOG.log(SEVERE, "Exception caught while counting chars in input file.", e);
        }

        return numOfChars;
    }
}
