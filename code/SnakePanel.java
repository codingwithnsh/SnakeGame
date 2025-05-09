package com.xxx.xxx; //replace the package came with yours


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Core game panel handling the snake's movement, collisions, 
 * and interactions with the database.
 */
public class SnakePanel extends JPanel implements ActionListener, KeyListener {

    private static final int PANEL_WIDTH = 600;
    private static final int PANEL_HEIGHT = 600;
    private static final int UNIT_SIZE = 20;      // size of each unit or pixel block
    private static final int GAME_UNITS = (PANEL_WIDTH * PANEL_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    private static final int DELAY = 150;         // timer delay (speed)

    private final int x[] = new int[GAME_UNITS];
    private final int y[] = new int[GAME_UNITS];

    private int bodyParts = 3;                    // initial body size
    private int applesEaten;
    private int appleX;
    private int appleY;
    private char direction = 'R';                 // initial movement: R=right, L=left, U=up, D=down
    private boolean running = false;

    private Timer timer;
    private Random random;
    private DatabaseManager dbManager;

    public SnakePanel() {
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);
        random = new Random();

        // Initialize database manager (adjust credentials as needed)
        dbManager = new DatabaseManager();

        startGame();
    }

    private void startGame() {
        spawnApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    /**
     * Spawn an apple at a random position within the game field.
     */
    private void spawnApple() {
        appleX = random.nextInt(PANEL_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        appleY = random.nextInt(PANEL_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
    }

    /**
     * Game's painting logic.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    /**
     * Draw the snake and apple on screen if the game is running,
     * or show "Game Over" otherwise.
     */
    private void draw(Graphics g) {
        if (running) {
            // Draw the apple
            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            // Draw the snake
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    // Head of snake
                    g.setColor(Color.GREEN);
                } else {
                    // Body of snake
                    g.setColor(new Color(45, 180, 0));
                }
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }

            // Show current score
            g.setColor(Color.WHITE);
            g.setFont(new Font("Ink Free", Font.BOLD, 20));
            g.drawString("Score: " + applesEaten, 10, 20);

        } else {
            gameOver(g);
        }
    }

    /**
     * Handle the timer event: move the snake, check collisions, and repaint.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    /**
     * Move snake body in the direction specified by 'direction' char.
     */
    private void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    /**
     * Check whether the snake head collides with the apple.
     */
    private void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            spawnApple();
        }
    }

    /**
     * Check for any collisions with boundaries or snake's own body.
     */
    private void checkCollisions() {
        // Check if head collides with body
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i]) && i > 0) {
                running = false;
                break;
            }
        }

        // Check if head touches left border
        if (x[0] < 0) {
            running = false;
        }
        // Check if head touches right border
        if (x[0] >= PANEL_WIDTH) {
            running = false;
        }
        // Check if head touches top border
        if (y[0] < 0) {
            running = false;
        }
        // Check if head touches bottom border
        if (y[0] >= PANEL_HEIGHT) {
            running = false;
        }

        if (!running) {
            timer.stop();
        }
    }

    /**
     * Display game over message and show top scores from DB.
     */
    private void gameOver(Graphics g) {
        // Save the last score to DB
        dbManager.saveScore(applesEaten);

        // Display final score
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        g.drawString("Game Over!", (PANEL_WIDTH - 200) / 2, PANEL_HEIGHT / 2);

        // Display yourscore and top 5 scores
        g.setColor(Color.WHITE);
        g.setFont(new Font("Ink Free", Font.PLAIN, 20));
        g.drawString("Your Score: " + applesEaten,
                (PANEL_WIDTH - 140) / 2, (PANEL_HEIGHT / 2) + 40);

        // Retrieve top scores from DB
        List<Integer> topScores = dbManager.getTopScores(5);
        int yPosition = (PANEL_HEIGHT / 2) + 80;
        g.drawString("Top Scores:", (PANEL_WIDTH - 100) / 2, yPosition);
        yPosition += 25;
        for (int i = 0; i < topScores.size(); i++) {
            g.drawString((i + 1) + ": " + topScores.get(i),
                    (PANEL_WIDTH - 100) / 2, yPosition);
            yPosition += 25;
        }
    }

    /**
     * Process user keystrokes for snake movement.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (direction != 'R') {
                    direction = 'L';
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (direction != 'L') {
                    direction = 'R';
                }
                break;
            case KeyEvent.VK_UP:
                if (direction != 'D') {
                    direction = 'U';
                }
                break;
            case KeyEvent.VK_DOWN:
                if (direction != 'U') {
                    direction = 'D';
                }
                break;
            default:
                break;
        }
    }

    /**
     * Unused, but required by KeyListener interface.
     */
    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void keyTyped(KeyEvent e) { }
}
