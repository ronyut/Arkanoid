package arkanoid.game.animation;

import arkanoid.game.GameEnvironment;
import arkanoid.game.SpriteCollection;
import arkanoid.game.levels.LevelInformation;
import arkanoid.geometry.Point;
import arkanoid.geometry.Rectangle;
import arkanoid.listeners.BallRemover;
import arkanoid.listeners.BlockRemover;
import arkanoid.listeners.HitListener;
import arkanoid.listeners.ScoreTrackingListener;
import arkanoid.sprites.Ball;
import arkanoid.sprites.Paddle;
import arkanoid.sprites.Block;
import arkanoid.sprites.Collidable;
import arkanoid.sprites.Sprite;
import arkanoid.sprites.LevelIndicator;
import arkanoid.sprites.LivesIndicator;
import arkanoid.sprites.ScoreIndicator;
import arkanoid.util.Counter;
import arkanoid.util.Velocity;
import biuoop.KeyboardSensor;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * Class Name: GameLevel
 * <p>
 * GameLevel class represents a level in a game.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.3
 * @since 24 May 2019
 */
public class GameLevel implements Animation {

    public static final int WINDOW_WIDTH = Ball.WINDOW_WIDTH;
    public static final int WINDOW_HEIGHT = Ball.WINDOW_HEIGHT;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MS_PER_FRAME = 1000 / FRAMES_PER_SECOND;
    public static final int BALL_SIZE = 5;
    public static final Color BALL_COLOR = Color.WHITE;
    public static final String GAME_TITLE = "Arkanoid";
    public static final int PADDLE_HEIGHT = Paddle.PADDLE_DEFAULT_HEIGHT;
    public static final Color PADDLE_COLOR = Color.ORANGE;
    public static final Color BORDER_COLOR = Color.GRAY;
    public static final int BORDER_THICKNESS = 24;
    public static final int PADDLE_CENTER_POINT_X = WINDOW_WIDTH / 2;
    public static final int PADDLE_CENTER_POINT_Y = WINDOW_HEIGHT - PADDLE_HEIGHT - BORDER_THICKNESS;
    public static final int BALL_START_POINT_X = PADDLE_CENTER_POINT_X;
    public static final int BALL_START_POINT_Y = PADDLE_CENTER_POINT_Y - BALL_SIZE / 2;
    public static final double COUNTDOWN_SECONDS = 2;
    public static final int COUNTDOWN_FROM = 3;

    private SpriteCollection sprites;
    private GameEnvironment environment;
    private KeyboardSensor keyboard;
    private Counter blockCounter;
    private Counter ballCounter;
    private Counter score;
    private Counter lives;
    private AnimationRunner runner;
    private boolean running;
    private LevelInformation levelInfo;
    private Paddle paddle;

    /**
     * Constructor.
     *
     * @param levelInformation the level's information and settings
     * @param ks               the keyboard sensor
     * @param ar               the animation runner
     * @param lives            the lives counter
     * @param score            the score counter
     * @param blocks           the blocks counter
     */
    public GameLevel(LevelInformation levelInformation, KeyboardSensor ks, AnimationRunner ar, Counter lives,
                     Counter score, Counter blocks) {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.blockCounter = blocks;
        this.ballCounter = new Counter();
        this.score = score;
        this.lives = lives;
        this.running = false;
        this.levelInfo = levelInformation;
        this.keyboard = ks;
        this.runner = ar;
    }

    /**
     * addCollidable: add a collidable object to the game.
     *
     * @param c a collidable object
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * addSprite: add a sprite object to the game.
     *
     * @param s a sprite object
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * removeCollidable: remove a collidable object from the game.
     *
     * @param c a collidable object
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * removeSprite: remove a sprite object from the game.
     *
     * @param s a sprite object
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * addBorders: add the borders to the game.
     *
     * @param ballRemover a listener to hit events that will remove balls
     */
    public void addBorders(HitListener ballRemover) {
        // top border
        Block topBorder = new Block(new Point(0, BORDER_THICKNESS), WINDOW_WIDTH, BORDER_THICKNESS, BORDER_COLOR);
        // left border
        Block leftBorder = new Block(new Point(0, BORDER_THICKNESS * 2), BORDER_THICKNESS,
                WINDOW_HEIGHT - 2 * BORDER_THICKNESS, BORDER_COLOR);
        // right border
        Block rightBorder = new Block(new Point(WINDOW_WIDTH - BORDER_THICKNESS, BORDER_THICKNESS * 2),
                BORDER_THICKNESS, WINDOW_HEIGHT - 2 * BORDER_THICKNESS, BORDER_COLOR);
        // bottom border
        Block bottomBorder = new Block(new Point(0, WINDOW_HEIGHT), WINDOW_WIDTH, BORDER_THICKNESS, BORDER_COLOR);

        // add them to the game
        topBorder.addToGame(this);
        leftBorder.addToGame(this);
        rightBorder.addToGame(this);
        bottomBorder.addToGame(this);

        // make the bottom border a ball killer block
        bottomBorder.setBallKiller(true);
        bottomBorder.addHitListener(ballRemover);
    }

