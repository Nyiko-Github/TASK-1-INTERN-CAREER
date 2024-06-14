import javax.swing.*;
import java.awt.*;

/**
 * The type Main.
 */
public class Main {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        final JFrame frame = new JFrame("Snake Game");
        frame.setSize(WIDTH, HEIGHT);
        SnakeGame game = new SnakeGame(WIDTH, HEIGHT);
        frame.add(game);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.pack();
        game.startGame();

    }
}