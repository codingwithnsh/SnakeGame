# Step-by-Step Installation and Usage Guide

1. Install Java:
   - Make sure Java (8 or higher) is installed and accessible in your system's PATH.

2. Clone the Repository:
   - Clone the project or download it as a ZIP.

3. Optional: Configure MySQL for High Scores
   - Set up a MySQL database (e.g., named snake_db).
   - Create a "scores" table if it doesn’t exist (the application code can do this automatically).
   - In DatabaseManager.java (or wherever your DB credentials are stored):
     - Update the DB_URL, DB_USER, and DB_PASS fields to match your environment.

4. Compile the Project:
   - Compile the Java source files (e.g., javac *.java if done manually).
   - Alternatively, import into an IDE like IntelliJ or Eclipse, then build the project.

5. Run the Game:
   - Execute the main class (e.g., java com.example.snakegame.SnakeGame).
   - Use arrow keys (↑, ↓, ←, →) to control the snake.

6. Verify Scores:
   - If MySQL is configured and running, your score will be stored in the database after each game.
   - If not configured, the game remains playable but scores aren’t stored.

7. Adjust Game Settings:
   - Modify speed, window size, or other parameters in the code to customize difficulty.

8. Enjoy:
   - The Snake Game is now ready to play. Gather apples, avoid hazards, and aim for a high score!
