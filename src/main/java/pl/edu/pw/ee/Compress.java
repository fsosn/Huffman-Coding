package pl.edu.pw.ee;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;

public class Compress {
    private final String filePath;
    private final String compressedContentsDir;

    private static final Logger LOG = Logger.getLogger(Compress.class.getName());

    public Compress(String filePath) {
        this.filePath = filePath;
        this.compressedContentsDir = getCompressedContentsDirName(this.filePath, getDirectoryPath(filePath));
    }


    public int encodeText(Map<Character, String> charCodes) {
        String compressedFilePath = this.compressedContentsDir + FileNames.COMPRESSED_FILE;

        BitSet bitSet = new BitSet();
        int bitIndex = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(this.filePath));
            Path path = Paths.get(compressedFilePath);

            int character;
            while ((character = br.read()) != -1) {
                if (character >= 128) {
                    throw new IllegalArgumentException("Text file contains characters, which are not present in the 128 code-point ASCII table.");
                }

                String code = charCodes.get((char) character);
                char[] codeBits = code.toCharArray();

                for (char codeBit : codeBits) {
                    if (codeBit == '1') {
                        bitSet.set(bitIndex);
                    }
                    bitIndex++;
                }
            }
            bitSet.set(bitIndex);

            Files.write(path, bitSet.toByteArray());
        } catch (FileNotFoundException e) {
            LOG.log(SEVERE, "File could not be found during writing encoded text to output file.", e);
            throw new RuntimeException("Failed to find file during writing encoded text to output file.", e);
        } catch (IOException e) {
            LOG.log(SEVERE, "Exception caught during writing encoded text to output file.", e);
            throw new RuntimeException("Failed to write encoded text to output file.", e);
        }

        return bitSet.toByteArray().length * 8;
    }

    public Map<Character, Integer> charFrequenciesMap() {
        Map<Character, Integer> charFrequencies = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
            int character;
            while ((character = br.read()) != -1) {
                if (character >= 128) {
                    throw new IllegalArgumentException("Text file contains characters, which are not present in the 128 code-point ASCII table.");
                }
                charFrequencies.put((char) character, charFrequencies.getOrDefault((char) character, 0) + 1);
            }
        } catch (FileNotFoundException e) {
            LOG.log(SEVERE, "File could not be found during reading character frequencies from input file.", e);
            throw new RuntimeException("Failed to find file during reading character frequencies from input file.", e);
        } catch (IOException e) {
            LOG.log(SEVERE, "Reading character frequencies from input file failed.", e);
            throw new RuntimeException("Failed to read character frequencies from input file", e);
        }
        if (charFrequencies.isEmpty()) {
            throw new IllegalArgumentException("Input text file is empty, does not contain any characters.");
        }

        return charFrequencies;
    }

    public void writeHeaderInformation(Node node) {
        createCompressDirectory(this.compressedContentsDir);
        StringBuilder header = new StringBuilder();
        postOrderTraversal(node, header);

        BitSet bitSet = new BitSet();
        int bitIndex = 0;

        for (char c : header.toString().toCharArray()) {
            if (c == '1') {
                bitSet.set(bitIndex);
            }
            bitIndex++;
        }
        bitSet.set(bitIndex);

        try {
            Path path = Paths.get(this.compressedContentsDir + FileNames.HEADER_INFORMATION_FILE);
            Files.write(path, bitSet.toByteArray(), StandardOpenOption.CREATE);
        } catch (FileNotFoundException e) {
            LOG.log(SEVERE, "File could not be found during writing header information.", e);
            throw new RuntimeException("Failed to find file during writing header information.", e);
        } catch (IOException e) {
            LOG.log(SEVERE, "Exception caught during writing header information.", e);
            throw new RuntimeException("Failed to write header information.", e);
        }
    }

    private void postOrderTraversal(Node node, StringBuilder header) {
        if (node == null) {
            return;
        }

        postOrderTraversal(node.getLeftChild(), header);
        postOrderTraversal(node.getRightChild(), header);

        if (node.isLeaf()) {
            header.append('1');
            String characterBinary = Integer.toBinaryString(node.getCharacter());
            header.append("0".repeat(Math.max(0, 7 - characterBinary.length())));
            header.append(characterBinary);
        } else {
            header.append('0');
        }
    }

    private String getDirectoryPath(String filePath) {
        return Paths.get(filePath).getParent().toString();
    }

    private String getCompressedContentsDirName(String filePath, String dirPath) {
        return dirPath + File.separator + getFileNameWithoutExt(filePath) + "_compressed";
    }

    private void createCompressDirectory(String compressedContentsDir) {
        try {
            Files.createDirectories(Paths.get(compressedContentsDir));
        } catch (IOException e) {
            LOG.log(SEVERE, "Directory for compressed contents could not be created.", e);
            throw new RuntimeException("Failed to create a directory for compressed contents.", e);
        }
    }

    private String getFileNameWithoutExt(String filePath) {
        Path path = Paths.get(filePath);
        String fileName = path.getFileName().toString();
        int dotIndex = fileName.lastIndexOf(".");
        String result;
        if (dotIndex != -1) {
            result = fileName.substring(0, dotIndex);
        } else {
            result = fileName;
        }
        return result;
    }
}
