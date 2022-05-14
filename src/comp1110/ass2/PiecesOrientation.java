package comp1110.ass2;

import java.util.*;

//======================Author: Nik Nur Aisyah binti Amran ==============================================
public class PiecesOrientation {

    /**
     * List of protrusions.
     */
    public enum Protrusion {

        SINGLE, DOUBLE;

    }


    /**
     * Get the current number of protrusion placed in board from a string of piece's current state.
     * @param piecePlacement a string of piece's current state
     * @return number of protrusion placed in board
     */
    public static Protrusion getProtrusion(String piecePlacement) {

        String colour = String.valueOf(piecePlacement.charAt(0));
        if ("BGILNOPRSY".contains(colour)) {
            return Protrusion.SINGLE;
        } else if ("bgilnoprsy".contains(colour)) {
            return Protrusion.DOUBLE;
        }

        return null;
    }


    /**
     * Get the current direction from a string of piece's current state.
     */
    public static Direction getDirection(String pieceState) {
        return Direction.fromChar(pieceState.charAt(3));
    }


    /**
     * This method returns spine's length.
     * @param character colour
     * @return spine's length
     */
    public static int getSpineChar(char character) {

        int spine = 0;

        if ("BOPRSY".contains(String.valueOf(character).toUpperCase())) {
            spine = 4;
        } else if ("GILN".contains(String.valueOf(character).toUpperCase())) {
            spine = 3;
        } else if ('X' == character) {
            spine = 1;
        }

        return spine;

    }

    /**
     * This method returns spine's length.
     * @param piecePlacement piece placement
     * @return spine's length (either 3 or 4)
     */
    public static int getSpine(String piecePlacement) {

        char character = piecePlacement.charAt(0);
        return getSpineChar(character);

    }

