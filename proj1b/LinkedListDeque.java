/**
 * @author ShuaiYe
 * @date 2019/6/28 21:19
 */
public class LinkedListDeque<T> implements Deque<T> {
    private class Node<T> {
        private T value;
        private Node<T> pre;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
            pre = null;
            next = null;
        }

        public Node(T value, Node<T> pre, Node<T> next) {
            this.value = value;
            this.pre = pre;
            this.next = next;
        }

        @Override
        public String toString() {
            return this.value.toString();
        }
    }

    private Node<T> first;
    private Node<T> last;
    private int size;

    public LinkedListDeque() {

    }

    @Override
    public void addFirst(T item) {
        if (item == null) {
            return;
        }
        Node<T> f = first;
        Node<T> newNode = new Node<>(item, null, first);
        if (f == null) {
            last = newNode;
        } else {
            f.pre = newNode;
        }
        first = newNode;
        size++;
    }

    @Override
    public void addLast(T item) {
        if (item == null) {
            return;
        }
        Node<T> l = last;
        Node<T> newNode = new Node<>(item, last, null);
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        Node<T> cur = first;
        while (first != null) {
            System.out.println(first.toString());
            first = first.next;
        }
    }

    @Override
    public T removeFirst() {
        if (first == null) {
            return null;
        }
        T item = first.value;
        Node<T> next = first.next;
        first.next = null;
        first.value = null;
        if (next == null) {
            last = null;
        } else {
            next.pre = null;
        }
        first = next;
        size--;
        return item;
    }

    @Override
    public T removeLast() {
        if (last == null) {
            return null;
        }
        T item = last.value;
        Node<T> pre = last.pre;
        if (pre == null) {
            first = null;
        } else {
            pre.next = null;
        }
        last = pre;
        size--;
        return item;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index > size - 1 || size == 0) {
            return null;
        }

        Node<T> cur = first;
        while (index > 0 && cur != null) {
            cur = cur.next;
            index--;
        }
        return cur == null ? null : cur.value;
    }

    public T getRecursive(int index) {
        return getHelper(index, first);
    }

    private T getHelper(int index, Node<T> node) {
        if (index < 0 || index > size - 1 || size == 0) {
            return null;
        }
        if (node == null) {
            return null;
        }
        if (index == 0) {
            return node.value;
        }
        return getHelper(index - 1, node.next);
    }
}
