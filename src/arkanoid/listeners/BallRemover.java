package arkanoid.listeners;

import arkanoid.game.animation.GameLevel;
import arkanoid.sprites.Ball;
import arkanoid.sprites.Block;
import arkanoid.util.Counter;

/**
 * Class Name: BallRemover.
 * <p>
 * This class is in charge of removing balls from the gameLevel, as well as keeping count of the number of balls that
 * remain.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.0
 * @since 11 May 2019
 */
public class BallRemover implements HitListener {

    private GameLevel gameLevel;
    private Counter remainingBalls;

    /**
     * Constructor.
     *
     * @param gameLevel        the gameLevel to remove balls from
     * @param ballsRemain the counter of remaining balls
     */
    public BallRemover(GameLevel gameLevel, Counter ballsRemain) {
        this.gameLevel = gameLevel;
        this.remainingBalls = ballsRemain;
    }

    /**
     * hitEvent: remove ball from the gameLevel.
     *
     * @param beingHit the block that was hit
     * @param hitter   the ball that hit it
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        // if the block is going to die now
        if (beingHit.isBallKiller()) {
            // removes the ball from the gameLevel
            hitter.removeFromGame(this.gameLevel);
            // add 1 to the counter of removed blocks
            this.remainingBalls.decrease(1);
        }
    }

    /**
     * setCounter: set the counter's value.
     *
     * @param number the number to assign to the counter's value
     */
    public void setCounter(int number) {
        this.remainingBalls.increase(number);
    }
}