package arkanoid.listeners;

import arkanoid.game.animation.GameLevel;
import arkanoid.sprites.Ball;
import arkanoid.sprites.Block;
import arkanoid.util.Counter;

/**
 * Class Name: BlockRemover.
 * <p>
 * This class is in charge of removing blocks from the gameLevel, as well as keeping count of the number of blocks that
 * remain.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.0
 * @since 11 May 2019
 */
public class BlockRemover implements HitListener {

    private GameLevel gameLevel;
    private Counter remainingBlocks;

    /**
     * Constructor.
     *
     * @param gameLevel         the gameLevel to remove blocks from
     * @param blocksRemain the counter of remaining blocks
     */
    public BlockRemover(GameLevel gameLevel, Counter blocksRemain) {
        this.gameLevel = gameLevel;
        this.remainingBlocks = blocksRemain;
    }

    /**
     * hitEvent: remove blocks that are hit and reach 0 hit-points from the gameLevel.
     *
     * @param beingHit the block that was hit
     * @param hitter   the ball that hit it
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        // if the block is going to die now
        if (beingHit.getHitPoints() == 1) {
            // remove the listener from the block that is being removed from the gameLevel
            beingHit.removeHitListener(this);
            // removes the block from the gameLevel
            beingHit.removeFromGame(this.gameLevel);
            // add 1 to the counter of removed blocks
            this.remainingBlocks.decrease(1);
        } else {
            // subtract 1 life from the block's lives
            beingHit.setHitPoints(beingHit.getHitPoints() - 1);
            // remove the last filling
            beingHit.removeFill();
        }
    }

    /**
     * setCounter: set the counter's value.
     *
     * @param number the number to assign to the counter's value
     */
    public void setCounter(int number) {
        this.remainingBlocks.increase(number);
    }
}