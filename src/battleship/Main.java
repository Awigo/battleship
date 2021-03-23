package battleship;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Map map1 = new Map();
        map1.fill();
        System.out.println("Player 1, place your ships to the game field");
        map1.display();
        map1.placeShips();
        map1.promptEnterKey();

        Map map2 = new Map();
        map2.fill();
        System.out.println("Player 2, place your ships to the game field");
        map2.display();
        map2.placeShips();
        map2.promptEnterKey();

        boolean player1Turn = true;
        while (!map1.ships.isEmpty() || !map2.ships.isEmpty()) {
            if (player1Turn) {
                map2.displayWithFog();
                System.out.println("---------------------");
                map1.display();
                System.out.println("Player 1, it's your turn:\n");
                String c1 = scanner.next();
                map2.shoot(c1);
                map2.promptEnterKey();
                player1Turn = false;
            } else {
                map1.displayWithFog();
                System.out.println("---------------------");
                map2.display();
                System.out.println("Player 2, it's your turn:\n");
                String c1 = scanner.next();
                map1.shoot(c1);
                map1.promptEnterKey();
                player1Turn = true;
            }
        }

        if (map2.ships.isEmpty()) {
            System.out.println("Player 1 won! Congratulations!");
        } else {
            System.out.println("Player 2 won! Congratulations!");
        }
    }
}


