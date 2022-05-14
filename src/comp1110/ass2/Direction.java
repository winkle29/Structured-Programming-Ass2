package comp1110.ass2;

public enum Direction {

    NORTH('N'), EAST('E'), SOUTH('S'), WEST('W');

    final private char direction;

    Direction(char direction) { this.direction = direction; }


    /**
     * Given the upper case character ('N','E','S','W'),
     * return the Direction associated with the character.
     *
     * @param initial a char value representing the 'Direction' enum
     * @return the 'Direction' associated with the char.
     */
    public static Direction fromChar(char initial) {

        switch (initial) {

            case ('N'):
                return NORTH;

            case ('E'):
                return EAST;

            case ('S'):
                return SOUTH;

            case ('W'):
                return WEST;

        }

        return null;
    }


    /**
     * Return the single character associated with a `Direction`, which is
     * the first character of the direction name, as an upper case character
     * ('N', 'E', 'S', 'W')
     *
     * @return The first character of the name of the direction
     */
    public char toChar() {

        return direction;

    }


}
