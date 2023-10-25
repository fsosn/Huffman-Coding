package pl.edu.pw.ee;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NodeTest {
    @Test
    public void valuesShouldBeAssignedCorrectlyWhenConstructingChildNode() {
        //given
        Node node;
        char givenCharacter = 'a';
        int givenFrequency = 1;

        //when
        node = new Node(givenCharacter, givenFrequency);
        char retrievedCharacter = node.getCharacter();
        int retrievedFrequency = node.getFrequency();
        Node leftChild = node.getLeftChild();
        Node rightChild = node.getRightChild();
        boolean isLeaf = node.isLeaf();

        //then
        assertEquals(givenCharacter, retrievedCharacter);
        assertEquals(givenFrequency, retrievedFrequency);
        assertNull(leftChild);
        assertNull(rightChild);
        assertTrue(isLeaf);
    }

    @Test
    public void valuesShouldBeAssignedCorrectlyWhenConstructingParentNode() {
        //given
        Node parent;
        Node leftChild;
        Node rightChild;

        char givenCharacterLeftChild = 'a';
        char givenCharacterRightChild = 'b';
        char expectedCharacterParent = '\0';

        int givenFrequencyLeftChild = 1;
        int givenFrequencyRightChild = 2;
        int expectedFrequencyParent = givenFrequencyLeftChild + givenFrequencyRightChild;

        //when
        leftChild = new Node(givenCharacterLeftChild, givenFrequencyLeftChild);
        rightChild = new Node(givenCharacterRightChild, givenFrequencyRightChild);
        parent = new Node(leftChild, rightChild);

        char retrievedCharacter = parent.getCharacter();
        int retrievedFrequency = parent.getFrequency();
        boolean isLeaf = parent.isLeaf();

        Node retrievedLeftChild = parent.getLeftChild();
        Node retrievedRightChild = parent.getRightChild();

        char retrievedCharacterLeftChild = parent.getLeftChild().getCharacter();
        char retrievedCharacterRightChild = parent.getRightChild().getCharacter();

        int retrievedFrequencyLeftChild = parent.getLeftChild().getFrequency();
        int retrievedFrequencyRightChild = parent.getRightChild().getFrequency();

        //then
        assertEquals(expectedCharacterParent, retrievedCharacter);
        assertEquals(expectedFrequencyParent, retrievedFrequency);
        assertFalse(isLeaf);
        assertEquals(leftChild, retrievedLeftChild);
        assertEquals(rightChild, retrievedRightChild);
        assertEquals(givenCharacterLeftChild, retrievedCharacterLeftChild);
        assertEquals(givenCharacterRightChild, retrievedCharacterRightChild);
        assertEquals(givenFrequencyLeftChild, retrievedFrequencyLeftChild);
        assertEquals(givenFrequencyRightChild, retrievedFrequencyRightChild);
    }

    @Test
    public void valuesShouldBeAssignedCorrectlyWhenMakingParentNodesChildrenOfAnotherNode() {
        //given
        Node parent;
        Node leftChild;
        Node rightChild;
        int[] freq = {1, 2, 3, 4};
        int expectedFrequency = freq[0] + freq[1] + freq[2] + freq[3];

        //when
        leftChild = new Node(new Node('a', freq[0]), new Node('b', freq[1]));
        rightChild = new Node(new Node('c', freq[2]), new Node('d', freq[3]));
        parent = new Node(leftChild, rightChild);
        char retrievedCharacter = parent.getCharacter();
        int retrievedFrequency = parent.getFrequency();
        Node retrievedLeftChild = parent.getLeftChild();
        Node retrievedRightChild = parent.getRightChild();

        //then
        assertEquals('\0', retrievedCharacter);
        assertEquals(expectedFrequency, retrievedFrequency);
        assertFalse(parent.isLeaf());
        assertEquals(leftChild, retrievedLeftChild);
        assertEquals(rightChild, retrievedRightChild);
    }
}
