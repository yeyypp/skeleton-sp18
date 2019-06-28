/**
 * @author ShuaiYe
 * @date 2019/6/28 21:21
 */
public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        if (word == null || word.length() == 0) {
            return null;
        }
        Deque<Character> ans = new LinkedListDeque<>();
        for (Character c : word.toCharArray()) {
            ans.addLast(c);
        }
        return ans;
    }

    public boolean isPalindrome(String word) {
        if (word == null) {
            return false;
        }
        if (word.length() < 2) {
            return true;
        }
        int left = 0, right = word.length() - 1;
        while (left < right) {
            if (word.charAt(left) != word.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word == null) {
            return false;
        }
        if (word.length() < 2) {
            return true;
        }
        int left = 0, right = word.length() - 1;
        while (left < right) {
            if (!cc.equalChars(word.charAt(left), word.charAt(right))) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

}
