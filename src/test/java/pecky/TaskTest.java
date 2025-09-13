package pecky;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


public class TaskTest {

    @Test
    public void convertStringToDate_validInput1() {
        LocalDateTime expectedOutput = LocalDateTime.of(2003, 8, 3, 0, 0, 0);
        assertEquals(expectedOutput, Task.convertStringToDate("03-08-2003"));
    }

    @Test
    public void convertStringToDate_validInput2() {
        LocalDateTime expectedOutput = LocalDateTime.of(2003, 8, 3, 0, 0, 0);
        assertEquals(expectedOutput, Task.convertStringToDate("2003-08-03"));
    }

    @Test
    public void convertStringToDate_validInput3() {
        LocalDateTime expectedOutput = LocalDateTime.of(2003, 8, 3, 0, 0, 0);
        assertEquals(expectedOutput, Task.convertStringToDate("03/08/2003"));
    }

    @Test
    public void convertStringToDate_validInput4() {
        LocalDateTime expectedOutput = LocalDateTime.of(2003, 8, 3, 0, 0, 0);
        assertEquals(expectedOutput, Task.convertStringToDate("2003/08/03"));
    }

    @Test
    public void convertStringToDate_validInput5() {
        LocalDateTime expectedOutput = LocalDateTime.of(2003, 8, 3, 12, 34, 0);
        assertEquals(expectedOutput, Task.convertStringToDate("03-08-2003 1234"));
    }

    @Test
    public void convertStringToDate_validInput6() {
        LocalDateTime expectedOutput = LocalDateTime.of(2003, 8, 3, 12, 34, 0);
        assertEquals(expectedOutput, Task.convertStringToDate("2003-08-03 1234"));
    }

    @Test
    public void convertStringToDate_validInput7() {
        LocalDateTime expectedOutput = LocalDateTime.of(2003, 8, 3, 12, 34, 0);
        assertEquals(expectedOutput, Task.convertStringToDate("03/08/2003 1234"));
    }

    @Test
    public void convertStringToDate_validInput8() {
        LocalDateTime expectedOutput = LocalDateTime.of(2003, 8, 3, 12, 34, 0);
        assertEquals(expectedOutput, Task.convertStringToDate("2003/08/03 1234"));
    }

    @Test
    public void convertStringToDate_invalidInput1() {
        assertNull(Task.convertStringToDate("03082003"));
    }

    @Test
    public void convertStringToDate_invalidInput2() {
        assertNull(Task.convertStringToDate("abcde"));
    }

    @Test
    public void convertStringToDate_invalidInput3() {
        assertNull(Task.convertStringToDate("1234-56-78"));
    }
}