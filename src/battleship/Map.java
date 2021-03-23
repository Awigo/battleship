package battleship;

import java.util.ArrayList;
import java.util.Scanner;

public class Map {
    String[][] map;
    ArrayList<Ship> ships;

    Map() {
        this.map = new String[11][11];
        this.ships = new ArrayList<>();
    }

    public void fill() {
        map[0][0] = " ";

        //from 1 to 10
        for (int i = 1; i < 11; i++) {
            map[0][i] = Integer.toString(i);
        }

        //from A to J
        map[1][0] = "A";
        map[2][0] = "B";
        map[3][0] = "C";
        map[4][0] = "D";
        map[5][0] = "E";
        map[6][0] = "F";
        map[7][0] = "G";
        map[8][0] = "H";
        map[9][0] = "I";
        map[10][0] = "J";

        //battle field
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                map[i][j] = "~";
            }
        }
    }

    public void display() {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                System.out.print(" " + map[i][j]);
            }
            System.out.println();
        }
    }

    public void displayWithFog() {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (map[i][j].equals("O")) {
                    System.out.print(" ~");
                } else {
                    System.out.print(" " + map[i][j]);
                }
            }
            System.out.println();
        }
    }

    public void placeShips() {
        Scanner scanner = new Scanner(System.in);
        int shipLength = 5;
        int counter = 0;
        boolean shipsArePlaced = false;
        System.out.println("Enter the coordinates of the Aircraft Carrier (5 cells):\n");

        while (!shipsArePlaced) {
            String c1 = scanner.next();
            String c2 = scanner.next();
            if (this.placeShip(c1, c2, shipLength) == 1) {
                this.display();
                counter++;
                switch (counter) {
                    case 1:
                        System.out.println("Enter the coordinates of the Battleship (4 cells):\n");
                        shipLength = 4;
                        break;
                    case 2:
                        System.out.println("Enter the coordinates of the Submarine (3 cells):\n");
                        shipLength = 3;
                        break;
                    case 3:
                        System.out.println("Enter the coordinates of the Cruiser (3 cells):\n");
                        break;
                    case 4:
                        System.out.println("Enter the coordinates of the Destroyer (2 cells)\n");
                        shipLength = 2;
                        break;
                    default:
                        shipsArePlaced = true;
                        System.out.println("Press Enter and pass the move to another player\n");
                        break;
                }
            }
        }
    }

    private int placeShip(String c1, String c2, int shipLength) {
        char c1X;
        int c1Y;

        char c2X;
        int c2Y;

        try {
            c1X = c1.toUpperCase().charAt(0);
            c1Y = Integer.parseInt(c1.toUpperCase().substring(1));

            c2X = c2.toUpperCase().charAt(0);
            c2Y = Integer.parseInt(c2.toUpperCase().substring(1));
        } catch (Exception e) {
            System.out.println("Error! Enter coordinates again!");
            return -1;
        }


        //if coordinates are not in one line -> throw error
        if (c1X != c2X && c1Y != c2Y || c1X == c2X && c1Y == c2Y) {
            System.out.println("Error! Ships should be in the same line! Try again:\n");
            return -1;
        }

        //if coordinates are out of map
        if (c1X < 'A' || c1X > 'J' || c2X < 'A' || c2X > 'J' || c1Y < 0 || c1Y > 10 || c2Y < 0 || c2Y > 10) {
            System.out.println("Error! Coordinates should be as A-J and 1-10! Try again:\n");
            return -1;
        }

        //if coordinates are reverse -> reverse them
        if (c1X > c2X || c1Y > c2Y) {
            char bufX = c1X;
            int bufY = c1Y;
            String buf = c1;
            c1 = c2;
            c2 = buf;
            c1X = c2X;
            c1Y = c2Y;
            c2X = bufX;
            c2Y = bufY;
        }

        //vertical (y = const) eg. F3 F7
        //if length of aircraft is different than coordinates --> throw an error
        if (c1X == c2X) { //Y to 1,2,3 X to A,B,C
            if ((c2Y - c1Y + 1) != shipLength) {
                System.out.println("Error! Ship should have exactly " + shipLength + " length! Now it has " + (c2Y - c1Y + 1) + "! Try again:\n");
                return -1;
            }
            int cor1XAsNumber = toNumber(c1X);
            for (int i = cor1XAsNumber - 1; i <= cor1XAsNumber + 1; i++) {
                for (int j = c1Y - 1; j <= c1Y + shipLength; j++) {
                    if (i < 1 || i > 10 || j < 1 || j > 10) {
                        continue;
                    }
                    if ("O".equals(map[i][j]) || "X".equals(map[i][j]) || "M".equals(map[i][j])) {
                        System.out.println("Error! You placed it too close to another one. Try again:\n");
                        return -1;
                    }
                }
            }
            for (int i = c1Y; i < c1Y + shipLength; i++) {
                map[toNumber(c1X)][i] = "O";
            }
        } else {
            if ((c2X - c1X + 1) != shipLength) {
                System.out.println("Error! Ship should have exactly " + shipLength + " length! Now it has " + (c2X - c1X + 1) + "! Try again:\n");
                return -1;
            }
            int cor1XAsNumber = toNumber(c1X);
            for (int i = cor1XAsNumber - 1; i <= cor1XAsNumber + shipLength; i++) {
                for (int j = c1Y - 1; j <= c1Y + 1; j++) {
                    if (i < 1 || i > 10 || j < 1 || j > 10) {
                        continue;
                    }
                    if ("O".equals(map[i][j]) || "X".equals(map[i][j]) || "M".equals(map[i][j])) {
                        System.out.println("Error! You placed it too close to another one. Try again:\n");
                        return -1;
                    }
                }
            }
            for (int i = toNumber(c1X); i < toNumber(c1X) + shipLength; i++) {
                map[i][c1Y] = "O";
            }
        }
        ships.add(new Ship(c1, c2, shipLength));
        return 1;

    }

    private int toNumber(char c) {
        switch (c) {
            case 'A':
                return 1;
            case 'B':
                return 2;
            case 'C':
                return 3;
            case 'D':
                return 4;
            case 'E':
                return 5;
            case 'F':
                return 6;
            case 'G':
                return 7;
            case 'H':
                return 8;
            case 'I':
                return 9;
            case 'J':
                return 10;
            default:
                return -1;
        }
    }

    public void shoot(String c1) {
        int c1X = toNumber(c1.charAt(0));
        int c1Y = Integer.parseInt(c1.substring(1));

        if (c1X < 1 || c1X > 10 || c1Y < 1 || c1Y > 10) {
            System.out.println("Error! You entered the wrong coordinates! Try again:\n");
            return;
        }

        switch (map[c1X][c1Y]) {
            case "O":
                map[c1X][c1Y] = "X";
                Ship ship = deleteCell(c1);
                ships.remove(ship);
                if (ships.isEmpty()) {
                    System.out.println("You sank the last ship. You won. Congratulations!");
                } else if (ship != null) {
                    System.out.println("You sank a ship! Specify a new target:\n");
                } else {
                    System.out.println("You hit a ship!\n");
                }
                break;
            case "~":
                map[c1X][c1Y] = "M";
                System.out.println("You missed!\n");
                break;
            case "X":
                System.out.println("Error! You have already shoot here!\n");
                break;
        }

    }

    public void promptEnterKey() {
        System.out.println("Press \"ENTER\" to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    Ship deleteCell(String cell) {
        for (Ship ship : ships) {
            if (ship.positions.contains(cell)) {
                ship.positions.remove(cell);
                if (ship.positions.isEmpty()) {
                    return ship;
                }
            }
        }
        return null;
    }
}