    /**
     * This method returns coordinates on board that have been occupied by a piece
     * @param piecePlacement piece placement
     * @return list of coordinates
     */
    public static List<String> getOccupiedPositionPiece (String piecePlacement) {

        if (piecePlacement == null)
            return Collections.emptyList();

        if (piecePlacement.equals(""))
            return Collections.emptyList();

        if (!FitGame.isPiecePlacementWellFormed(piecePlacement))
            return Collections.emptyList();

        // spine

        int x = Character.getNumericValue(piecePlacement.charAt(1));
        int y = Character.getNumericValue(piecePlacement.charAt(2));

        int spine = PiecesOrientation.getSpine(piecePlacement);

        List<String> list = new LinkedList<>();

        switch (piecePlacement.charAt(3)) {

            case ('N'):

                for (int i = 0; i < spine; i++) {
                    list.add((x + i) + Integer.toString(y));
                }

            case ('E'):

                for (int i = 0; i < spine; i++) {
                    list.add(Integer.toString((x + 1)) + (y + i));
                }

            case ('S'):

                for (int i = 0; i < spine; i++) {
                    list.add((x + i) + Integer.toString((y + 1)));
                }

            case ('W'):

                for (int i = 0; i < spine; i++) {
                    list.add(Integer.toString(x) + (y + i));
                }

        }

        list = list.subList(0, spine);

        // protrusion

        String firstNorth = Integer.toString(x) + (y + 1);
        String secondNorth = Integer.toString((x + 1)) + (y + 1);
        String thirdNorth = Integer.toString((x + 2)) + (y + 1);
        String fourthNorth = Integer.toString((x + 3)) + (y + 1);

        String firstEast = Integer.toString(x) + y;
        String secondEast = Integer.toString(x) + (y + 1);
        String thirdEast = Integer.toString(x) + (y + 2);
        String fourthEast = Integer.toString(x) + (y + 3);

        String firstSouth = Integer.toString(x) + y;
        String secondSouth = Integer.toString((x + 1)) + y;
        String thirdSouth = Integer.toString((x + 2)) + y;
        String fourthSouth = Integer.toString((x + 3)) + y;

        String firstWest = Integer.toString((x + 1)) + y;
        String secondWest = Integer.toString((x + 1)) + (y + 1);
        String thirdWest = Integer.toString((x + 1)) + (y + 2);
        String fourthWest = Integer.toString((x + 1)) + (y + 3);


        // DOUBLE protrusion, NORTH & EAST
        boolean conditionOne = PiecesName.fromChar(piecePlacement) == PiecesName.BLUE
                || PiecesName.fromChar(piecePlacement) == PiecesName.LIME_GREEN
                || PiecesName.fromChar(piecePlacement) == PiecesName.RED;

        boolean conditionTwo = PiecesName.fromChar(piecePlacement) == PiecesName.GREEN
                || PiecesName.fromChar(piecePlacement) == PiecesName.NAVY_BLUE
                || PiecesName.fromChar(piecePlacement) == PiecesName.ORANGE
                || PiecesName.fromChar(piecePlacement) == PiecesName.SKY_BLUE;

        boolean conditionThree = PiecesName.fromChar(piecePlacement) == PiecesName.INDIGO
                || PiecesName.fromChar(piecePlacement) == PiecesName.PINK;

        boolean conditionFour = PiecesName.fromChar(piecePlacement) == PiecesName.YElLOW;

        // DOUBLE protrusion, SOUTH & WEST
        boolean conditionFive = PiecesName.fromChar(piecePlacement) == PiecesName.INDIGO
                || PiecesName.fromChar(piecePlacement) == PiecesName.YElLOW;

        boolean conditionSix = PiecesName.fromChar(piecePlacement) == PiecesName.GREEN
                || PiecesName.fromChar(piecePlacement) == PiecesName.NAVY_BLUE
                || PiecesName.fromChar(piecePlacement) == PiecesName.PINK;

        boolean conditionSeven = PiecesName.fromChar(piecePlacement) == PiecesName.LIME_GREEN
                || PiecesName.fromChar(piecePlacement) == PiecesName.ORANGE
                || PiecesName.fromChar(piecePlacement) == PiecesName.SKY_BLUE;

        boolean conditionEight = PiecesName.fromChar(piecePlacement) == PiecesName.BLUE
                || PiecesName.fromChar(piecePlacement) == PiecesName.RED;


        // SINGLE protrusion, NORTH & EAST
        boolean conditionNine = PiecesName.fromChar(piecePlacement) == PiecesName.GREEN
                || PiecesName.fromChar(piecePlacement) == PiecesName.PINK;

        boolean conditionTen = PiecesName.fromChar(piecePlacement) == PiecesName.INDIGO
                || PiecesName.fromChar(piecePlacement) == PiecesName.SKY_BLUE;

        boolean conditionEleven = PiecesName.fromChar(piecePlacement) == PiecesName.LIME_GREEN
                || PiecesName.fromChar(piecePlacement) == PiecesName.NAVY_BLUE
                || PiecesName.fromChar(piecePlacement) == PiecesName.ORANGE;

        boolean conditionTwelve = PiecesName.fromChar(piecePlacement) == PiecesName.BLUE;

        boolean conditionThirteen = PiecesName.fromChar(piecePlacement) == PiecesName.RED;

        boolean conditionFourteen = PiecesName.fromChar(piecePlacement) == PiecesName.YElLOW;

        // DOUBLE protrusion, SOUTH & WEST
        boolean conditionFifteen = PiecesName.fromChar(piecePlacement) == PiecesName.YElLOW
                || PiecesName.fromChar(piecePlacement) == PiecesName.INDIGO;

        boolean conditionSixteen = PiecesName.fromChar(piecePlacement) == PiecesName.GREEN
                || PiecesName.fromChar(piecePlacement) == PiecesName.SKY_BLUE;

        boolean conditionSeventeen = PiecesName.fromChar(piecePlacement) == PiecesName.BLUE
                || PiecesName.fromChar(piecePlacement) == PiecesName.LIME_GREEN
                || PiecesName.fromChar(piecePlacement) == PiecesName.NAVY_BLUE;

        boolean conditionEighteen = PiecesName.fromChar(piecePlacement) == PiecesName.PINK;

        boolean conditionNineteen = PiecesName.fromChar(piecePlacement) == PiecesName.RED;

        boolean conditionTwenty = PiecesName.fromChar(piecePlacement) == PiecesName.ORANGE;



        if (PiecesOrientation.getProtrusion(piecePlacement) == PiecesOrientation.Protrusion.DOUBLE) {

            switch (piecePlacement.charAt(3)) {

                case ('N'):

                    if (conditionOne) {
                        list.add(firstNorth);
                        break;

                    } else if (conditionTwo) {
                        list.add(secondNorth);
                        break;

                    } else if (conditionThree) {
                        list.add(thirdNorth);
                        break;

                    } else if (conditionFour) {
                        list.add(fourthNorth);
                        break;

                    }

                case ('E'):

                    if (conditionOne) {
                        list.add(firstEast);
                        break;

                    } else if (conditionTwo) {
                        list.add(secondEast);
                        break;

                    } else if (conditionThree) {
                        list.add(thirdEast);
                        break;

                    } else if (conditionFour) {
                        list.add(fourthEast);
                        break;

                    }

                case ('S'):

                    if (conditionFive) {
                        list.add(firstSouth);
                        break;

                    } else if (conditionSix) {
                        list.add(secondSouth);
                        break;

                    } else if (conditionSeven) {
                        list.add(thirdSouth);
                        break;

                    } else if (conditionEight) {
                        list.add(fourthSouth);
                        break;

                    }

                case ('W'):

                    if (conditionFive) {
                        list.add(firstWest);
                        break;

                    } else if (conditionSix) {
                        list.add(secondWest);
                        break;

                    } else if (conditionSeven) {
                        list.add(thirdWest);
                        break;

                    } else if (conditionEight) {
                        list.add(fourthWest);
                        break;

                    }

            }

        } else if (PiecesOrientation.getProtrusion(piecePlacement) == PiecesOrientation.Protrusion.SINGLE) {

            switch (piecePlacement.charAt(3)) {

                case ('N'):

                    if (conditionNine) {
                        list.add(firstNorth);
                        list.add(secondNorth);
                        break;

                    } else if (conditionTen) {
                        list.add(secondNorth);
                        list.add(thirdNorth);
                        break;

                    } else if (conditionEleven) {
                        list.add(firstNorth);
                        list.add(thirdNorth);
                        break;

                    } else if (conditionTwelve) {
                        list.add(secondNorth);
                        list.add(fourthNorth);
                        break;

                    } else if (conditionThirteen) {
                        list.add(firstNorth);
                        list.add(fourthNorth);
                        break;

                    } else if (conditionFourteen) {
                        list.add(thirdNorth);
                        list.add(fourthNorth);
                        break;

                    }

                case ('E'):

                    if (conditionNine) {
                        list.add(firstEast);
                        list.add(secondEast);
                        break;

                    } else if (conditionTen) {
                        list.add(secondEast);
                        list.add(thirdEast);
                        break;

                    } else if (conditionEleven) {
                        list.add(firstEast);
                        list.add(thirdEast);
                        break;

                    } else if (conditionTwelve) {
                        list.add(secondEast);
                        list.add(fourthEast);
                        break;

                    } else if (conditionThirteen) {
                        list.add(firstEast);
                        list.add(fourthEast);
                        break;

                    } else if (conditionFourteen) {
                        list.add(thirdEast);
                        list.add(fourthEast);
                        break;

                    }

                case ('S'):

                    if (conditionFifteen) {
                        list.add(firstSouth);
                        list.add(secondSouth);
                        break;

                    } else if (conditionSixteen) {
                        list.add(secondSouth);
                        list.add(thirdSouth);
                        break;

                    } else if (conditionSeventeen) {
                        list.add(firstSouth);
                        list.add(thirdSouth);
                        break;

                    } else if (conditionEighteen) {
                        list.add(thirdSouth);
                        list.add(fourthSouth);
                        break;

                    } else if (conditionNineteen) {
                        list.add(firstSouth);
                        list.add(fourthSouth);
                        break;

                    } else if (conditionTwenty) {
                        list.add(secondSouth);
                        list.add(fourthSouth);
                        break;

                    }

                case ('W'):

                    if (conditionFifteen) {
                        list.add(firstWest);
                        list.add(secondWest);
                        break;

                    } else if (conditionSixteen) {
                        list.add(secondWest);
                        list.add(thirdWest);
                        break;

                    } else if (conditionSeventeen) {
                        list.add(firstWest);
                        list.add(thirdWest);
                        break;

                    } else if (conditionEighteen) {
                        list.add(thirdWest);
                        list.add(fourthWest);
                        break;

                    } else if (conditionNineteen) {
                        list.add(firstWest);
                        list.add(fourthWest);
                        break;

                    } else if (conditionTwenty) {
                        list.add(secondWest);
                        list.add(fourthWest);
                        break;

                    }

            }

        }

        return list;


    }

