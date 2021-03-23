package battleship;

import java.util.ArrayList;

public class Ship {
    ArrayList<String> positions;

    Ship(String c1, String c2, int shipLength) {
        positions = new ArrayList<>();
        StringBuilder c1Builder = new StringBuilder(c1);
        if (c1.charAt(0) == c2.charAt(0)) { //eg A1, A5, 5
            for (int i = 0; i < shipLength; i++) {
                positions.add(c1Builder.toString());
                c1Builder.replace(1, c1.length(), Integer.toString(Integer.parseInt(c1Builder.substring(1)) + 1));
            }
        } else { //eg A1, E1, 5
            for (int i = 0; i < shipLength; i++) {
                positions.add(c1Builder.toString());
                c1Builder.replace(0, 1, Character.toString(c1Builder.charAt(0) + 1));
            }
        }
    }
}
