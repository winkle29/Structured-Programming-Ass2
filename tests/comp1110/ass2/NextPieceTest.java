package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class NextPieceTest {

    @Rule
    public Timeout globalTimeout = Timeout.millis(500);

    private void test(String in1, String in2, String expected) {
        String out = Pieces.getNextPiece(in1, in2);
        assertEquals("Current placement is '" + in1 + "' and full placement is '" + in2 + "', expected " + expected + " but got " + out, expected, out);
    }

    Random random = new Random();
    int index = random.nextInt(TestUtility.BAD_PIECES.length - 1);
    String badPiece = TestUtility.BAD_PIECES[index];

    @Test
    public void invalidFullPlacement() {
        test("b00N", null, null);
        test("b00N", "", null);
        test("b00N", badPiece, null);

    }

    @Test
    public void invalidPlacement() {
        test(null, null, null);
        test(null, "", null);
        test(null, badPiece, null);

    }

    @Test
    public void wellFormedPlacement() {
        test("", "B03SG70Si52SL00Nn01Eo63Sp20Er41WS40Ny62N", "B03S");
        test("", "b43SG82Wi70Wl32Sn80Eo50EP20NR11SS03Sy00W", "b43S");
        test("B01Wg53Ni33SL12E", "B01Wg53Ni33SL12En73SO80Ep51Nr40Ns32NY00N", "n73S");
        test("B70WG73SI40Nl02Wn80Eo13N", "B70WG73SI40Nl02Wn80Eo13NP11Nr00Ns33Sy32N", "P11N");
        test("B12Ng20Si82El01Wn00No40NP60Sr03SS43SY52N", "B12Ng20Si82El01Wn00No40NP60Sr03SS43SY52N", null);

    }

    @Test
    public void unFormedPlacement() {
        test("B01Wg53Ni33SL12EY00N", "B01Wg53Ni33SL12En73SO80Ep51Nr40Ns32NY00N", "n73S");
        test("G02WO30NR11S", "b80EG02Wi62NL00Nn60SO30Np63SR11Ss32Sy23S", "b80E");
        test("B81EI50El43Sn03So23NP11Sg70N", "B81Eg70NI50El43Sn03So23NP11Sr20Ns71WY00W", "r20N");
    }

}
