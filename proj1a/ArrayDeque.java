import javax.xml.stream.FactoryConfigurationError;

/**
 * @author ShuaiYe
 * @date 2019/6/26 18:12
 */
public class ArrayDeque<T> {
    private T[] elements;
    private int size;
    private static final double FACTOR = 0.25;
    private static final int MINSIZE = 8;
    private int first;
    private int last;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        this.elements = (T[]) new Object[MINSIZE];
        size = 0;
        first = 3;
        last = 4;
        nextFirst = 3;
        nextLast = 4;
    }

    private void addSize() {
        T[] newElements = (T[]) new Object[elements.length * 2];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[first];
            first = ++first == elements.length  ? 0 : first;
        }
        first = 0;
        last = size - 1;
        nextFirst = newElements.length - 1;
        nextLast = size;
        elements = newElements;
    }

    private void shrinkSize() {
        if (elements.length == MINSIZE) {
            return;
        }
        int newSize = elements.length * FACTOR < MINSIZE ? MINSIZE : (int) (elements.length * FACTOR);
        T[] newElements = (T[]) new Object[newSize];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[first];
            first = ++first == elements.length  ? 0 : first;
        }
        first = 0;
        last = size - 1;
        nextFirst = newElements.length - 1;
        nextLast = size;
        elements = newElements;
    }

    public void addFirst(T item) {
        elements[nextFirst] = item;
        first = nextFirst;
        nextFirst = --nextFirst < 0 ? elements.length - 1 : nextFirst;
        size++;
        if (size == 1) {
            last = first;
        }
        if (size == elements.length) {
            addSize();
        }
    }

    public void addLast(T item) {
        elements[nextLast] = item;
        last = nextLast;
        nextLast = ++nextLast == elements.length ? 0 : nextLast;
        size++;
        if (size == 1) {
            first = last;
        }
        if (size == elements.length) {
            addSize();
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        if (size == 0) {
            return;
        }
        int cur = first;
        for (int i = 0; i < size; i++) {
            System.out.println(elements[cur]);
            cur = ++cur == elements.length ? 0 : cur;
        }
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T item = elements[first];
        elements[first] = null;
        nextFirst = first;
        first = ++first == elements.length ? 0 : first;
        size--;
        if (size < elements.length * FACTOR) {
            shrinkSize();
        }
        return item;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T item = elements[last];
        elements[last] = null;
        nextLast = last;
        last = --last < 0 ? elements.length - 1 : last;
        size--;
        if (size < elements.length * FACTOR) {
            shrinkSize();
        }
        return item;
    }

    public T get(int index) {
        if (index < 0 || index > size - 1) {
            return null;
        }
        int cur = first;
        return elements[(cur + index) % elements.length];
    }
}
