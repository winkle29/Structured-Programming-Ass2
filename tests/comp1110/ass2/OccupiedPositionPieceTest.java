package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class OccupiedPositionPieceTest {

    @Rule
    public Timeout globalTimeout = Timeout.millis(500);

    private void test(String in, List<String> expected) {
        List<String> out = PiecesOrientation.getOccupiedPositionPiece(in);
        assertEquals("Input was '" + in + "', expected " + expected + " but got " + out, out, expected);
    }

    Random random = new Random();
    int badIndex = random.nextInt(TestUtility.BAD_PIECES.length - 1);

    @Test
    public void invalidPiece() {
        test("", Collections.emptyList()); // empty placement
        test(null, Collections.emptyList()); //null placement
        test("S00", Collections.emptyList()); // length less than 4
        test("xxxx", Collections.emptyList()); // invalid placement
        test("S00w", Collections.emptyList()); // invalid direction
        test("S00Np10W", Collections.emptyList()); // exceeded length
        test(TestUtility.BAD_PIECES[badIndex], Collections.emptyList()); // random invalid piece

    }

    @Test
    public void validPiece() {
        test("O11N", List.of("11", "21", "31", "41", "12", "32"));
        test("l82E", List.of("92", "93", "94", "82"));
        test("y41S", List.of("42", "52", "62", "72", "41"));
        test("P61W", List.of("61", "62", "63", "64", "73", "74"));

    }

    @Test
    public void boundedCase() {
        test("b90N", List.of("90", "100", "110", "120", "91"));
        test("R94W", List.of("94", "95", "96", "97", "104", "107"));
        test("Y04E", List.of("14", "15", "16", "17", "06", "07"));
        test("n04S", List.of("05", "15", "25", "14"));

    }
}
