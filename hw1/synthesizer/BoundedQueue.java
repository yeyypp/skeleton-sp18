package synthesizer;

/**
 * @author ShuaiYe
 * @date 2019/7/5 21:58
 */
public interface BoundedQueue<T> {

    default boolean isEmpty() {
        return fillCount() == 0;
    }

    default boolean isFull() {
        return capacity() == fillCount();
    }


    //return size of the buffer
    int capacity();

    // return number of items currently in the buffer
    int fillCount();

    // add item x to the end
    void enqueue(T x);

    //delete and return item from the front
    T dequeue();

    //return item from the front
    T peek();
}
