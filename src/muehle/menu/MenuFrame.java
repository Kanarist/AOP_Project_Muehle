 package muehle.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuFrame extends JFrame {

	private static final long serialVersionUID = -7158389682750440270L;

	public MenuFrame() {
        setTitle("Mühle - Hauptmenü");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // Center Window

        // Create Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));

        // Create Buttons
        JButton startGameButton = new JButton("Neues Spiel starten");
        JButton loadGameButton = new JButton("Spiel laden");
        JButton exitButton = new JButton("Spiel beenden");

        // Add Buttons to Panel
        panel.add(startGameButton);
        panel.add(loadGameButton);
        panel.add(exitButton);

        add(panel, BorderLayout.CENTER);

        // ActionListener for NewGame
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Neues Spiel starten wird implementiert...");
                // Implement NewGame here
            }
        });

        // ActionListener for LoadGame
        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Spiel laden wird implementiert...");
                // Implement LoadGame here
            }
        });

        // ActionListener for Exit Program
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Set Window visible
        setVisible(true);
    }

    public static void main(String[] args) {
        // Start Menu
        SwingUtilities.invokeLater(() -> new MenuFrame());
    }
}
