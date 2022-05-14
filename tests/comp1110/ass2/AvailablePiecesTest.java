package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AvailablePiecesTest {

    @Rule
    public Timeout globalTimeout = Timeout.millis(1000);
    private void test(String in, String expected) {
        String out = FitGame.getAvailablePieces(in);
        assertTrue("Input was '" + in + "', expected " + expected + " but got " + out, out.equals(expected));
    }


    @Test
    public void coloursReturned() {
        test("","BGILNOPRSY");
        test("b23NY63S", "GILNOPRS");
        test("b30NN70WP23Ss80EY00W", "GILOR");
        test("B03SG70Si52SL00Nn01Er41WS40Ny62N", "OP");
        test("b00Wg60SI10Sl03Sn33SO52Np12NR80ES30Ny63S", "");
    }
}
