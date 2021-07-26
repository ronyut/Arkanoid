package arkanoid.sprites;

import arkanoid.game.animation.GameLevel;
import biuoop.DrawSurface;

import java.util.List;

/**
 * Class Name: Background.
 * <p>
 * Background class represents a background image/complex of the game level.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.0
 * @since 25 May 2019
 */
public class Background implements Sprite {
    private List<Sprite> background;

    /**
     * Constructor.
     *
     * @param bg a list of all the sprites that make up the background
     */
    public Background(List<Sprite> bg) {
        this.background = bg;
    }

    /**
     * drawOn: draw the sprite on a given surface.
     *
     * @param d the surface to draw the sprite on
     */
    public void drawOn(DrawSurface d) {
        for (Sprite sp : this.background) {
            sp.drawOn(d);
        }
    }

    /**
     * timePassed: notify the sprite that time has passed.
     */
    public void timePassed() {
        // do nothing
    }

    /**
     * addToGame: add a sprite to the game.
     *
     * @param gameLevel the game to add the sprite to
     */
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
    }
}
