import javax.xml.stream.FactoryConfigurationError;

/**
 * @author ShuaiYe
 * @date 2019/6/26 18:12
 */
public class ArrayDeque<T> {
    private T[] elements;
    private int size;
    private static final double FACTOR = 0.25;
    private int first;
    private int last;
    private static final int MINSIZE = 8;

    public ArrayDeque() {
        this.elements = (T[]) new Object[8];
        this.first = 3;
        this.last = 4;
        this.size = 0;
    }

    private void resize(int capacity) {
        T[] newElements = (T[]) new Object[capacity];
        System.arraycopy(elements, 0, newElements, 0, size);
        elements = newElements;
    }

    public void addFirst(T item) {
        if (item == null) {
            return;
        }
        elements[first] = item;
        if (first == 0) {
            first = elements.length - 1;
        } else {
            first--;
        }
        size++;
        if (size == elements.length) {
            resize(size * 2);
            first = elements.length - 1;
            last = size;
        }
    }

    public void addLast(T item) {
        if (item == null) {
            return;
        }
        elements[last] = item;
        if (last == elements.length - 1) {
            last = 0;
        } else {
            last++;
        }
        size++;
        if (size == elements.length) {
            resize(size * 2);
            first = elements.length - 1;
            last = size;
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
        int i = first;
        for (int j = 0; j < size; j++) {
            i = ++i == elements.length ? 0 : i;
            System.out.println(elements[i].toString());
        }
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        first = ++first == elements.length ? 0 : first;
        T item = elements[first];
        elements[first] = null;
        size--;
        if (size < elements.length * FACTOR) {
            int newSize = elements.length * FACTOR < MINSIZE ? MINSIZE : (int) (elements.length * FACTOR);
            T[] newElements = (T[]) new Object[newSize];
            if (first > last) {
                System.arraycopy(elements, first + 1, newElements, 0, size);
                first = newElements.length - 1;
                last = size;
                elements = newElements;
            } else {
                for (int i = 0; i < size; i++) {
                    first = ++first > elements.length - 1 ? 0 : first;
                    newElements[i] = elements[first];
                }
                first = newElements.length - 1;
                last = size;
                elements = newElements;
            }
        }
        return item;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        last = --last < 0 ? elements.length - 1 : last;
        T item = elements[last];
        elements[last] = null;
        size--;
        if (size < elements.length * FACTOR) {
            int newSize = elements.length * FACTOR < MINSIZE ? MINSIZE : (int) (elements.length * FACTOR);
            T[] newElements = (T[]) new Object[newSize];
            if (first > last) {
                System.arraycopy(elements, first + 1, newElements, 0, size);
                first = newElements.length - 1;
                last = size;
                elements = newElements;
            } else {
                for (int i = 0; i < size; i++) {
                    first = ++first > elements.length - 1 ? 0 : first;
                    newElements[i] = elements[first];
                }
                first = newElements.length - 1;
                last = size;
                elements = newElements;
            }
        }
        return item;
    }

    public T get(int index) {
        if (index < 0 || index > size - 1) {
            return null;
        }
        first = ++first == elements.length ? 0 : first;
        return elements[(first + index) % elements.length];
    }
}
