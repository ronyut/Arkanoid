package arkanoid.game.levels;

import arkanoid.game.animation.GameLevel;
import arkanoid.geometry.Point;
import arkanoid.sprites.Background;
import arkanoid.sprites.Block;
import arkanoid.sprites.Fill.Image;
import arkanoid.sprites.Sprite;
import arkanoid.util.Velocity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Name: GenericLevel.
 * <p>
 * This class represents a generic level.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.1
 * @since 17 June 2019
 */
public class GenericLevel implements LevelInformation {
    public static final int WINDOW_WIDTH = GameLevel.WINDOW_WIDTH;
    public static final int WINDOW_HEIGHT = GameLevel.WINDOW_HEIGHT;

    private List<Velocity> velocities = new ArrayList<>();
    private List<Block> blocks = new ArrayList<>();
    private String levelName;
    private int paddleSpeed;
    private int paddleWidth;
    private int blocksToRemove;
    private Sprite bg;
    private Point blockStart = new Point(0, 0);
    private int rowHeight;


    /**
     * numberOfBalls: get the initial number of balls.
     *
     * @return the initial number of balls
     */
    public int numberOfBalls() {
        return initialBallVelocities().size();
    }

    /**
     * initialBallVelocities: get the initial velocities of the balls.
     *
     * @return the list of the initial velocity of each ball
     */
    public List<Velocity> initialBallVelocities() {
        return this.velocities;
    }

    /**
     * addVelocity: adds a velocity (i.e. a ball with velocity) by angle and speed.
     *
     * @param angle the velocity's angle
     * @param speed the velocity's speed
     */
    public void addVelocity(double angle, double speed) {
        Velocity v = Velocity.fromAngleAndSpeed(angle, speed);
        this.velocities.add(v);
    }

    /**
     * addVelocities: adds velocities (i.e. balls with their velocities) by angle and speed.
     *
     * @param vels the velocities
     */
    public void addVelocities(List<Velocity> vels) {
        this.velocities.addAll(vels);
    }

    /**
     * paddleSpeed: get the initial paddle speed.
     *
     * @return the initial paddle speed
     */
    public int paddleSpeed() {
        return this.paddleSpeed;
    }

    /**
     * setPaddleSpeed: set the paddle's speed.
     *
     * @param speed the paddle's speed
     */
    public void setPaddleSpeed(int speed) {
        this.paddleSpeed = speed;
    }

    /**
     * paddleWidth: get the initial paddle width.
     *
     * @return the initial paddle width
     */
    public int paddleWidth() {
        return this.paddleWidth;
    }

    /**
     * setPaddleWidth: set the paddle's width.
     *
     * @param width the paddle's width
     */
    public void setPaddleWidth(int width) {
        this.paddleWidth = width;
    }

    /**
     * levelName: get the level name (it'll be displayed at the top of the screen).
     *
     * @return the level's name
     */
    public String levelName() {
        return this.levelName;
    }

    /**
     * setLevelName: set the level's name.
     *
     * @param name the level's name
     */
    public void setLevelName(String name) {
        this.levelName = name;
    }

    /**
     * getBackground: get the level's background (i.e. the sprite that makes up the background).
     *
     * @return a sprite with the background of the level
     */
    public Sprite getBackground() {
        return this.bg;
    }

    /**
     * setBg: set the level's background - fill with color.
     *
     * @param color the bg's color
     */
    public void setBg(Color color) {
        List<Sprite> list = new ArrayList<>();
        Block main = new Block(new Point(0, 0), WINDOW_WIDTH, WINDOW_HEIGHT, color);
        list.add(main);
        this.bg = new Background(list);
    }

    /**
     * setBg: set the level's background - fill with color.
     *
     * @param image the bg's image path
     */
    public void setBg(String image) {
        List<Sprite> list = new ArrayList<>();
        Block main = new Block(new Point(0, 0), WINDOW_WIDTH, WINDOW_HEIGHT, Color.MAGENTA);
        main.addFill(new Image(image));
        list.add(main);
        this.bg = new Background(list);
    }

    /**
     * blocks: get the block that make up the level.
     * Note that each block contains its size, color and location.
     *
     * @return a list of the block that make up the level
     */
    public List<Block> blocks() {
        return this.blocks;
    }

    /**
     * addBlock: add a block to the level.
     *
     * @param block the block to be added to the level
     */
    public void addBlock(Block block) {
        this.blocks.add(block);
    }

    /**
     * addBlocks: add blocks to the level.
     *
     * @param b the blocks to be added to the level
     */
    public void addBlocks(List<Block> b) {
        this.blocks.addAll(b);
    }

    /**
     * numberOfBlocksToRemove: get the Number of blocks that should be removed before the level is considered to be
     * "cleared".
     * Note that this number should be <= blocks.size()
     *
     * @return the Number of blocks that should be removed before the level is considered to be "cleared".
     */
    public int numberOfBlocksToRemove() {
        return this.blocksToRemove;
    }

    /**
     * setNumberOfBlocksToRemove: set the Number of blocks that should be removed before the level is considered to be
     * "cleared".
     * Note that this number should be <= blocks.size()
     *
     * @param num the number of blocks that should be removed before the level is considered to be "cleared".
     */
    public void setNumberOfBlocksToRemove(int num) {
        this.blocksToRemove = num;
    }

    /**
     * getBlockStart: get the start point of the blocks.
     *
     * @return the start point of the blocks
     */
    public Point getBlockStart() {
        return blockStart;
    }

    /**
     * setBlockStartX: set the x value of the start point of the blocks.
     *
     * @param x the x value of the start point of the blocks
     */
    public void setBlockStartX(double x) {
        this.blockStart.setX(x);
    }

    /**
     * setBlockStartY: set the y value of the start point of the blocks.
     *
     * @param y the y value of the start point of the blocks
     */
    public void setBlockStartY(double y) {
        this.blockStart.setY(y);
    }

    /**
     * getRowHeight: get the height of the row.
     * Note: the blocks are arranged in equally-spaced rows. This field specifies the number of pixels in each row.
     * If first row y value is 50 (blocks_start_y:50), the second row will be located at 50 + the value of row_height.
     *
     * @return the height of the row
     */
    public int getRowHeight() {
        return rowHeight;
    }

    /**
     * setRowHeight: set the height of the row.
     * Note: the blocks are arranged in equally-spaced rows. This field specifies the number of pixels in each row.
     * If first row y value is 50 (blocks_start_y:50), the second row will be located at 50 + the value of row_height.
     *
     * @param rh the height of the row
     */
    public void setRowHeight(int rh) {
        this.rowHeight = rh;
    }
}