package comp1110.ass2;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertTrue;


public class getOrientationIntegerTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(2000);


    private void test(String piece, int expected) {
        int out = Pieces.getOrientation(piece);
        assertTrue("Expected " + expected + " for input piece placement" + piece +
                ", but got " + out + ".", out == expected);
    }

    @Test
    public void test1() {
        test("B00N", 0);
        test("i45S", 2);
    }

    @Test
    public void test2() {
        test("O53W", 3);
        test("l34E", 1);
    }

    @Test
    public void test3() {
        test("p55N", 0);
        test("Y56S", 2);
    }
}
