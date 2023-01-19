package battleship;

import java.util.Scanner;

public class Main {
    private final static Scanner SCANNER = new Scanner(System.in);
    private final static int GRID_SIZE = 10;
    private final static ShipType[] requiredShips = ShipType.values();
    private final static GameField playerField = new GameField(GRID_SIZE);
    private final static GameField opponentField = new GameField(GRID_SIZE);

    enum Player {
        PLAYER1("Player 1", playerField), PLAYER2("Player 2", opponentField);
        final String name;
        final GameField field;
        int shipsLeft;

        Player(String name, GameField field) {
            this.name = name;
            this.field = field;
            this.shipsLeft = requiredShips.length;
        }

        void sinkShip() {
            shipsLeft--;
        }
    }

    public static int[] parsePosition(String position) {
        return new int[]{
            (int) (position.toUpperCase().charAt(0)) - 64,
            Integer.parseInt(position.substring(1))
        };
    }

    private static void validateInput(int[] start, int[] end, ShipType type)
        throws Exception {
        int[] coordinates = new int[]{start[0], start[1], end[0], end[1]};
        for (int c : coordinates) {
            if (c < 1 || c > 10) {
                throw new Exception("Error: coordinates must be in the range A1 - J10");
            }
        }

        if (start[0] != end[0] && start[1] != end[1]) {
            throw new Exception("Error: coordinates must have the same row letter " +
                                "or the same column number");
        }

        int rowSize = Math.abs(start[0] - end[0]) + 1;
        int colSize = Math.abs(start[1] - end[1]) + 1;
        if ((rowSize == 1 && colSize != type.getSize()) ||
            (colSize == 1 && rowSize != type.getSize())) {
            int sizeProvided = Math.max(colSize, rowSize);
            throw new Exception("Error: a " + type + " requires " + type.getSize() +
                                " cells, and the coordinates provided take up " + sizeProvided
            );
        }
    }

    private static void prepareField(GameField field) {
        String[] endPoints;
        int[] start;
        int[] end;
        boolean valid;

        for (ShipType type : requiredShips) {
            valid = false;

            System.out.printf(
                "Enter the coordinates of the %s (%d cells):\n\n",
                type.getName(), type.getSize()
            );

            do {
                try {
                    endPoints = SCANNER.nextLine().trim().split(" ");
                    start = parsePosition(endPoints[0]);
                    end = parsePosition(endPoints[1]);

                    validateInput(start, end, type);
                    field.addShip(new Ship(type, start, end));
                    System.out.println(field);
                    valid = true;
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("\nError: you must provide exactly " +
                                       "two coordinates separated by a space.");
                } catch (NumberFormatException e) {
                    System.out.println("\nError: each coordinate must contain " +
                                       "a single letter (A - J) and a digit (0 - 9).");
                } catch (Exception e) {
                    System.out.println("\n" + e.getMessage());
                }
            } while (!valid);
        }
    }

    private static void printFields(GameField top, GameField bottom) {
        System.out.println(top.hidden());
        System.out.println("---------------------");
        System.out.println(bottom);
    }

    public static void main(String[] args) {
        Player player = Player.PLAYER1;
        int[] shotPosition;
        LocationDetail shotInfo;
        String result;
        GameField activeField;
        GameField hiddenField;

        for (Player t : Player.values()) {
            System.out.println(t.name + ", place your ships on the game field");
            System.out.println(t.field);
            prepareField(t.field);

            System.out.println("Press Enter and pass the move to another player\n...");
            SCANNER.nextLine();
        }


        do {
            activeField = player.equals(Player.PLAYER1) ? Player.PLAYER1.field : Player.PLAYER2.field;
            hiddenField = activeField.equals(Player.PLAYER1.field) ? Player.PLAYER2.field : Player.PLAYER1.field;
            printFields(hiddenField, activeField);

            System.out.println(player.name + ", it's your turn:");
            try {
                shotPosition = parsePosition(SCANNER.nextLine());
                shotInfo = hiddenField.takeAShot(shotPosition);

                if (shotInfo.ship == null) {
                    result = shotInfo.symbol.equals(Symbol.HIT)
                        ? "You hit a ship! Try again:"
                        : "You missed. Try again:";
                } else {
                    switch (shotInfo.symbol) {
                        case HIT -> {
                            if (shotInfo.ship.isSunk()) {
                                player.sinkShip();
                                result = "You sank a ship! Specify a new target:";
                            } else {
                                result = "You hit a ship! Try again:";
                            }
                        }
                        case MISS -> result = "You missed. Try again:";
                        default -> throw new Exception("An unknown error has occurred");
                    }
                }

                if (player.shipsLeft > 0) {
                    System.out.println(result);
                }

                player = player.equals(Player.PLAYER1) ? Player.PLAYER2 : Player.PLAYER1;

            } catch (NumberFormatException e) {
                System.out.println("\nError: a coordinate must contain " +
                                   "a single letter (A - J) and a digit (0 - 9).");
            } catch (Exception e) {
                System.out.println("\n" + e.getMessage());
            }

        } while (Player.PLAYER1.shipsLeft > 0 && Player.PLAYER2.shipsLeft > 0);

        Player winner = Player.PLAYER1.shipsLeft == 0 ? Player.PLAYER1 : Player.PLAYER2;
        System.out.println(winner.name + " sank the last ship. Congratulations!");
    }
}
