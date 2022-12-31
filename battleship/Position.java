package battleship;

public class Position {
    Coordinate start;
    Coordinate end;
    Symbol[] status;

    private static class Coordinate {
        int column;
        int row;
    }
}
