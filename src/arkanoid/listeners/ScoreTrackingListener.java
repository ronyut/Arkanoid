package arkanoid.listeners;

import arkanoid.sprites.Ball;
import arkanoid.sprites.Block;
import arkanoid.util.Counter;

/**
 * Class Name: ScoreTrackingListener.
 * <p>
 * This class is in charge of keeping the score.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.0
 * @since 11 May 2019
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    public static final int HITTING_BLOCK_POINTS = 5;
    public static final int KILLING_BLOCK_POINTS = 10;

    /**
     * Constructor.
     *
     * @param scoreCounter the counter of remaining balls
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * hitEvent: update the score upon block hit.
     *
     * @param beingHit the block that was hit
     * @param hitter   the ball that hit it
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        int addToScore = HITTING_BLOCK_POINTS;

        // if the block dies, give the user 10 points
        if (beingHit.getHitPoints() == 1) {
            addToScore = KILLING_BLOCK_POINTS;
        }

        // update the score
        this.currentScore.increase(addToScore);
    }

    /**
     * setCounter: set the counter's value.
     *
     * @param number the number to assign to the counter's value
     */
    public void setCounter(int number) {
        this.currentScore.increase(number);
    }
}
