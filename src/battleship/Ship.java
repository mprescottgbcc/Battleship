package battleship;

import java.util.ArrayList;

public class Ship {
    private final int[] start;
    private final int[] end;
    private final ArrayList<String> hits;
    private final ShipType shipType;

    public Ship(ShipType type, int[] ints1, int[] ints2) {
        shipType = type;
        if (ints1[0] == ints2[0]) {
            //same row (horizontal)
            start = (ints1[1] < ints2[1]) ? ints1 : ints2;
        } else {
            //same column (vertical)
            start = (ints1[0] < ints2[0]) ? ints1 : ints2;
        }
        end = (start == ints1) ? ints2 : ints1;
        hits = new ArrayList<>();
    }

    public int[] getStart() {
        return start;
    }

    public int[] getEnd() {
        return end;
    }

    public void addHit(int[] position) {
        String cell = (char)('A' + position[0]) + "" + (position[1] + 1);
        if (!hits.contains(cell)) {
            hits.add(cell);
        }
    }

    public boolean isSunk() {
        return shipType.getSize() == hits.size();
    }
}
