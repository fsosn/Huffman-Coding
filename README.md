# Huffman-Coding
## Introduction
This Java project contains implements Huffman coding, which is a lossless data compression algorithm. It can be used to either compress a text file or decompress a directory with compressed contents, that were created during a compression performed earlier.
The project includes a Swing graphical user interface (GUI) and it can also be run from command-line interface (CLI).
## Getting started
1. **Clone the Repository:**
   ```
   git clone https://github.com/fsosn/Huffman-Coding
   ```
2. **Open the project in terminal and then use Maven:**
   ```
   mvn clean install
   ```
3. **Run the project:**
   ```
   java -jar ./target/HuffmanCoding-1.0-SNAPSHOT.jar
   ```
## Usage
### GUI
1. Run the project
2. Compression panel:
    - press **Browse** button to look for a .txt file that you want to compress or enter the file path in text field
    - press **Compress** button to compress chosen text file.
3. Decompression panel:
    - press **Browse** button to look for a directory with compressed contents* that you want to decompress or enter the directory path in text field
    - press **Decompress** button to decompress a file in chosen directory
  
\* - such directory contains compressed.bin and header_information.bin files
### CLI
To run the project you need to pass **two** arguments:
   ```
   java -jar ./target/HuffmanCoding-1.0-SNAPSHOT.jar <path> <action>
   ```
1. **path**:
     - if you want to compress, pass a path to a text file
     - if you want to decompress, pass a path to compressed directory
2. **action**:
     - compress
     - decompress
  
**Examples**

1. Compressing a file whose path is "C:\Users\example.txt"
   ```
   java -jar ./target/HuffmanCoding-1.0-SNAPSHOT.jar C:\Users\example.txt compress
   ```
2. Decompressing a directory with compressed contents
   ```
   java -jar ./target/HuffmanCoding-1.0-SNAPSHOT.jar C:\Users\example_compressed\ decompress
   ```
