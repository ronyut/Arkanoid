package arkanoid.game.levels;

import arkanoid.sprites.Block;
import arkanoid.sprites.Sprite;
import arkanoid.util.Velocity;
import java.util.List;

/**
 * Interface Name: LevelInformation.
 * <p>
 * This interface represents classes that hold information about different levels in the game.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.0
 * @since 25 May 2019
 */
public interface LevelInformation {
    /**
     * numberOfBalls: get the initial number of balls.
     *
     * @return the initial number of balls
     */
    int numberOfBalls();

    /**
     * initialBallVelocities: get the initial velocities of the balls.
     *
     * @return the list of the initial velocity of each ball
     */
    List<Velocity> initialBallVelocities();

    /**
     * paddleSpeed: get the initial paddle speed.
     *
     * @return the initial paddle speed
     */
    int paddleSpeed();

    /**
     * paddleWidth: get the initial paddle width.
     *
     * @return the initial paddle width
     */
    int paddleWidth();

    /**
     * levelName: get the level name (it'll be displayed at the top of the screen).
     *
     * @return the level's name
     */
    String levelName();

    /**
     * getBackground: get the level's background (i.e. the sprite that make up the bg)
     *
     * @return a sprite with the background of the level
     */
    Sprite getBackground();

    /**
     * blocks: get the block that make up the level.
     * Note that each block contains its size, color and location.
     *
     * @return a list of the block that make up the level
     */
    List<Block> blocks();

    /**
     * numberOfBlocksToRemove: get the Number of blocks that should be removed before the level is considered to be
     * "cleared".
     * Note that this number should be <= blocks.size()
     *
     * @return the Number of blocks that should be removed before the level is considered to be "cleared".
     */
    int numberOfBlocksToRemove();
}