    /**
     * addBalls: create the balls and add them to the game.
     */
    public void addBalls() {
        for (Velocity velocity : this.levelInfo.initialBallVelocities()) {
            // create the ball
            Ball ball = new Ball(new Point(BALL_START_POINT_X, BALL_START_POINT_Y), BALL_SIZE, BALL_COLOR, environment,
                    true);
            // give the ball some speed
            ball.setVelocity(velocity);
            // add the ball to the game
            ball.addToGame(this);
        }
    }

    /**
     * addPaddle: create a paddle and add it to the game.
     */
    public void addPaddle() {
        // the upper-left point of the paddle
        Point upperLeft = new Point(PADDLE_CENTER_POINT_X - this.levelInfo.paddleWidth() / 2, PADDLE_CENTER_POINT_Y);
        // the paddle's rectangle
        Rectangle rect = new Rectangle(upperLeft, this.levelInfo.paddleWidth(), PADDLE_HEIGHT);
        // create the paddle and add it to game
        this.paddle = new Paddle(this.keyboard, rect, this.levelInfo.paddleSpeed(), PADDLE_COLOR);
        this.paddle.addToGame(this);
    }

    /**
     * addTheBlocks: create some block and add them to the game.
     *
     * @param blockRemover a listener to hit event that removes block
     * @param scoreTracker a listener to hit event that counts the score
     */
    public void addTheBlocks(HitListener blockRemover, HitListener scoreTracker) {

        for (Block block : this.levelInfo.blocks()) {
            // add the block to game
            block.addToGame(this);
            // add the listeners to hit events
            block.addHitListener(scoreTracker);
            block.addHitListener(blockRemover);
        }

    }

    /**
     * addLifeNScoreIndicator: create life and score indicators and add them to the game.
     */
    public void addMenuIndicators() {
        // top menu
        Block menu = new Block(new Point(0, 0), WINDOW_WIDTH, BORDER_THICKNESS, Color.WHITE);

        ScoreIndicator scoreIndicator = new ScoreIndicator(this.score);
        LivesIndicator livesIndicator = new LivesIndicator(this.lives);
        LevelIndicator levelIndicator = new LevelIndicator(this.levelInfo.levelName());

        // add them to the game
        menu.addToGame(this);
        scoreIndicator.addToGame(this);
        livesIndicator.addToGame(this);
        levelIndicator.addToGame(this);

    }

    /**
     * createBallsOnTopOfPaddle: create 2 balls on top of the paddle.
     */
    public void createBallsOnTopOfPaddle() {
        addBalls();
        this.ballCounter.increase(this.levelInfo.numberOfBalls());
        // recenter the paddle
        Point upperLeft = new Point(PADDLE_CENTER_POINT_X - this.levelInfo.paddleWidth() / 2, PADDLE_CENTER_POINT_Y);
        this.paddle.getCollisionRectangle().setUpperLeft(upperLeft);
    }

    /**
     * initialize: Initialize a new game:
     * create the GUI, the blocks, the ball and the paddle and add them to the game.
     */
    public void initialize() {
        // create new hit listeners
        BlockRemover blockRemover = new BlockRemover(this, this.blockCounter);
        BallRemover ballRemover = new BallRemover(this, this.ballCounter);
        ScoreTrackingListener scoreTracker = new ScoreTrackingListener(this.score);

        // add the background
        addSprite(this.levelInfo.getBackground());
        // create the paddle
        addPaddle();
        // generate the borders and the blocks
        addBorders(ballRemover);
        addTheBlocks(blockRemover, scoreTracker);
        // add the life & score indicators
        addMenuIndicators();

        // update the block counter value to the number of unremovable collidables on the screen
        this.blockCounter.increase(this.levelInfo.numberOfBlocksToRemove());
    }

    /**
     * doOneFrame: handle the logic of a single frame in the game.
     *
     * @param surface the surface to draw on.
     */
    public void doOneFrame(DrawSurface surface) {
        // game-specific logic
        this.sprites.drawAllOn(surface);
        this.sprites.notifyAllTimePassed();

        // pause the game if "p" is pressed
        if (this.keyboard.isPressed("p") || this.keyboard.isPressed("P")) {
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, "space", new PauseScreen()));
        }

        // pause the game if "e" is pressed
        if (this.keyboard.isPressed("e")) {
            this.running = false;
        }

        // make the blocks dance if m is pressed
        if (this.keyboard.isPressed("m")) {
            for (Collidable c : this.environment.getCollidables()) {
                c.letsDance();
            }
        }

        // if no blocks or balls are left, end the game
        if (this.ballCounter.getValue() == 0 || this.blockCounter.getValue() == 0) {
            this.running = false;
        }
    }

    /**
     * shouldStop: tells if the animation should stop.
     *
     * @return whether the animation should stop or not.
     */
    public boolean shouldStop() {
        return !this.running;
    }

    /**
     * shouldStop: tell the animation to stop.
     *
     * @param stop whether the animation should stop or not.
     */
    public void shouldStop(boolean stop) {
        this.running = stop;
    }

    /**
     * playOneTurn: play one turn in the game, i.e. until a life is lost.
     */
    public void playOneTurn() {
        createBallsOnTopOfPaddle();
        this.runner.run(new CountdownAnimation(COUNTDOWN_SECONDS, COUNTDOWN_FROM, this.sprites));
        this.running = true;
        // use our runner to run the current animation, which is one turn of the game.
        this.runner.run(this);
    }

}