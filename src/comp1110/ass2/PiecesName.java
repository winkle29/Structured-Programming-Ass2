package comp1110.ass2;

public enum PiecesName {

    BLUE('B'),
    GREEN('G'),
    INDIGO('I'),
    LIME_GREEN('L'),
    NAVY_BLUE('N'),
    ORANGE('O'),
    PINK('P'),
    RED('R'),
    SKY_BLUE('S'),
    YElLOW('Y');


    private final char symbol;

    PiecesName(char symbol) {
        this.symbol = symbol;

    }

    /**
     * Get pieces name from char
     */
    public static PiecesName getName(char character) {

        switch (character) {

            case ('B'):
                return BLUE;

            case ('G'):
                return GREEN;

            case ('I'):
                return INDIGO;

            case ('L'):
                return LIME_GREEN;

            case ('N'):
                return NAVY_BLUE;

            case ('O'):
                return ORANGE;

            case ('P'):
                return PINK;

            case ('R'):
                return RED;

            case ('S'):
                return SKY_BLUE;

            case ('Y'):
                return YElLOW;

        }

        return null;
    }


    /**
     * Get the name of the piece from a string of piece's current state.
     *
     * @param piecePlacement a string of piece's current state
     * @return character of the piece
     */
    public static PiecesName fromChar(String piecePlacement) {
        char colour;
        colour = piecePlacement.toUpperCase().charAt(0);
        return getName(colour);

    }

}

