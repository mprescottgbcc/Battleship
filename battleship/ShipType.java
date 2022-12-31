package battleship;

enum ShipType {
    CARRIER(5), BATTLESHIP(4), SUBMARINE(3), CRUISER(3), DESTROYER(2);
    private final int size;

    ShipType(int i) {
        size = i;
    }

    public int getSize() {
        return size;
    }
}
