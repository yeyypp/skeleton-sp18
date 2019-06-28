import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {

    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        String a = "abba";
        String b = "a";
        String c = "";
        String d = "abbA";
        assertTrue(palindrome.isPalindrome(a));
        assertTrue(palindrome.isPalindrome(b));
        assertTrue(palindrome.isPalindrome(c));
        assertFalse(palindrome.isPalindrome(d));
    }

    @Test
    public void testNewIsPalindrome() {
        OffByOne o = new OffByOne();
        String a = "acxdb";
        String b = "a";
        String c = "";
        String d = "abba";
        String e = null;
        String f = "%&";
        assertTrue(palindrome.isPalindrome(f, o));
        assertTrue(palindrome.isPalindrome(a, o));
        assertTrue(palindrome.isPalindrome(b, o));
        assertTrue(palindrome.isPalindrome(c, o));
        assertFalse(palindrome.isPalindrome(d, o));
        assertFalse(palindrome.isPalindrome(e, o));
    }
}
