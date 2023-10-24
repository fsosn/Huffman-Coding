package pl.edu.pw.ee;

public class Node implements Comparable<Node> {

    private final char character;
    private final int frequency;
    private final Node leftChild;
    private final Node rightChild;

    public Node(Node leftChild, Node rightChild) {
        this.character = '\0';
        this.frequency = leftChild.frequency + rightChild.frequency;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public Node(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
        this.leftChild = null;
        this.rightChild = null;
    }

    public char getCharacter() {
        return this.character;
    }

    public int getFrequency() {
        return this.frequency;
    }

    public Node getLeftChild() {
        return this.leftChild;
    }

    public Node getRightChild() {
        return this.rightChild;
    }

    public boolean isLeaf() {
        return this.leftChild == null && this.rightChild == null;
    }

    @Override
    public int compareTo(Node node) {
        int frequencyComparison = this.frequency - node.frequency;

        if (frequencyComparison != 0) {
            return frequencyComparison;
        } else {
            return Integer.compare(this.character, node.character);
        }
    }

}