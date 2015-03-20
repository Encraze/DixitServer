import java.util.Random;

/**
 * @author Igor Royd
 */
public class Util {
    public static final Random RND = new Random();
    private static long counter;
    
    public static long nextId() {
        return counter++;
    }
}
