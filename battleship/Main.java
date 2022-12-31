package battleship;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private final static Scanner SCANNER = new Scanner(System.in);
    private final static int GRID_SIZE = 10;

    public static void main(String[] args) {
        GameField playerField = new GameField(GRID_SIZE);
        String[] positions;

        System.out.println(playerField);
        System.out.println("Enter the coordinates of the Aircraft Carrier (5 cells):");
        positions = SCANNER.nextLine().split(" ");
        System.out.println((int)positions[0].charAt(0) - 64);
        System.out.println((int)positions[1].charAt(0) - 64);
    }
}
