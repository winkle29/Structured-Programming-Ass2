package comp1110.ass2;

import comp1110.ass2.gui.Board;

import java.util.HashSet;
import java.util.Set;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class provides the text interface for the IQ Fit Game
 * <p>
 * The game is based directly on Smart Games' IQ-Fit game
 * (https://www.smartgames.eu/uk/one-player-games/iq-fit)
 */
public class FitGame {
    static final int[] OFF_BOARD = {-1, -1};


    /**
     * The objective represents the problem to be solved
     */
    Games objective;

    /**
     * Constructing a game with a specific objective
     *
     * @param objective problem string
     */
    public FitGame(Games objective) {
        this.objective = objective;
    }


    /**
     * Constructs a game for a given level of difficulty.
     * Chooses new objective and creates a new instance of game
     * given the difficulty
     *
     * @param difficulty chosen difficulty level
     */
    public FitGame(int difficulty) {
        this(Games.newGame(difficulty));
    }



//=================Author: Nik Nur Aisyah binti Amran======================================
    /**
     * Determine whether a piece placement is well-formed according to the
     * following criteria:
     * - it consists of exactly four characters
     * - the first character is a valid piece descriptor character (b, B, g, G, ... y, Y)
     * - the second character is in the range 0 .. 9 (column)
     * - the third character is in the range 0 .. 4 (row)
     * - the fourth character is in valid orientation N, S, E, W
     *
     * @param piecePlacement A string describing a piece placement
     * @return True if the piece placement is well-formed
     */
    public static boolean isPiecePlacementWellFormed(String piecePlacement) {

        boolean condition;

        condition = piecePlacement.length() == 4
                && PiecesName.fromChar(piecePlacement) != null
                && "0123456789".indexOf(piecePlacement.charAt(1)) != -1
                && "01234".indexOf(piecePlacement.charAt(2)) != -1
                && Direction.fromChar(piecePlacement.charAt(3)) != null;


        return condition; // FIXME Task 2: determine whether a piece placement is well-formed
    }
//=========================================================================================

//=================Author: Prabhjot Kaur Dhawan============================================
    /**
     * Determine whether a placement string is well-formed:
     * - it consists of exactly N four-character piece placements (where N = 1 .. 10);
     * - each piece placement is well-formed
     * - no shape appears more than once in the placement
     * - the pieces are ordered correctly within the string
     *
     * @param placement A string describing a placement of one or more pieces
     * @return True if the placement is well-formed
     */
    public static boolean isPlacementWellFormed(String placement) {
        // Initialization
        char[] order = {'B', 'G', 'I', 'L', 'N', 'O', 'P', 'R', 'S', 'Y'};
        int count;
        int n = 0;
        boolean check_well;
        String[] pieces = new String[10];

        // Checking N four-character piece placements in the placement string
        if (placement.length() % 4 != 0 || placement.equals(""))
            return false;

        // Checking no shapes appear more than once and the order is correct
        for (int i = 0; i < placement.length(); i = i + 4) {
            count = 0;
            for (int j = 0; j < order.length; j++) {
                if ((Character.toUpperCase(placement.charAt(i)) == order[j]) && (j > n || i == 0)) {
                    n = j;
                    count++;
                }
            }
            if (count != 1)
                return false;
        }

        // Slicing placement string to piece placement and checking if well-formed
        for (int start = 0, stop = 4, p = 0; start < placement.length() - 3 && stop <= placement.length()
                && p < 10; start = start + 4, stop = stop + 4, p++) {
            pieces[p] = placement.substring(start, stop);
            check_well = isPiecePlacementWellFormed(pieces[p]);
            if (!check_well)
                return false;
        }

        return true;
    } // FIXME Task 3: determine whether a placement is well-formed
//=============================================================================================

//=================Author: Nik Nur Aisyah binti Amran==========================================
    /**
     * Determine whether a placement string is valid.
     * <p>
     * To be valid, the placement string must be:
     * - well-formed, and
     * - each piece placement must be a valid placement according to the
     * rules of the game:
     * - pieces must be entirely on the board
     * - pieces must not overlap each other
     *
     * @param placement A placement string
     * @return True if the placement sequence is valid
     */
    public static boolean isPlacementValid(String placement) {

        boolean condition = true;

        if (!isPlacementWellFormed(placement)) {
            return false;
        }

        List<String> list = PiecesOrientation.getOccupiedPositionBoard(placement);

        for (int i = 0; i < list.size() && condition; i++) {

            String a = list.get(i);

            condition = a.length() == 2
                    && Character.isDigit(a.charAt(0))
                    && "01234".indexOf(a.charAt(1)) != -1
                    && list.indexOf(a) == list.lastIndexOf(a);

        }
        return condition;

    }//  FIXME Task 5: determine whether a placement string is valid

    /**
     * This method checks whether piece placement is valid for interesting challenges.
     *
     * @param piecePlacement piece placement
     * @param currentPlacement current board placement
     * @param objective placement of solved board
     * @return whether piece placement is valid
     */
    public static boolean isPlacementValidNew(String piecePlacement, String currentPlacement, String objective) {

        currentPlacement = Board.removePosition(currentPlacement, piecePlacement);

        List<String> occupiedBlack = PiecesOrientation.getVacantPositionBoard(objective);
        List<String> occupiedCurrent = PiecesOrientation.getOccupiedPositionBoard(currentPlacement);
        List<String> occupiedBoard = Stream.concat(occupiedBlack.stream(), occupiedCurrent.stream())
                .collect(Collectors.toList());
        List<String> occupiedPiece = PiecesOrientation.getOccupiedPositionPiece(piecePlacement);

        boolean condition = true;

        for (String s: occupiedBoard) {
            condition = condition && (!occupiedPiece.contains(s));
        }


        return condition && isPlacementValid(Board.updateCurrentPosition(currentPlacement, piecePlacement));
    }
//=============================================================================================

//=====================Author: Aaron Chhor-Luu=================================================
    /**
     * Given a string describing a placement of pieces, and a location
     * that must be covered by the next move, return a set of all
     * possible next viable piece placements which cover the location.
     * <p>
     * For a piece placement to be viable it must:
     * - be a well formed piece placement
     * - be a piece that is not already placed
     * - not overlap a piece that is already placed
     * - cover the location
     *
     * @param placement A starting placement string
     * @param col       The location's column.
     * @param row       The location's row.
     * @return A set of all viable piece placements, or null if there are none.
     */
    static Set<String> getViablePiecePlacements(String placement, int col, int row) {

        Set<String> viablePiecePlacements = new HashSet<String>();
        String colS = Integer.toString(col);
        String rowS = Integer.toString(row);
        String coordinates = colS + rowS;

        // checks if the col and row used are already occupied
        if (PiecesOrientation.getOccupiedPositionBoard(placement).contains(coordinates)) {
            return null;
        }

        for (String possiblePiece : Objects.requireNonNull(getPossiblePieces(placement, col, row))) {

            String newPlacement = Board.arrangeCurrentPlacement(placement, possiblePiece);

            if (isPlacementValid(newPlacement) && PiecesOrientation.getOccupiedPositionBoard(newPlacement).contains(coordinates)) {
                viablePiecePlacements.add(possiblePiece);
            }

        }

        if (viablePiecePlacements.size() == 0) {
            return null;
        }

        return viablePiecePlacements;
    }
    // FIXME Task 6: determine the set of all viable piece placements given existing placements


    /**
     * Given a placement string return the pieces that are not being used.
     *
     * @param placement A starting placement string
     * @return A string of all the pieces that are not currently being used.
     */
    public static String getAvailablePieces(String placement) {
        String allColours = "BGILNOPRSY";
        StringBuilder rtnColours = new StringBuilder();

        if (placement.length() == 0) {
            return allColours;
        }

        for (int b = 0; b < allColours.length(); b++) {
            int counter = 0;
            for (int a = 0; a < placement.length(); a += 4) {
                if (placement.charAt(a) == allColours.charAt(b) || placement.charAt(a) == Character.toLowerCase(allColours.charAt(b))) {
                    counter++;
                }

            }
            if (counter == 0) {
                rtnColours.append(allColours.charAt(b));
            }

        }
        return rtnColours.toString();
    }


    /**
     * Finds all the possible piece placements.
     *
     * @param placement A starting placement string
     * @param col       The location's column.
     * @param row       The location's row.
     * @return A string of all possible piece placements.
     */
    public static List<String> getPossiblePieces(String placement, int col, int row) {
        List<String> possiblePieces = new ArrayList<>();

        char direction = ' ';
        int x;
        int y;
        String newCoordinates;// = "";
        int rowReducing = 0;
        int colReducing = 0;

        if (col < 0 || col > 9 || row < 0 || row > 4) {
            return null;
        }

        for (int a = 0; a < 4; a++) {
            switch (a) {
                case 0: {
                    direction = 'N';
                    rowReducing = 4;
                    colReducing = 2;
                    break;
                }
                case 1: {
                    direction = 'E';
                    rowReducing = 2;
                    colReducing = 4;
                    break;
                }
                case 2: {
                    direction = 'S';
                    rowReducing = 4;
                    colReducing = 2;
                    break;
                }
                case 3: {
                    direction = 'W';
                    rowReducing = 2;
                    colReducing = 4;
                    break;
                }

            }

            for (y = 0; y < colReducing; y++) {
                for (x = 0; x < rowReducing; x++) {

                    if ((row - y) < 0 || (col - x) < 0)
                        break;
                    else {
                        newCoordinates = Integer.toString(col - x).concat(Integer.toString(row - y));
                        for (int b = 0; b < getAvailablePieces(placement).length(); b++) {
                            possiblePieces.add(getAvailablePieces(placement).charAt(b) + newCoordinates + direction);
                            possiblePieces.add(getAvailablePieces(placement).toLowerCase().charAt(b)
                                    + newCoordinates + direction);
                        }
                    }
                }
            }
        }
        return possiblePieces;
    }


    /**
     * Determine whether a placement string is well-formed without taking alphabetical order into consideration.
     *
     * @param placement A string describing a placement of one or more pieces
     * @return True if the placement is well-formed
     */
    public static boolean isPlacementWellFormedNoOrder(String placement) {

        //check length
        if (placement.length() == 0 || placement.length() % 4 != 0)
            return false;

        //check all pieces are well-formed
        for (int a = 0; a < placement.length(); a += 4) {
            if (!isPiecePlacementWellFormed(placement.substring(a, a + 4)))
                return false;
        }

        // Checking no pieces used more than once
        for (int a = 0; a < placement.length(); a += 4) {
            for (int b = a + 4; b < placement.length(); b += 4) {
                if (placement.toUpperCase().charAt(a) == placement.toUpperCase().charAt(b))
                    return false;
            }
        }
        return true;
    }

    /**
     * Determine whether a placement string is valid without taking alphabetical order into consideration.
     *
     * @param placement A placement string
     * @return True if the placement sequence is valid disregarding order
     */
    public static boolean isPlacementValidNoOrder(String placement) {

        boolean condition = true;

        if (!isPlacementWellFormedNoOrder(placement)) {
            return false;
        }

        List<String> list = PiecesOrientation.getOccupiedPositionBoard(placement);

        for (int i = 0; i < list.size() && condition; i++) {

            String a = list.get(i);

            condition = a.length() == 2
                    && Character.isDigit(a.charAt(0))
                    && "01234".indexOf(a.charAt(1)) != -1
                    && list.indexOf(a) == list.lastIndexOf(a);

        }
        return condition;
    }
//===================================================================================

    /**
     * Return the solution to a particular challenge.
     *
     * @param challenge A challenge string.
     * @return A placement string describing the encoding of the solution to
     * the challenge.
     */
    public static String getSolution(String challenge){

        for (Games game : Games.SOLUTIONS) {
            if (challenge.equals(game.objective))
                return game.placement;
        }

        return "";

    } // FIXME Task 9: determine the solution to the game, given a particular challenge

}