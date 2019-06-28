/**
 * @author ShuaiYe
 * @date 2019/6/28 22:01
 */
public class OffByOne implements CharacterComparator {

    @Override
    public boolean equalChars(char x, char y) {
        return Math.abs(x - y) == 1;
    }
}
