
public class Dog {
    private int size;

    public Dog(int s) {
        size = s;
    }

    /** Makes a noise.
     * @return*/
    public String noise() {
        if (size < 10) {
            return "yip";
        } 
        return "bark";
    }
}
