package arkanoid.sprites;

import arkanoid.game.animation.GameLevel;
import biuoop.DrawSurface;

/**
 * Interface Name: Sprite.
 * <p>
 * Sprite interface represents all object on screen.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.2
 * @since 11 May 2019
 */
public interface Sprite {
    /**
     * drawOn: draw the sprite on a given surface.
     *
     * @param surface the surface to draw the sprite on
     */
    void drawOn(DrawSurface surface);

    /**
     * timePassed: notify the sprite that time has passed.
     */
    void timePassed();

    /**
     * addToGame: add a sprite to the gameLevel.
     *
     * @param gameLevel the gameLevel to add the sprite to
     */
    void addToGame(GameLevel gameLevel);
}
