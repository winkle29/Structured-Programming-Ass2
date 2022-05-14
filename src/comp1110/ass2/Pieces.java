package comp1110.ass2;

import java.util.ArrayList;

public class Pieces {
//=============Author: Prabhjot Kaur Dhawan===============================================
    /**
     * This method returns the orientation of the piece in the form of integer
     * North=0
     * East=1
     * South=2
     * West=3
     * @return the orientation of this tile */
    public static int getOrientation(String piece) {
        char or =piece.charAt(3);
        int result=0;
        switch(or){
            case 'N':{
                result=0;
                break;
            }
            case 'E':{
                result=1;
                break;
            }
            case 'S':{
                result=2;
                break;
            }
            case 'W':{
                result=3;
                break;
            }

        }

        return result; }

    /**
     * This method converts given placement into array of string pieces placement
     * @param placement game state string
     * @return Array of piece placement strings
     */
    public static ArrayList<String> getPieces(String placement) {

        if (placement.length()%4 !=0){
            return null;
        }

        ArrayList<String> result = new ArrayList<String>(placement.length() / 4);
        for (int start = 0, stop = 4, p = 0; start < placement.length() - 3 && stop <= placement.length()
                && p <= placement.length() / 4; start = start + 4, stop = stop + 4, p++) {
            result.add(placement.substring(start, stop));

        }
        return result;
    }
//============================================================================

    public static void main(String[] args) {
        Pieces Try = new Pieces();
        ArrayList<String> check;
        check= getPieces("b63Sn03Sy31S");
        System.out.println(check);

    }

 //=============== Author: Nik Nur Aisyah binti Amran ========================
    /**
     * Given the current placement and the full placement, return the placement of next piece
     * for hint.
     * @param currentPlacement of pieces on board
     * @param fullPlacement if all pieces are on board
     * @return next piece
     */
    public static String getNextPiece(String currentPlacement, String fullPlacement) {

        // extreme cases
        if (currentPlacement == null || fullPlacement == null)
            return null;
        if (fullPlacement.length() == 0)
            return null;
        if (!FitGame.isPlacementWellFormed(fullPlacement))
            return null;
        if (currentPlacement.equals(fullPlacement))
            return null;

        String piece = "";
        String compare = PiecesOrientation.getOccupiedColours(currentPlacement).toUpperCase();

        int index = 0;


        for (int i = 0; i < fullPlacement.length() ; i += 4) {
            String sub = fullPlacement.substring(i, i+4);
            if (!compare.contains(String.valueOf(sub.charAt(0)))) {
                piece = sub;
                index = i;
                break;
            }
        }

        if (piece.equals(""))
            return "";

        if (FitGame.isPlacementValidNoOrder(currentPlacement.concat(piece)))
            return piece;
        else
            return getNextPiece(currentPlacement, fullPlacement.substring(0, index).
                    concat(fullPlacement.substring(index+4)));

    }
//============================================================================
}
