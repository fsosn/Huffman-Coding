package pl.edu.pw.ee;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            SwingUtilities.invokeLater(Main::createAndShowGUI);
        } else if (args.length == 2) {
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
        } else {
            System.out.println("Usage: java -jar .\\target\\HuffmanCoding-1.0-SNAPSHOT.jar <path> <compress|decompress>");
            System.out.println("\tpath:\n\t- if you want to compress, pass a path to a text file");
            System.out.println("\t- if you want to decompress, pass a path to compressed directory");
            System.out.println("\taction:\n\t- compress\n\t- decompress");
        }
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Compression and Decompression App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setResizable(false);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int centerX = (screenSize.width - frame.getWidth()) / 2;
        int centerY = (screenSize.height - frame.getHeight()) / 2;

        frame.setLocation(centerX, centerY);

        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel compressPanel = createCompressPanel();
        JPanel decompressPanel = createDecompressPanel();

        tabbedPane.addTab("Compress", null, compressPanel, "Compress a .txt file");
        tabbedPane.addTab("Decompress", null, decompressPanel, "Decompress a directory");

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    private static JPanel createCompressPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextField filePathField = new JTextField(20);
        JButton browseButton = new JButton("Browse");
        JButton compressButton = new JButton("Compress");

        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
            fileChooser.setFileFilter(filter);
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                filePathField.setText(selectedFile.getAbsolutePath());
            }
        });

        compressButton.addActionListener(e -> {
            String filePath = filePathField.getText();
            try {
                Huffman huffman = new Huffman();
                huffman.huffman(filePath, true);
                showMessage("File compressed successfully", "Success");
            } catch (Exception ex) {
                showMessage("Compression failed: " + ex.getMessage(), "Error");
            }
        });

        JPanel innerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        innerPanel.add(filePathField);
        innerPanel.add(browseButton);
        innerPanel.add(compressButton);
        panel.add(innerPanel, BorderLayout.CENTER);

        return panel;
    }


    private static JPanel createDecompressPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextField directoryPathField = new JTextField(20);
        JButton browseButton = new JButton("Browse");
        JButton decompressButton = new JButton("Decompress");

        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedDirectory = fileChooser.getSelectedFile();
                directoryPathField.setText(selectedDirectory.getAbsolutePath());
            }
        });

        decompressButton.addActionListener(e -> {
            String dirPath = directoryPathField.getText();
            try {
                Huffman huffman = new Huffman();
                huffman.huffman(dirPath, false);
                showMessage("File decompressed successfully", "Success");
            } catch (Exception ex) {
                showMessage("Decompression failed: " + ex.getMessage(), "Error");
            }
        });

        JPanel innerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        innerPanel.add(directoryPathField);
        innerPanel.add(browseButton);
        innerPanel.add(decompressButton);
        panel.add(innerPanel, BorderLayout.CENTER);

        return panel;
    }

    private static void showMessage(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
