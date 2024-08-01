import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.event.KeyEvent;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Snake game test.
 */
public class SnakeGameTest {
    private SnakeGame game;

    /**
     * Sets up.
     */
    @BeforeEach
    public void setUp() {
        game = new SnakeGame(400, 400);
        game.startGame();
    }

    /**
     * Test initial snake position.
     */
    @Test
    public void testInitialSnakePosition() {
        assertEquals(1, game.getSnake().size(), "Initial snake size should be 1");
        SnakeGame.GamePoint head = game.getSnake().get(0);
        assertEquals(200, head.getX(), "Initial snake head x position should be 200");
        assertEquals(200, head.getY(), "Initial snake head y position should be 200");
    }

    /**
     * Test snake movement.
     */
    @Test
    public void testSnakeMovement() {
        // Ensure game is started
        game.startGame();

        // Initial direction should be RIGHT
        assertEquals(SnakeGame.Direction.RIGHT, game.getDirection(), "Initial direction should be RIGHT");

        // Move up
        game.handleKeyEvent(KeyEvent.VK_UP);
        game.moveSnake();
        assertEquals(SnakeGame.Direction.UP, game.getDirection(), "Snake direction should be UP after moving up");

        // Move left
        game.handleKeyEvent(KeyEvent.VK_LEFT);
        game.moveSnake();
        assertEquals(SnakeGame.Direction.LEFT, game.getDirection(), "Snake direction should be LEFT after moving left");

        // Move down
        game.handleKeyEvent(KeyEvent.VK_DOWN);
        game.moveSnake();
        assertEquals(SnakeGame.Direction.DOWN, game.getDirection(), "Snake direction should be DOWN after moving down");

        // Move right
        game.handleKeyEvent(KeyEvent.VK_RIGHT);
        game.moveSnake();
        assertEquals(SnakeGame.Direction.RIGHT, game.getDirection(), "Snake direction should be RIGHT after moving right");
    }


    /**
     * Test food generation.
     */
    @Test
    public void testFoodGeneration() {
        game.generateFood();
        SnakeGame.GamePoint food = game.getFood();
        assertNotNull(food, "Food should be generated");
        assertFalse(game.getSnake().contains(food), "Food should not be on the snake");
    }

    /**
     * Test collision detection.
     */
    @Test
    public void testCollisionDetection() {
        // Move the snake to collide with itself
        game.handleKeyEvent(KeyEvent.VK_DOWN);
        for (int i = 0; i < 10; i++) {
            game.moveSnake();
        }
        game.handleKeyEvent(KeyEvent.VK_LEFT);
        for (int i = 0; i < 10; i++) {
            game.moveSnake();
        }
        game.handleKeyEvent(KeyEvent.VK_UP);
        for (int i = 0; i < 10; i++) {
            game.moveSnake();
        }
        game.handleKeyEvent(KeyEvent.VK_RIGHT);
        for (int i = 0; i < 10; i++) {
            game.moveSnake();
        }
        assertTrue(game.checkCollision(), "Collision should be detected");
    }
}
