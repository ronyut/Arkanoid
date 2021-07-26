package arkanoid.listeners;

import arkanoid.sprites.Ball;
import arkanoid.sprites.Block;

/**
 * Interface Name: HitListener.
 * <p>
 * This interface represents all objects that receive notifications when (certain) collidables are being hit.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.2
 * @since 11 May 2019
 */
public interface HitListener {
    /**
     * hitEvent: This method is called whenever the beingHit object is hit.
     *
     * @param beingHit the block that was hit
     * @param hitter   the ball that hit it
     */
    void hitEvent(Block beingHit, Ball hitter);
}