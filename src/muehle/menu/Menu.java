package muehle.menu;

import java.util.Scanner;
import javax.swing.*;
import java.awt.GridLayout;


public class Menu {
    private boolean inTerminal;
    private Scanner scanner;

    public Menu() {
        this.inTerminal = true;
        this.scanner = new Scanner(System.in);
    }

    public Menu(boolean inTerminal) {
        this.inTerminal = inTerminal;
        if (!inTerminal) {
            javax.swing.SwingUtilities.invokeLater(this::createSwingMenu);
        } else {
            this.scanner = new Scanner(System.in);
        }
    }

    public void startMenu() {
        if (inTerminal) {
            showTerminalMenu();
        } else {
            createSwingMenu();
        }
    }

    private void showTerminalMenu() {
        boolean running = true;
        while (running) {
            displayOptions();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Starting new game...");
                    break;
                case 2:
                    System.out.println("Loading game...");
                    break;
                case 3:
                    System.out.println("Activating debug mode...");
                    break;
                case 4:
                    System.out.println("Exiting program...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
        }
        scanner.close();
    }

    private void displayOptions() {
        System.out.println("----- Main Menu -----");
        System.out.println("1. Start new game");
        System.out.println("2. Load game");
        System.out.println("3. Activate debug mode");
        System.out.println("4. Exit");
        System.out.print("Your choice: ");
    }

    private void createSwingMenu() {
        JFrame frame = new JFrame("Mühle - Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));

        JButton newGameButton = new JButton("Start new game");
        JButton loadGameButton = new JButton("Load game");
        JButton debugModeButton = new JButton("Activate debug mode");
        JButton exitButton = new JButton("Exit");

        panel.add(newGameButton);
        panel.add(loadGameButton);
        panel.add(debugModeButton);
        panel.add(exitButton);

        frame.add(panel);

        exitButton.addActionListener(e -> System.exit(0));
        newGameButton.addActionListener(e -> System.out.println("Starting new game..."));
        loadGameButton.addActionListener(e -> System.out.println("Loading game..."));
        debugModeButton.addActionListener(e -> System.out.println("Activating debug mode..."));

        frame.setVisible(true);
    }
}
