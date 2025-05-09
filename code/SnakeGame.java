package com.xxx.xxx; //replace the package came with yours

import javax.swing.JFrame;
import java.awt.EventQueue;

/**
 * Main class to start the Snake Game.
 * 
 * Requirements:
 * 1. Java (for core game logic and GUI).
 * 2. MySQL database to store high scores.
 * 3. JDBC for connecting Java with MySQL.
 *
 * To run:
 * 1. Update the database credentials in DatabaseManager.java.
 * 2. Create a "scores" table in your MySQL database as per instructions in DatabaseManager.java.
 * 3. Compile and run SnakeGame.java.
 */
public class SnakeGame extends JFrame {

    public SnakeGame() {
        initUI();
    }

    private void initUI() {
        // Initialize the panel that contains the game logic
        add(new SnakePanel());
        pack();

        setTitle("Snake Game in Java");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            SnakeGame game = new SnakeGame();
            game.setVisible(true);
        });
    }
}
