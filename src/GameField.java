public class GameField {
    private final LocationDetail[][] grid;

    public GameField(int size) {
        grid = new LocationDetail[size][size];

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                grid[row][col] = new LocationDetail(null, Symbol.FOG);
            }
        }
    }

    boolean isNotAvailable(int row, int col) {
        try {
            return !grid[row - 1][col - 1].symbol.equals(Symbol.FOG);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    void updateGrid(Ship ship) {
        int row;
        int col;
        if (ship.getStart()[0] == ship.getEnd()[0]) {
            row = ship.getStart()[0] - 1;
            for (col = ship.getStart()[1] - 1; col < ship.getEnd()[1]; col++) {
                grid[row][col] = new LocationDetail(ship, Symbol.OK);
            }
        } else {
            col = ship.getStart()[1] - 1;
            for (row = ship.getStart()[0] - 1; row < ship.getEnd()[0]; row++) {
                grid[row][col] = new LocationDetail(ship, Symbol.OK);
            }
        }
    }

    void checkBoundary(int[] start, int[] end) throws Exception {
        boolean isVertical = start[1] == end[1];

        if (isVertical) {
            //Different rows, same column
            int firstRow = start[0];
            int lastRow = end[0];
            int col = end[1];

            for (int row = firstRow; row <= lastRow; row++) {
                //check ship spaces
                if (isNotAvailable(row, col)) {
                    throw new Exception("Error 1: there is another ship at that location");
                }

                //check space to the left
                if (isNotAvailable(row, col - 1)) {
                    throw new Exception("Error 2: there is another ship too close to the left");
                }

                //check space to the right
                if (isNotAvailable(row, col + 1)) {
                    throw new Exception("Error 3: there is another ship too close to the right");
                }
            }

            //check single spot above
            if (isNotAvailable(firstRow - 1, col)) {
                throw new Exception("Error 4: there is another ship too close above");
            }

            //check single spot below
            if (isNotAvailable(lastRow + 1, col)) {
                throw new Exception("Error 5: there is another ship too close below");
            }

        } else {
            //ship is horizontal (same row, different columns
            int firstCol = start[1];
            int lastCol = end[1];
            int row = start[0];

            //check ship spaces
            for (int col = firstCol; col <= lastCol; col++) {
                //check ship spaces
                if (isNotAvailable(row, col)) {
                    throw new Exception("Error 6: there is another at that location");
                }

                //check space above
                if (isNotAvailable(row - 1, col)) {
                    throw new Exception("Error 7: there is another ship too close above");
                }

                //check space below
                if (isNotAvailable(row + 1, col)) {
                    throw new Exception("Error 8: there is another ship too close below");
                }
            }

            //check single space to the left
            if (isNotAvailable(row, firstCol - 1)) {
                throw new Exception("Error 9: there is another ship too close to the left");
            }

            //check single space to the right
            if (isNotAvailable(row, lastCol + 1)) {
                throw new Exception("Error 10: there is another ship too close to the right");
            }
        }
    }

    public void addShip(Ship ship) throws Exception {
        checkBoundary(ship.getStart(), ship.getEnd());
        updateGrid(ship);
    }

    public LocationDetail takeAShot(int[] position) {
        int row = position[0] - 1;
        int col = position[1] - 1;
        LocationDetail cell = grid[row][col];

        switch (cell.symbol) {
            case OK -> {
                cell.symbol = Symbol.HIT;
                cell.ship.addHit(new int[]{row, col});
            }
            case FOG -> cell.symbol = Symbol.MISS;
            case HIT, MISS -> {
                return new LocationDetail(null, cell.symbol);
            }
            default -> {
                return new LocationDetail(null, Symbol.MISS);
            }
        }

        return cell;
    }

    public String hidden() {
        StringBuilder sb = new StringBuilder("\n ");
        char rowLetter = 'A';

        for (int i = 1; i <= grid.length; i++) {
            sb.append(" ").append(i);
        }

        sb.append("\n");

        for (LocationDetail[] row : grid) {
            sb.append(rowLetter);
            for (LocationDetail detail : row) {
                sb.append(" ").append(Symbol.FOG);
            }
            sb.append("\n");
            rowLetter++;
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\n ");
        char rowLetter = 'A';

        for (int i = 1; i <= grid.length; i++) {
            sb.append(" ").append(i);
        }

        sb.append("\n");

        for (LocationDetail[] row : grid) {
            sb.append(rowLetter);
            for (LocationDetail detail : row) {
                sb.append(" ").append(detail.symbol);
            }
            sb.append("\n");
            rowLetter++;
        }

        return sb.toString();
    }
}
