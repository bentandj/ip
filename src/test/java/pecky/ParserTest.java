package pecky;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;



public class ParserTest {

    @Test
    public void parse_validInput1() {
        assertDoesNotThrow(() -> Parser.parse("list"));
    }

    @Test
    public void parse_invalidInput1() {
        assertEquals(0, Parser.parse("unknown"));
    }

    @Test
    public void parse_invalidInput2() {
        assertEquals(0, Parser.parse("asdfmuoiawef 0awef"));
    }

    @Test
    public void parse_invalidInput3() {
        assertEquals(0, Parser.parse("mark asdf"));
    }

    @Test
    public void parse_invalidInput4() {
        assertEquals(0, Parser.parse("todo"));
    }

    @Test
    public void parse_invalidInput5() {
        assertEquals(0, Parser.parse("date"));
    }

    @Test
    public void parse_invalidInput6() {
        assertEquals(0, Parser.parse("date 00000000"));
    }
}
