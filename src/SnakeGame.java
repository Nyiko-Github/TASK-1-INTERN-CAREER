import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.font.TextLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.*;

public class SnakeGame extends JPanel implements ActionListener {

    private final int width;
    private final int height;
    private final int cellSize;
    private final Random random = new Random();
    private static final int FRAME_RATE = 20;
    private boolean gameStarted = false;
    private boolean gameOver = false;
    private int highScore;
    private GamePoint food;
    private Direction direction = Direction.RIGHT;
    private Direction newDirection = Direction.RIGHT;
    private final Deque<GamePoint> snake = new ArrayDeque<>();

    public SnakeGame(final int width, final int height) {
        super();
        this.width = width;
        this.height = height;
        this.cellSize = width / (FRAME_RATE * 2);
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.black);
    }

    public void startGame() {
        resetGameData();
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        requestFocusInWindow();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent e) {
                handledKeyEvent(e.getKeyCode());
            }
        });
        new Timer(1000 / FRAME_RATE, this).start();
    }

    private void handledKeyEvent(final int keyCode) {
        if (!gameStarted) {
            if (keyCode == KeyEvent.VK_SPACE) {
                gameStarted = true;
            }
        } else if (!gameOver) {
            switch (keyCode) {
                case KeyEvent.VK_UP:
                    if (direction != Direction.DOWN) {
                        newDirection = Direction.UP;
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != Direction.UP) {
                        newDirection = Direction.DOWN;
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != Direction.LEFT) {
                        newDirection = Direction.RIGHT;
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if (direction != Direction.RIGHT) {
                        newDirection = Direction.LEFT;
                    }
                    break;
            }
        } else if (keyCode == KeyEvent.VK_SPACE) {
            gameStarted = false;
            gameOver = false;
            resetGameData();
        }
    }

    private void resetGameData() {
        snake.clear();
        snake.add(new GamePoint(width / 2, height / 2));
        generateFood();
    }

    public void generateFood() {
        do {
            food = new GamePoint(random.nextInt(width / cellSize) * cellSize,
                    random.nextInt(height / cellSize) * cellSize);
        } while (snake.contains(food));
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        if (!gameStarted) {
            printMessage(graphics, "Press Space Bar to Begin Game");
        } else {
            graphics.setColor(Color.cyan);
            graphics.fillRect(food.x, food.y, cellSize, cellSize);

            Color snakeColor = Color.GREEN;
            for (final var point : snake) {
                graphics.setColor(snakeColor);
                graphics.fillRect(point.x, point.y, cellSize, cellSize);
                final int newGreen = (int) Math.round(snakeColor.getGreen() * (0.95));
                snakeColor = new Color(0, newGreen, 0);
            }
            if (gameOver) {
                final int currentScore = snake.size();
                if (currentScore > highScore) {
                    highScore = currentScore;
                }
                printMessage(graphics, "Your score: " + snake.size()
                        + "\nHIGH Score: " + highScore
                        + "\nPress Space Bar to Reset");
            }
        }
    }

    private void printMessage(final Graphics graphics, final String message) {
        graphics.setColor(Color.WHITE);
        graphics.setFont(graphics.getFont().deriveFont(30F));
        int currentHeight = height / 3;
        final var graphics2D = (Graphics2D) graphics;
        final var frc = graphics2D.getFontRenderContext();
        for (final var line : message.split("\n")) {
            final var layout = new TextLayout(line, graphics.getFont(), frc);
            final var bounds = layout.getBounds();
            final var targetWidth = (float) (width - bounds.getWidth()) / 2;
            layout.draw(graphics2D, targetWidth, currentHeight);
            currentHeight += graphics.getFontMetrics().getHeight();
        }
    }

    private void move() {
        direction = newDirection;

        final GamePoint head = snake.getFirst();
        final GamePoint newHEAD = switch (direction) {
            case UP -> new GamePoint(head.x, head.y - cellSize);
            case DOWN -> new GamePoint(head.x, head.y + cellSize);
            case LEFT -> new GamePoint(head.x - cellSize, head.y);
            case RIGHT -> new GamePoint(head.x + cellSize, head.y);
        };
        snake.addFirst(newHEAD);

        if (newHEAD.equals(food)) {
            generateFood();
        } else if (isCollision()) {
            gameOver = true;
        } else {
            snake.removeLast();
        }
    }

    private boolean isCollision() {
        final GamePoint head = snake.getFirst();
        final var invalidWidth = (head.x < 0) || (head.x >= width);
        final var invalidHeight = (head.y < 0) || (head.y >= height);
        if (invalidWidth || invalidHeight) {
            return true;
        }

        return snake.size() != new HashSet<>(snake).size();
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        if (gameStarted && !gameOver) {
            move();
        }
        repaint();
    }

    public List<GamePoint> getSnake() {
        return new ArrayList<>(snake);
    }

    public Direction getDirection() {
        return direction;
    }

    public GamePoint getFood() {
        return food;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void moveSnake() {
        move();
    }

    public void handleKeyEvent(int keyCode) {
        handledKeyEvent(keyCode);
    }

    public boolean checkCollision() {
        return isCollision();
    }

    public enum Direction {
        UP,
        DOWN,
        RIGHT,
        LEFT
    }

    public static class GamePoint {
        private final int x;
        private final int y;

        public GamePoint(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GamePoint gamePoint = (GamePoint) o;
            return x == gamePoint.x && y == gamePoint.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
