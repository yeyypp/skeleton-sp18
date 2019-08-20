import java.util.*;

/**
 * @author ShuaiYe
 * @date 2019/8/20 22:25
 */
public class Trie {

    /**
     * Basic TrieNode
     */
    private class TrieNode {

        private TrieNode[] links;

        private final int R = 26;

        private boolean isEnd;

        private String word;

        private TrieNode() {
            links = new TrieNode[R];
        }

        private boolean containsKey(char c) {
            return links[c - 'a'] != null;
        }

        private TrieNode get(char c) {
            return links[c - 'a'];
        }

        private void put(char c, TrieNode node) {
            links[c - 'a'] = node;
        }

        private void setEnd() {
            isEnd = true;
        }

        private boolean isEnd() {
            return isEnd;
        }

        private void setItem(String word) {
            this.word = word;
        }

        private String getWord() {
            return word;
        }
    }

    private TrieNode root = new TrieNode();

    public Trie() {

    }

    /**
     * remove all the item that is not a character
     * @param word
     * @return
     */
    private String clean(String word) {
        return word.replaceAll("\\W", "").toLowerCase();
    }

    /**
     * Insert a word to the Trie
     * @param word
     */
    public void insert(String word) {
        String cleanWord = clean(word);
        TrieNode curNode = root;
        for (int i = 0; i < cleanWord.length(); i++) {
            char curC = cleanWord.charAt(i);
            if (!curNode.containsKey(curC)) {
                curNode.put(curC, new TrieNode());
            }
            curNode = curNode.get(curC);
        }
        curNode.setEnd();
        curNode.setItem(word);
    }

    /**
     * Find if a word exist in the Trie
     * @param word
     * @return return null if no such word or the last node of the word if exist
     */
    private TrieNode search(String word) {
        String cleanWord = clean(word);
        TrieNode curNode = root;
        for (int i = 0; i < cleanWord.length(); i++) {
            char curC = cleanWord.charAt(i);
            if (curNode.containsKey(curC)) {
                curNode = curNode.get(curC);
            } else {
                return null;
            }
        }
        return curNode;
    }

    /**
     * Find if the word exist in the Trie
     * @param word
     * @return
     */
    public boolean searchWord(String word) {
        TrieNode node = search(word);
        return node != null && node.isEnd;
    }

    /**
     * Find if the prefix exist in the Trie
     * @param prefix
     * @return
     */
    public boolean searchPrefix(String prefix) {
        TrieNode node = search(prefix);
        return node != null;
    }

    public List<String> wordsByPrefix(String prefix) {
        String cleanPrefix = clean(prefix);
        TrieNode curNode = root;
        for (int i = 0; i < cleanPrefix.length(); i++) {
            char c = cleanPrefix.charAt(i);
            if (!curNode.containsKey(c)) {
                return null;
            }
            curNode = curNode.get(c);
        }
        Set<String> set = new HashSet<>();
        Deque<TrieNode> stack = new LinkedList<>();
        stack.push(curNode);

        while (!stack.isEmpty()) {
            TrieNode cur = stack.pop();
            if (cur.isEnd) {
                set.add(cur.word);
            }
            for (int i = 25; i >= 0; i--) {
                if (cur.links[i] != null) {
                    stack.push(cur.links[i]);
                }
            }
        }
        List<String> ans = new LinkedList<>();
        ans.addAll(set);
        return ans;
    }
}
