package arkanoid.sprites;

import arkanoid.game.animation.GameLevel;
import biuoop.DrawSurface;
import arkanoid.util.Counter;

import java.awt.Color;

/**
 * Class Name: LivesIndicator.
 * <p>
 * This class can indicate the lives left and show it on the screen.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.0
 * @since 12 May 2019
 */
public class LivesIndicator implements Sprite {
    public static final Color BG_COLOR = Color.WHITE;
    public static final Color TEXT_COLOR = Color.BLACK;
    public static final int THICKNESS = GameLevel.BORDER_THICKNESS;

    private Counter lives;

    /**
     * Constructor.
     *
     * @param livesCounter the lives counter
     */
    public LivesIndicator(Counter livesCounter) {
        this.lives = livesCounter;
    }

    /**
     * drawOn: draw the sprite on a given surface.
     *
     * @param d the surface to draw the sprite on
     */
    public void drawOn(DrawSurface d) {
        double fontSize = THICKNESS * 0.6;
        d.setColor(TEXT_COLOR);
        double textX = GameLevel.WINDOW_WIDTH * 0.12;
        d.drawText((int) textX, THICKNESS / 2 + 5, "Lives: " + lives.getValue(), (int) fontSize);
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
     * @param g the game to add the sprite to
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
