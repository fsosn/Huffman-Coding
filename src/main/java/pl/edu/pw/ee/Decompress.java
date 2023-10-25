package pl.edu.pw.ee;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.BitSet;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;

public class Decompress {

    private final String pathToDir;

    private static final Logger LOG = Logger.getLogger(Decompress.class.getName());

    public Decompress(String pathToRootDir) {
        this.pathToDir = pathToRootDir;
    }

    public String readHeaderInformation() {
        String headerFilePath = this.pathToDir + FileNames.HEADER_INFORMATION_FILE;
        FileNames.checkPathToFile(headerFilePath);

        DataInputStream dataInputStream;
        StringBuilder decodedHeaderBinary = new StringBuilder();

        try {
            dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(headerFilePath)));
            byte[] byteArray = new byte[dataInputStream.available()];
            if (dataInputStream.read(byteArray) == -1) {
                LOG.log(SEVERE, "No byte was available, because the stream was at the end of the file.");
                throw new RuntimeException("Failed to read header information, because there was no available bytes.");
            }
            BitSet bitSet = BitSet.valueOf(byteArray);

            for (int i = 0; i < bitSet.length() - 1; i++) {
                if (bitSet.get(i)) {
                    decodedHeaderBinary.append('1');
                } else {
                    decodedHeaderBinary.append('0');
                }
            }
        } catch (FileNotFoundException e) {
            LOG.log(SEVERE, "Could not find file during reading header information.", e);
            throw new RuntimeException("Failed to find file during reading header information.", e);
        } catch (IOException e) {
            LOG.log(SEVERE, "Exception caught during reading header information.", e);
            throw new RuntimeException("Failed to read header information.", e);
        }

        char[] bits = decodedHeaderBinary.toString().toCharArray();
        StringBuilder header = new StringBuilder();

        for (int i = 0; i < bits.length; i++) {
            if (bits[i] == '1') {
                header.append('1');
                if (i <= bits.length - 8) {
                    StringBuilder characterBinary = new StringBuilder();
                    for (int j = i + 1; j < i + 8; j++) {
                        characterBinary.append(bits[j]);
                    }
                    char binaryToCharacter = (char) Integer.parseInt(characterBinary.toString(), 2);

                    header.append(binaryToCharacter);
                    i += characterBinary.length();
                }
            } else {
                header.append('0');
            }
        }

        return header.toString();
    }

    public int decodeText(Node root) {
        int numOfCharacters = 0;
        Node current = root;
        BitSet bitSet = getBitSetFromCompressed();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.pathToDir + FileNames.DECOMPRESSED_FILE))) {
            for (int i = 0; i < bitSet.length() - 1; i++) {
                if (bitSet.get(i)) {
                    if (current == null) {
                        throw new NullPointerException("Header or compressed file might have been changed or encoded incorrectly.");
                    }
                    current = current.getRightChild();

                    if (current.isLeaf() && current.getCharacter() != '\0') {
                        writer.write(current.getCharacter());
                        numOfCharacters++;
                        current = root;
                    }
                } else {
                    if (current == null) {
                        throw new NullPointerException("Header or compressed file might have been changed or encoded incorrectly.");
                    }
                    current = current.getLeftChild();

                    if (current.isLeaf() && current.getCharacter() != '\0') {
                        writer.write(current.getCharacter());
                        numOfCharacters++;
                        current = root;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            LOG.log(SEVERE, "File could not be found during decoding compressed text.", e);
            throw new RuntimeException("Failed to find file during decoding compressed text.");
        } catch (IOException e) {
            LOG.log(SEVERE, "Exception caught during decoding compressed text.", e);
            throw new RuntimeException("Failed during writing decoded compressed text.", e);
        }

        return numOfCharacters;
    }
    private BitSet getBitSetFromCompressed() {
        String filePath = this.pathToDir + FileNames.COMPRESSED_FILE;
        FileNames.checkPathToFile(filePath);

        BitSet bitSet;
        try {
            byte[] byteArray = Files.readAllBytes(Paths.get(filePath));
            bitSet = BitSet.valueOf(byteArray);
        } catch (FileNotFoundException e) {
            LOG.log(SEVERE, "File could not be found during reading bit set from compressed file.", e);
            throw new RuntimeException("Failed to find file during reading bit set from compressed file.", e);
        } catch (IOException e) {
            LOG.log(SEVERE, "Reading bit set from compressed file failed.", e);
            throw new RuntimeException("Failed to read bit set from compressed file.", e);
        }
        return bitSet;
    }
}
