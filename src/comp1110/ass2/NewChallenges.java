package comp1110.ass2;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

//======================Author: Nik Nur Aisyah binti Amran ==============================================
public class NewChallenges {

    public String objective;
    String placement;
    Set<String> placements;
    public String shape;

    NewChallenges(String iObjective, String iPlacement, String iShape) {
        objective = iObjective;
        placement = iPlacement;
        shape = iShape;
        placements = new HashSet<>();
        placements.add(iPlacement);
    }

    public static final NewChallenges[] SOLUTIONS = {
            new NewChallenges(blackCircles("g22NI42En70Wo50ES10S").concat("n70W"),
                    "g22NI42Eo50ES10S","HEART"),
            new NewChallenges(blackCircles("g10NI81Ep63SS00W").concat("g10N"),
                    "I81Ep63SS00W","CORNERS"),
            new NewChallenges(blackCircles("G01WI60Np80Es03S").concat("s03S"),
                    "G01WI60Np80E", "CORNERS"),
            new NewChallenges(blackCircles("G13Sn60No31ES50W").concat("G13S"),
                    "n60No31ES50W", "SLIDE"),
            new NewChallenges(blackCircles("I63Sn10Np51WS30E").concat("I63S"),
                    "n10Np51WS30E", "SLIDE"),
            new NewChallenges(blackCircles("G31En51WR33Sy30N").concat("G31E"),
                    "n51WR33Sy30N", "SQUARE"),
            new NewChallenges(blackCircles("G51Wn31ER30Ny33S").concat("y33S"),
                    "G51Wn31ER30N", "SQUARE"),
            new NewChallenges(blackCircles("G52SI33Sn03So63Sr22NS30S").concat("n03S"),
                    "G52SI33So63Sr22NS30S", "TRIANGLE"),
            new NewChallenges(blackCircles("G21SI33Nl40En61W").concat("I33N"),
                    "G21Sl40En61W", "DIAMOND"),
            new NewChallenges(blackCircles("b51Ng31En81Ep01NS52SY02S").concat("Y02Sn81E"),
                    "b51Ng31Ep01NS52S", "LINE")
    };


    /**
     * This method generates placement of black pieces that occupy certain positions on board
     * to create certain shapes.
     * @param placement current board placement
     * @return placement of black pieces
     */
    public static String blackCircles(String placement) {

        String result = "";
        List<String> vacant = PiecesOrientation.getVacantPositionBoard(placement);

        for (String s : vacant) {
            result = result.concat("X" + s + "X");

        }
        return result;

    }

    public String getInitialState() { return objective;}

    public String getShape() { return shape;}

    public String getPlacement() { return placement;}

    public static NewChallenges getNewChallenge(int index) { return SOLUTIONS[index];}
//============================================================================================

}
