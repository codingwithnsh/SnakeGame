# Snake Game in Java

A simple Snake Game developed in Java with optional MySQL database support for saving high scores.  
If you don't configure MySQL, you can still play the game, but scores won't be recorded.

## Features
- Classic Snake mechanics: the snake grows with each apple eaten.
- Adjustable speed for various difficulty levels (configured in code).
- Optional database integration for storing high scores.

## Requirements
- Java 8 or higher.
- MySQL (optional) for score persistence.

## How It Works
1. The snake starts at a fixed size.  
2. Apple spawns randomly on the board.  
3. Each time the snake eats an apple, it grows in length and your score increases.  
4. Hitting a wall or colliding with your own snake ends the game.  
5. If MySQL is configured, the final score is saved in the database.

## License
This project is under the MIT License. Feel free to use or modify it as you wish.
