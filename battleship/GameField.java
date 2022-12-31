package battleship;

public class GameField {
    private final Symbol[][] grid;

    public GameField(int size) {
        grid = new Symbol[size][size];

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                grid[row][col] = Symbol.FOG;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(" ");
        char rowLetter = 'A';

        for (int i = 1; i <= grid.length; i++) {
            sb.append(" ").append(i);
        }

        sb.append("\n");

        for (Symbol[] row : grid) {
            sb.append(rowLetter);
            for (Symbol symbol : row) {
                sb.append(" ").append(symbol.getSymbol());
            }
            sb.append("\n");
            rowLetter++;
        }

        return sb.toString();
    }
}