    /**
     * This method returns coordinates on board that have been occupied by pieces.
     * @param placement current board placement
     * @return list of coordinates
     */
    public static List<String> getOccupiedPositionBoard (String placement) {

        List<String> list = new LinkedList<>();
        String input;

        if (placement == null || placement.length() == 0)
            return Collections.emptyList();

        for (int i = 0; i < placement.length(); i+=4) {

            input = placement.substring(i, i + 4);
            list.addAll(getOccupiedPositionPiece(input));

        }
        return list;
    }

    /**
     * This method returns coordinates on board that have not been occupied by any pieces.
     * @param placement current board placement
     * @return list of coordinates
     */
    public static List<String> getVacantPositionBoard (String placement) {

        List<String> result = new LinkedList<>();
        List<String> occupied = getOccupiedPositionBoard(placement);

        List<String> holes = Arrays.asList(
                "00", "10", "20", "30", "40", "50", "60", "70", "80", "90",
                "01", "11", "21", "31", "41", "51", "61", "71", "81", "91",
                "02", "12", "22", "32", "42", "52", "62", "72", "82", "92",
                "03", "13", "23", "33", "43", "53", "63", "73", "83", "93",
                "04", "14", "24", "34", "44", "54", "64", "74", "84", "94"
        );

        for (String hole : holes) {
            if (!occupied.contains(hole)) {
                result.add(hole);
            }

        }
        return result;
    }

    /**
     * This method returns the available pieces that are not on board.
     * @param placement current board placement
     * @return string of available pieces
     */
    public static String getAvailablePieces(String placement) {

        String compare = getOccupiedColours(placement);
        String index = "BGILNOPRSY";
        List<String> list = new LinkedList<>();

        for (int i = 0; i < index.length(); i ++) {
            if (!compare.toUpperCase().contains(String.valueOf(index.charAt(i))))
                list.add(String.valueOf(index.charAt(i)));
        }

        String str = "";

        for (String s : list) {
            str = str.concat(s).concat("00N");
        }

        return str.toLowerCase().concat(str.toUpperCase());
    }

    /**
     * This method returns the colours that are on board.
     * @param placement current board placement
     * @return string of colours
     */
    public static String getOccupiedColours(String placement) {

        String result = "";

        String compare = placement.replaceAll("[^a-zA-Z ]", "");

        for (int i = 0; i < compare.length(); i += 2) {
            result = result.concat(String.valueOf(compare.charAt(i)));

        }

        return result;
    }

//=============================================================================================
}
