package muehle.main;

import muehle.menu.Menu;

public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu(); // Start in terminal
        // Or:
        // Menu menu = new Menu(false); // Start with Swing
        menu.startMenu();
    }
}
