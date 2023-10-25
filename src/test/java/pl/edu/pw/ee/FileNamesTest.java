package pl.edu.pw.ee;

import org.junit.Test;

public class FileNamesTest {
    private static final String INPUT_FILE = "input_file.txt";

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenPathToFileIsNull() {
        //given
        String invalidPathToFile = null;

        //when
        FileNames.checkPathToFile(invalidPathToFile);

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenPathToFileIsEmpty() {
        //given
        String invalidPathToFile = "";

        //when
        FileNames.checkPathToFile(invalidPathToFile);

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenPathToFileDoesNotExist() {
        //given
        String invalidPathToFile = "src/main/resources/doesnotexist.txt";

        //when
        FileNames.checkPathToFile(invalidPathToFile);

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenPathToDirIsNull() {
        //given
        String invalidPathString = null;

        //when
        FileNames.checkPathToDirectory(invalidPathString);

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenPathToDirIsEmpty() {
        //given
        String invalidPathString = "";

        //when
        FileNames.checkPathToDirectory(invalidPathString);

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenPathToDirDoesNotExist() {
        //given
        String invalidPathString = "src/path/does/not/exist/";

        //when
        FileNames.checkPathToDirectory(invalidPathString);

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenPathToDirIsNotDir() {
        //given
        String invalidPathString = "src/main/resources" + INPUT_FILE;

        //when
        FileNames.checkPathToDirectory(invalidPathString);

        //then
        assert false;
    }
}