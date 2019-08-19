/**
 * @author ShuaiYe
 * @date 2019/8/19 21:39
 */
public class ArrayMinHeap<T> {

    private static class Node<T> {
        private T item;
        private double priority;

        private Node(T item, double priority) {
            this.item = item;
            this.priority = priority;
        }

        private T getItem() {
            return item;
        }

        private double getPriority() {
            return priority;
        }

        private void setPriority(double priority) {
            this.priority = priority;
        }
    }

    private Node[] contents;
    private int size;

    public ArrayMinHeap() {
        contents = new Node[16];
        size = 0;
        contents[0] = null;
    }

    private int leftIndex(int i) {
        return 2 * i;
    }
}
