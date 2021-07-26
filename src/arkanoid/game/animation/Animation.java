package arkanoid.game.animation;

import biuoop.DrawSurface;

/**
 * Interface Name: Animation
 * <p>
 * This interface represents animations.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.0
 * @since 24 May 2019
 */
public interface Animation {
    /**
     * doOneFrame: handle the logic of a single frame in the game.
     *
     * @param surface the surface to draw on.
     */
    void doOneFrame(DrawSurface surface);

    /**
     * shouldStop: tells if the animation should stop.
     *
     * @return whether the animation should stop or not.
     */
    boolean shouldStop();

    /**
     * shouldStop: tell the animation to stop.
     *
     * @param stop whether the animation should stop or not.
     */
    void shouldStop(boolean stop);
}
