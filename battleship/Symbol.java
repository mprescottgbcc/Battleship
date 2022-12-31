package battleship;

enum Symbol {
    FOG('~'), OK('O'), HIT('X'), MISS('M');
    private final char symbol;

    Symbol(char c) {
        symbol = c;
    }

    char getSymbol() {
        return symbol;
    }
}
