
/**
 * @author ShuaiYe
 * @date 2019/6/27 18:00
 */
public class Test {
    public static void main(String[] args) {
        ArrayDeque<Integer> arrayDeque = new ArrayDeque<>();
        arrayDeque.addFirst(0);
        arrayDeque.removeFirst();
        arrayDeque.addFirst(2);
        arrayDeque.removeFirst();
        arrayDeque.addLast(4);
        System.out.println(arrayDeque.get(0));
    }
}
