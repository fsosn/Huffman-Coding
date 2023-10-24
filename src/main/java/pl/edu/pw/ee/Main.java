package pl.edu.pw.ee;

public class Main {
    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("Usage: java Main <inputPath> <compress|decompress>");
            return;
        }
        Huffman huffman = new Huffman();

        String path = args[0];
        String action = args[1];

        if ("compress".equals(action)) {
            huffman.huffman(path, true);
        } else if ("decompress".equals(action)) {
            huffman.huffman(path, false);
        } else {
            System.out.println("Invalid action. Use 'compress' or 'decompress'.");
        }
    }
}
