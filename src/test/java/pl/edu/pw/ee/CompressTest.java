package pl.edu.pw.ee;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompressTest {
    private Huffman huffman;
    private static final String PATH_TO_ROOT_DIR = "src/main/resources";
    private static final String INPUT_FILE = "input_file.txt";
    private static final String INPUT_FILE_PATH = PATH_TO_ROOT_DIR + File.separator + INPUT_FILE;
    private static final String COMPRESSED_DIR_PATH = PATH_TO_ROOT_DIR + "/input_file_compressed";
    private static final Logger LOG = Logger.getLogger(CompressTest.class.getName());

    @Test
    public void charFrequencyMapSizeShouldBeEqualToNumOfDiffCharsInTextFile() {
        //given
        Compress read = new Compress(INPUT_FILE_PATH);
        int actualMapSize;
        int expectedMapSize = countNumOfDifferentCharsInInputFile();

        //when
        actualMapSize = read.charFrequenciesMap().size();

        //then
        assertEquals(expectedMapSize, actualMapSize);
    }

    @Test
    public void charFrequencyValuesInMapShouldBeCorrect() {
        //given
        Compress compress = new Compress(INPUT_FILE_PATH);
        char firstChar = 'a';
        char secondChar = ' ';
        char thirdChar = '\n';
        List<Integer> list = countFrequenciesOfThreeCharsInInputFile(firstChar, secondChar, thirdChar);
        Map<Character, Integer> charFrequencies;

        //when
        charFrequencies = compress.charFrequenciesMap();
        int retrievedFrequencyForA = charFrequencies.get(firstChar);
        int retrievedFrequencyForSpace = charFrequencies.get(secondChar);
        int retrievedFrequencyForNewline = charFrequencies.get(thirdChar);

        //then
        assertEquals(list.get(0), retrievedFrequencyForA);
        assertEquals(list.get(1), retrievedFrequencyForSpace);
        assertEquals(list.get(2), retrievedFrequencyForNewline);
    }

    @Test
    public void shouldCreateCorrectDirsAndFiles() {
        //given
        huffman = new Huffman();

        //when
        huffman.huffman(INPUT_FILE_PATH, true);
        FileNames.checkPathToDirectory(COMPRESSED_DIR_PATH);
        FileNames.checkPathToFile(COMPRESSED_DIR_PATH + FileNames.COMPRESSED_FILE);
        FileNames.checkPathToFile(COMPRESSED_DIR_PATH + FileNames.HEADER_INFORMATION_FILE);

        //then
        assert true;
    }

    @Test
    public void shouldReturnCorrectNumOfBitsWhenCompress() {
        //given
        huffman = new Huffman();
        long expectedNumberOfBits = fileSizeInBits();

        //when
        int actualNumberOfBits = huffman.huffman(INPUT_FILE_PATH, true);

        //then
        assertEquals((int) expectedNumberOfBits, actualNumberOfBits);
    }

    private int countNumOfDifferentCharsInInputFile() {
        List<Integer> charList = new ArrayList<>();
        int character;
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_PATH))) {
            while ((character = br.read()) != -1) {
                if (!charList.contains(character)) {
                    charList.add(character);
                }
            }
        } catch (IOException e) {
            LOG.log(SEVERE, "Exception caught while counting chars in input file.", e);
        }

        return charList.size();
    }

    private List<Integer> countFrequenciesOfThreeCharsInInputFile(char firstChar, char secondChar, char thirdChar) {
        List<Integer> frequencies = new ArrayList<>(3);
        frequencies.add(0);
        frequencies.add(0);
        frequencies.add(0);

        int character;
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_PATH))) {
            while ((character = br.read()) != -1) {
                if (character == firstChar) {
                    frequencies.set(0, frequencies.get(0) + 1);
                } else if (character == secondChar) {
                    frequencies.set(1, frequencies.get(1) + 1);
                } else if (character == thirdChar) {
                    frequencies.set(2, frequencies.get(2) + 1);
                }
            }
        } catch (IOException e) {
            LOG.log(SEVERE, "Exception caught while counting chars in input file.", e);
        }

        return frequencies;
    }

    private long fileSizeInBits() {
        Path filePath = Paths.get(COMPRESSED_DIR_PATH + FileNames.COMPRESSED_FILE);

        long numOfBytes = 0;
        try {
            numOfBytes = Files.size(filePath);
        } catch (IOException e) {
            LOG.log(SEVERE, "Exception caught while getting file size (in bits) from compressed file.", e);
        }

        return numOfBytes * 8;
    }
}
