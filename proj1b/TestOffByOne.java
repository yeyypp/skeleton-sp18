import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
    public void testEqualChars() {
        char a = 'a';
        char b = 'b';
        char c = 'a';
        char A = 'A';
        assertFalse(offByOne.equalChars(a, A));
        assertFalse(offByOne.equalChars(a, c));
        assertTrue(offByOne.equalChars(a, b));
        assertTrue(offByOne.equalChars(b, a));
    }
}
